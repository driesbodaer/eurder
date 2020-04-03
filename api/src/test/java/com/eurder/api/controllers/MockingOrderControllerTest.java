package com.eurder.api.controllers;

import com.eurder.domain.classes.Item;
import com.eurder.domain.classes.ItemGroup;
import com.eurder.domain.classes.Order;
import com.eurder.domain.classes.Price;
import com.eurder.domain.mapper.CustomerFactory;
import com.eurder.service.OrderService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ComponentScan(basePackages = "com.eurder")
@WebMvcTest(OrderController.class)
class MockingOrderControllerTest {

    @MockBean
    OrderService orderService;

    @Autowired
    MockMvc mockMvc;


//    @Test
//    void getAllOrders() throws Exception {
//
//        List<Order> orderList = new ArrayList<>();
//             orderList.add(new Order(new ArrayList<ItemGroup>( List.of(new ItemGroup(  new Item("kaas", "camenbert", new Price(1.5, "eur"), 10), 2, true))), CustomerFactory.buildCustomer()
//                     .setAddress("kerkstraat")
//                     .setFirstname("admin")
//                     .setLastname("bodaer")
//                     .setAddress("kerkstraat")
//                     .setEmailadress("dries@gmail.com")
//                     .setPhonenumber("013426238")
//                     .build()));
//
//
//        Mockito.when(orderService.getAllOrders()).thenReturn(orderList);
//
//        mockMvc.perform(get("/orders")
//                .with(user("admin")
//                        .password("admin")
//                        .roles("admin"))
//                .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk());
//
//        // meer uitleg over nodig
//    }



}