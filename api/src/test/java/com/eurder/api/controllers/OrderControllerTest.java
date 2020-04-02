package com.eurder.api.controllers;

import com.eurder.domain.classes.*;
import com.eurder.domain.dto.OrderDto;
import com.eurder.domain.mapper.CustomerFactory;
import com.eurder.domain.mapper.OrderMapper;
import com.eurder.domain.repository.CustomerRepository;
import com.eurder.domain.repository.ItemRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.util.Base64Utils;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class OrderControllerTest {
    OrderController orderController;
    OrderMapper orderMapper;
    WebTestClient testClient;
    ItemRepository itemRepository;
    CustomerRepository customerRepository;

    @Autowired
    public OrderControllerTest(OrderController orderController, WebTestClient webTestClient, OrderMapper orderMapper, ItemRepository itemRepository, CustomerRepository customerRepository) {
        this.orderController = orderController;
        this.orderMapper = orderMapper;
        this.testClient = webTestClient;
        this.itemRepository = itemRepository;
        this.customerRepository = customerRepository;
        customerRepository.addCustomer(CustomerFactory.buildCustomer()
                .setAddress("kerkstraat")
                .setFirstname("ccc")
                .setLastname("test")
                .setEmailadress("dries@gmail.com")
                .setPhonenumber("")
                .build());
        itemRepository.addItem(new Item("iets", "iets", new Price(10.0, "eur"), 42));
    }

    @Test
    void addOrder() {
        Order order = getOrder();
        OrderDto orderDto = orderMapper.toOrderDto(order);

        Assertions.assertThat(order).isEqualTo(orderController.placeOrder(orderDto));
    }

    @Test
    void addOrder_addedToList() {
        Order order = getOrder();
        OrderDto orderDto = orderMapper.toOrderDto(order);

        orderController.placeOrder(orderDto);
        Assertions.assertThat(orderController.getOrderService().getOrderRepository().getOrderList().get(0)).isEqualTo(order);
    }

    private Order getOrder() {
        return new Order(List.of(new ItemGroup(itemRepository.getItemList().get(0), 2, true)), customerRepository.getCustomerList().get(0));
    }


    @Test
    void webtestclient_test() {
        Order order = getOrder();
        OrderDto orderDto = orderMapper.toOrderDto(order);

        String url = "orders";

        testClient.post()
                .uri(url)
                .header("Authorization", "Basic " + Base64Utils
                        .encodeToString(("admin" + ":" + "admin").getBytes(StandardCharsets.UTF_8)))
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(orderDto), OrderDto.class)
                .exchange()
                .expectStatus().isOk()
                .expectBody(OrderDto.class)
                .isEqualTo(orderDto);

    }
}