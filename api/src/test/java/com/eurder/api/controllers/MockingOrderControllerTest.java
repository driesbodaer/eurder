package com.eurder.api.controllers;

import com.eurder.domain.classes.Item;
import com.eurder.domain.classes.ItemGroup;
import com.eurder.domain.classes.Order;
import com.eurder.domain.classes.Price;
import com.eurder.domain.dto.OrderDto;
import com.eurder.domain.mapper.CustomerFactory;
import com.eurder.domain.mapper.OrderMapper;
import com.eurder.service.OrderService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WithMockUser(username="admin", authorities={"ADMIN_ONLY"})
@ComponentScan(basePackages = "com.eurder")
@WebMvcTest(OrderController.class)
class MockingOrderControllerTest {

    @MockBean
    OrderService orderService;

    @Autowired
    MockMvc mockMvc;
    @Autowired
    OrderMapper orderMapper;
//dummyomgeving

    @Test
    void getAllOrders() throws Exception {

        List<Order> orderList = new ArrayList<>();
        orderList.add(new Order(new ArrayList<ItemGroup>(List.of(new ItemGroup(new Item("kaas", "camenbert", new Price(1.5, "eur"), 10), 2, true))), CustomerFactory.buildCustomer()
                .setAddress("kerkstraat")
                .setFirstname("admin")
                .setLastname("bodaer")
                .setAddress("kerkstraat")
                .setEmailadress("dries@gmail.com")
                .setPhonenumber("013426238")
                .build()));

        List<OrderDto> orderDtoList = new ArrayList<>();
        for (Order order : orderList) {
            orderDtoList.add(orderMapper.toOrderDto(order));
        }

        Mockito.when(orderService.getAllOrders()).thenReturn(orderDtoList);


        //doe een get op ordercontroller
        mockMvc.perform(get("/orders")
                .with(user("admin")
                        .password("admin")
                        .roles("admin"))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());

    }

//    @Test
//    void Create_Orders() throws Exception {
//
//        List<Order> orderList = new ArrayList<>();
//        orderList.add(new Order(new ArrayList<ItemGroup>(List.of(new ItemGroup(new Item("kaas", "camenbert", new Price(1.5, "eur"), 10), 2, true))), CustomerFactory.buildCustomer()
//                .setAddress("kerkstraat")
//                .setFirstname("admin")
//                .setLastname("bodaer")
//                .setAddress("kerkstraat")
//                .setEmailadress("dries@gmail.com")
//                .setPhonenumber("013426238")
//                .build()));
//
//        List<OrderDto> orderDtoList = new ArrayList<>();
//        for (Order order : orderList) {
//            orderDtoList.add(orderMapper.toOrderDto(order));
//        }
//
//        Mockito.when(orderService.placeOrder(orderDtoList.get(0) , "admin")).thenReturn(orderDtoList.get(0));
//
//
//        mockMvc.perform(MockMvcRequestBuilders.post("/orders")
//                .content(asJsonString(orderDtoList.get(0)))
//                .contentType(MediaType.APPLICATION_JSON)
//                .accept(MediaType.APPLICATION_JSON))
//                .andExpect(status().isCreated())
//                .andExpect(jsonPath("$.itemGroupDtoList" , is(orderList.get(0).getItemGroupList())));
//
//    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


}