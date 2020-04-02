package com.eurder.api.controllers;

import com.eurder.domain.classes.Customer;
import com.eurder.domain.dto.CustomerDto;
import com.eurder.domain.mapper.CustomerFactory;
import com.eurder.domain.mapper.CustomerMapper;
import com.eurder.domain.repository.CustomerRepository;
import com.eurder.infrastructure.authentication.ExternalAuthentication;
import com.eurder.infrastructure.eurderRoles.EurderRole;
import com.google.common.base.Utf8;
import org.assertj.core.api.Assertions;
import org.assertj.core.api.ThrowableAssert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.util.Base64Utils;
import reactor.core.publisher.Mono;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CustomerControllerTest {
    CustomerController customerController;
    CustomerMapper customerMapper;

    WebTestClient testClient;

    @Autowired
    public CustomerControllerTest(CustomerController customerController, WebTestClient webTestClient, CustomerMapper customerMapper) {
        this.customerController = customerController;
        this.customerMapper = customerMapper;
        this.testClient = webTestClient;
    }


    @Test
    void createCostumer_customerIsAdded() {

        CustomerDto expected = CustomerFactory.buildCustomer()
                .setAddress("kerkstraat")
                .setFirstname("bart2")
                .setLastname("test")
                .setEmailadress("dries@gmail.com")
                .setPhonenumber("013426238")
                .buildCustomerDto();

        Customer expectedCustomer = customerController.createCostumer(expected);

        Assertions.assertThat(customerController.getCustomerService().getCustomerRepository().getCustomerList().get(2)).isEqualTo(expectedCustomer);
    }

    @Test
    void createCostumer_customerIsAddedToSecurity() {

        CustomerDto expected = CustomerFactory.buildCustomer()
                .setAddress("kerkstraat")
                .setFirstname("bart2")
                .setLastname("test")
                .setEmailadress("dries@gmail.com")
                .setPhonenumber("013426238")
                .buildCustomerDto();

        Customer expectedCustomer = customerController.createCostumer(expected);

        Assertions.assertThat(customerController.getCustomerService().getFakeAuthenticationService().getUser("bart2", "customer")).isEqualTo(new ExternalAuthentication().withUsername("bart2").withPassword("customer").withRoles(List.of(EurderRole.CUSTOMER)));
    }

    @Test
    void createCostumer_FieldEmpty_throws() {

        Customer actual = CustomerFactory.buildCustomer()
                .setAddress("kerkstraat")
                .setFirstname("bart7")
                .setLastname("test")
                .setEmailadress("dries@gmail.com")
                .setPhonenumber("")
                .build();
        CustomerDto customerDto = customerMapper.toCustomerDto(actual);

        Assertions.assertThatThrownBy(() -> customerController.createCostumer(customerDto));
    }

    @Test
    void createCostumer() {

        Customer actual = CustomerFactory.buildCustomer()
                .setAddress("kerkstraat")
                .setFirstname("bart1")
                .setLastname("test")
                .setEmailadress("dries@gmail.com")
                .setPhonenumber("013426238")
                .build();
        Customer expectedCustomer = customerController.createCostumer(customerMapper.toCustomerDto(actual));

        Assertions.assertThat(actual).isEqualTo(expectedCustomer);
    }

    @Test
    void createCostumer_WithSprinboottest() throws UnsupportedEncodingException {
        CustomerDto expected = CustomerFactory.buildCustomer()
                .setAddress("kerkstraat")
                .setFirstname("bart")
                .setLastname("test")
                .setEmailadress("dries@gmail.com")
                .setPhonenumber("013426238")
                .buildCustomerDto();

        String url = "customers";

        testClient.post()
                .uri(url)
                .header("Authorization", "Basic " + Base64Utils
                        .encodeToString(("admin" + ":" + "admin").getBytes(StandardCharsets.UTF_8)))
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(expected), CustomerDto.class)
                .exchange()
                .expectStatus().isOk()
                .expectBody(CustomerDto.class)
                .isEqualTo(expected);
    }


}