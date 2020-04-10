package com.eurder.api.controllers;

import com.eurder.domain.classes.Item;
import com.eurder.domain.classes.ItemGroup;
import com.eurder.domain.classes.Order;
import com.eurder.domain.classes.Price;
import com.eurder.domain.dto.OrderDto;
import com.eurder.domain.mapper.CustomerFactory;
import com.eurder.domain.mapper.OrderMapper;
import com.eurder.domain.repository.CustomerRepository;
import com.eurder.domain.repository.ItemRepository;
import com.eurder.domain.repository.OrderRepository;
import com.eurder.service.OrderService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.util.Base64Utils;
import reactor.core.publisher.Mono;
import java.nio.charset.StandardCharsets;
import java.security.Principal;
import java.time.LocalDate;
import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class OrderControllerTest {
    OrderController orderController;
    OrderRepository orderRepository;
    OrderMapper orderMapper;
    WebTestClient testClient;
    ItemRepository itemRepository;
    CustomerRepository customerRepository;
    OrderService orderService;
    Principal principal = new Principal() {
        @Override
        public String getName() {
            return "admin";
        }
    };

    @Autowired
    public OrderControllerTest(OrderController orderController, WebTestClient webTestClient, OrderMapper orderMapper, ItemRepository itemRepository, CustomerRepository customerRepository, OrderRepository orderRepository, OrderService orderService) {
        this.orderController = orderController;
        this.orderMapper = orderMapper;
        this.testClient = webTestClient;
        this.itemRepository = itemRepository;
        this.customerRepository = customerRepository;
        this.orderRepository = orderRepository;
        this.orderService = orderService;

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
    void IsremovedfromStock_WhenOrdered() {
        Order order = getOrder();
        OrderDto orderDto = orderMapper.toOrderDto(order);

        orderController.placeOrder(orderDto, principal);

        Assertions.assertThat(itemRepository.getItemList().get(0).getAmount()).isEqualTo(2);
    }

    @Test
    void addOrder() {
        Order order = getOrder();
        OrderDto orderDto = orderMapper.toOrderDto(order);

        Assertions.assertThat(orderDto).isEqualTo(orderController.placeOrder(orderDto, principal));
    }

    @Test
    void addOrder_addedToList() {
        Order order = getOrder();
        OrderDto orderDto = orderMapper.toOrderDto(order);

        orderController.placeOrder(orderDto, principal);

        Assertions.assertThat(orderController.getOrderService().getOrderRepository().getOrderList().get(0)).isEqualTo(order);
    }

    @Test
    void Hascorrect_deliverydate() {
        Order order = getOrder();
        OrderDto orderDto = orderMapper.toOrderDto(order);

        orderController.placeOrder(orderDto, principal);

        Assertions.assertThat(order.getItemGroupList().get(0).getShippingdate()).isEqualTo(LocalDate.now().plusDays(1));
    }

    private Order getOrder() {
        return new Order(List.of(new ItemGroup(itemRepository.getItemList().get(0), 2, true)), customerRepository.getCustomerList().get(0));
    }

    // uw orderDto klass heeft Default constructors EN setter nodig!!!!!!!!!!
    @Test
    void webtestclient_placeOrder() {
        OrderDto orderDto = orderMapper.toOrderDto(getOrder());

        String url = "/orders";

        testClient.post()
                .uri(url)
                .header("Authorization", "Basic " + Base64Utils
                        .encodeToString(("admin" + ":" + "admin").getBytes(StandardCharsets.UTF_8)))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .body(Mono.just(orderDto), OrderDto.class)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(OrderDto.class)
                .isEqualTo(orderDto);

//        Assertions.assertThat(orderRepository.getOrderList().get(1)).isEqualTo(getOrder()); gaat niet door items die wegzijn uit stock
    }

    @Test
    void webtestclient_getAllOrders() {
        OrderDto orderDto = orderMapper.toOrderDto(getOrder());

        String url = "/orders";

        testClient.get()
                .uri(url)
                .header("Authorization", "Basic " + Base64Utils
                        .encodeToString(("admin" + ":" + "admin").getBytes(StandardCharsets.UTF_8)))
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(OrderDto.class)
                .isEqualTo(orderService.getAllOrders());

    }

//    @Test
//    void webtestclient_getOrdersByCustomer() {
//        OrderDto orderDto = orderMapper.toOrderDto(getOrder());
//
//        String url = "/orders";
//
//        testClient.get()
//                .uri(url)
//                .header("Authorization", "Basic " + Base64Utils
//                        .encodeToString(("admin" + ":" + "admin").getBytes(StandardCharsets.UTF_8)))
//                .exchange()
//                .expectStatus().isOk()
//                .expectBody(ReportDto.class)
//        .isEqualTo(customerRepository.getCustomerList().get(0).getReportDto());
//
//
//    }
}