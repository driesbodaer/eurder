package com.eurder.api.controllers;

import com.eurder.domain.classes.Customer;
import com.eurder.domain.dto.CustomerDto;
import com.eurder.domain.mapper.CustomerFactory;
import com.eurder.domain.mapper.CustomerMapper;
import com.eurder.domain.repository.CustomerRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CustomerControllerTest {
//    @Autowired
//    public CustomerControllerTest(CustomerController customerController) {
//        this.customerController = customerController;
//    }

    CustomerController customerController;
    @Autowired
    WebTestClient testClient;

    @BeforeEach
    public void init() {
        this.customerController = new CustomerController(new CustomerMapper(), new CustomerRepository());
    }

    @Test
    void createCostumer() {

        Customer actual = CustomerFactory.buildCustomer()
                .setAddress("kerkstraat")
                .setFirstname("bart")
                .setLastname("test")
                .setAddress("kerkstraat")
                .setEmailadress("dries@gmail.com")
                .setPhonenumber("013426238")
                .build();

        CustomerDto expected = CustomerFactory.buildCustomer()
                .setAddress("kerkstraat")
                .setFirstname("bart")
                .setLastname("test")
                .setAddress("kerkstraat")
                .setEmailadress("dries@gmail.com")
                .setPhonenumber("013426238")
                .buildCustomerDto();

        Customer expectedCustomer = customerController.createCostumer(expected);

        Assertions.assertThat(actual).isEqualTo(expectedCustomer);
    }
    @Test
    void createCostumer_customerIsAdded() {

        Customer actual = CustomerFactory.buildCustomer()
                .setAddress("kerkstraat")
                .setFirstname("bart")
                .setLastname("test")
                .setAddress("kerkstraat")
                .setEmailadress("dries@gmail.com")
                .setPhonenumber("013426238")
                .build();

        CustomerDto expected = CustomerFactory.buildCustomer()
                .setAddress("kerkstraat")
                .setFirstname("bart")
                .setLastname("test")
                .setAddress("kerkstraat")
                .setEmailadress("dries@gmail.com")
                .setPhonenumber("013426238")
                .buildCustomerDto();

        Customer expectedCustomer = customerController.createCostumer(expected);

        Assertions.assertThat(actual).isEqualTo(expectedCustomer);
    }

    @Test
    void createCostumer_WithSprinboottest() {
        CustomerDto expected = CustomerFactory.buildCustomer()
                .setAddress("kerkstraat")
                .setFirstname("bart")
                .setLastname("test")
                .setAddress("kerkstraat")
                .setEmailadress("dries@gmail.com")
                .setPhonenumber("013426238")
                .buildCustomerDto();

        String url = "customers";

        testClient.post()
                .uri(url)
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(expected), CustomerDto.class)
                .exchange()
                .expectStatus().isOk()
                .expectBody(CustomerDto.class)
                .isEqualTo(expected);
    }
}