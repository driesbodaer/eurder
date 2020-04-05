package com.eurder.api.controllers;

import com.eurder.domain.classes.Item;
import com.eurder.domain.classes.Price;
import com.eurder.domain.dto.ItemDto;
import com.eurder.domain.mapper.ItemMapper;
import com.eurder.domain.mapper.OrderMapper;
import com.eurder.service.ItemService;
import com.eurder.service.OrderService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WithMockUser(username="admin", authorities={"ADMIN_ONLY"})
@ComponentScan(basePackages = "com.eurder")
@WebMvcTest(ItemController.class)
class MockingItemControllerTest {



    @MockBean
    ItemService itemService;

    @Autowired
    MockMvc mockMvc;
    @Autowired
    com.eurder.domain.mapper.ItemMapper itemMapper;
    @Autowired
    ItemController itemController;


    @Test
    void addItem() throws Exception {

        Mockito.when(itemService.getSortedList()).thenReturn(new ArrayList<>(List.of(new ItemDto("iets", "ites" ,new Price(5, "eur"), 5))));


        //doe een get op ordercontroller
        mockMvc.perform(get("/items")
                .with(user("admin")
                        .password("admin")
                        .roles("admin"))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }



    // I dont know how to turn off authentication in tests?

    private Item getItem() {
        return new Item("cheese", "burgut", new Price(5.4, "eur"), 15);
    }

}