package com.eurder.api.controllers;

import com.eurder.domain.classes.Customer;
import com.eurder.domain.dto.CustomerDto;
import com.eurder.domain.mapper.CustomerFactory;
import com.eurder.domain.mapper.CustomerMapper;
import com.eurder.service.CustomerService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ComponentScan(basePackages = "com.eurder")
@WebMvcTest(CustomerController.class)
class MockingCustomerControllerTest {

    @MockBean
    CustomerService customerService;

    @Autowired
    MockMvc mockMvc;
    @Autowired
    CustomerMapper customerMapper;
//dummyomgeving

    @Test
    void getAllCustomers() throws Exception {

        List<Customer> customerList = new ArrayList<>();
        Customer customer =  CustomerFactory.buildCustomer()
                .setAddress("kerkstraat")
                .setFirstname("ggg")
                .setLastname("test")
                .setEmailadress("dries@gmail.com")
                .setPhonenumber("013426238")
                .build();
        customerList.add(customer);

        List<CustomerDto> customerDtoList = new ArrayList<>();
        for (Customer customer1 : customerList) {
            customerDtoList.add(customerMapper.toCustomerDto(customer1));
        }

        Mockito.when(customerService.getAllCustomers()).thenReturn(customerDtoList);


        //doe een get op ordercontroller
        mockMvc.perform(get("/customers")
                .with(user("admin")
                        .password("admin")
                        .roles("admin"))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());

    }

    @Test
    void getCustomer_byID() throws Exception {

        CustomerDto customer =  CustomerFactory.buildCustomer()
                .setAddress("kerkstraat")
                .setFirstname("ggg")
                .setLastname("test")
                .setEmailadress("dries@gmail.com")
                .setPhonenumber("013426238")
                .buildCustomerDto();


        Mockito.when(customerService.getCustomer(1)).thenReturn(customer);


        //doe een get op ordercontroller
        mockMvc.perform(get("/customers/1")
                .with(user("admin")
                        .password("admin")
                        .roles("admin"))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstname" , is("ggg")));

    }

    @Test
    void Create_Customer() throws Exception {

        CustomerDto customer =  CustomerFactory.buildCustomer()
                .setAddress("kerkstraat")
                .setFirstname("mmm")
                .setLastname("test")
                .setEmailadress("dries@gmail.com")
                .setPhonenumber("013426238")
                .buildCustomerDto();


        Mockito.when(customerService.createCustomer(customer)).thenReturn(customer);


        mockMvc.perform(MockMvcRequestBuilders.post("/customers")
                .with(user("admin")
                        .password("admin")
                        .roles("admin"))
                .content(asJsonString(customer))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.firstname" , is("mmm")));

    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


}