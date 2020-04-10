package com.eurder.api.controllers;

import com.eurder.domain.classes.Item;
import com.eurder.domain.classes.Price;
import com.eurder.domain.dto.ItemDto;
import com.eurder.service.ItemService;
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
    void getItems() throws Exception {

        Mockito.when(itemService.getSortedList()).thenReturn(new ArrayList<>(List.of(new ItemDto("iets", "ites" ,new Price(5, "eur"), 5))));


        mockMvc.perform(get("/items")
                .with(user("admin")
                        .password("admin")
                        .roles("admin"))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    void addItem() throws Exception {

        Mockito.when(itemService.addItem(itemMapper.toItemDto(getItem()))).thenReturn(itemMapper.toItemDto(getItem()));


        mockMvc.perform(MockMvcRequestBuilders.post("/items")
                .content(asJsonString(getItem()))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name" , is("cheese")))
                .andExpect(jsonPath("$.description" , is("burgut")));
    }

//    @Test
//    void update_Item() throws Exception {
//
//
//        Mockito.when(itemService.updateItem(itemMapper.toItemDto(getItem()), "admin")).thenReturn(itemMapper.toItemDto(getItem()));
//
//
//        mockMvc.perform(MockMvcRequestBuilders.put("/items/cheese")
//                .content(asJsonString(getItem()))
//                .contentType(MediaType.APPLICATION_JSON)
//                .accept(MediaType.APPLICATION_JSON))
//                .andExpect(status().isCreated())
//                .andExpect(jsonPath("$.name" , is("cheese")))
//                .andExpect(jsonPath("$.description" , is("burgut")));
//    }



    private Item getItem() {
        return new Item("cheese", "burgut", new Price(5.4, "eur"), 15);
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}