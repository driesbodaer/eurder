package com.eurder.api.controllers;

import com.eurder.domain.classes.Customer;
import com.eurder.domain.dto.CustomerDto;
import com.eurder.domain.mapper.CustomerFactory;
import com.eurder.domain.mapper.CustomerMapper;
import com.eurder.infrastructure.authentication.ExternalAuthentication;
import com.eurder.infrastructure.eurderRoles.EurderRole;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.util.Base64Utils;
import reactor.core.publisher.Mono;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//@EnableAutoConfiguration (exclude = { SecurityAutoConfiguration.class})
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
                .setFirstname("aaaaa")
                .setLastname("test")
                .setEmailadress("dries@gmail.com")
                .setPhonenumber("013426238")
                .buildCustomerDto();

        CustomerDto expectedCustomer = customerController.createCostumer(expected);

        Assertions.assertThat(customerController.getCustomerService().getCustomerRepository().getCustomerList().get(2)).isEqualTo(customerMapper.toCustomer(expectedCustomer));
    }

    @Test
    void createCostumer_customerIsAddedToSecurity() {

        CustomerDto expected = CustomerFactory.buildCustomer()
                .setAddress("kerkstraat")
                .setFirstname("bbb")
                .setLastname("test")
                .setEmailadress("dries@gmail.com")
                .setPhonenumber("013426238")
                .buildCustomerDto();

        CustomerDto expectedCustomer = customerController.createCostumer(expected);

        Assertions.assertThat(customerController.getCustomerService().getFakeAuthenticationService().getUser("bbb", "customer")).isEqualTo(new ExternalAuthentication().withUsername("bbb").withPassword("customer").withRoles(List.of(EurderRole.CUSTOMER)));
    }

    @Test
    void createCostumer_FieldEmpty_throws() {

        Customer actual = CustomerFactory.buildCustomer()
                .setAddress("kerkstraat")
                .setFirstname("ccc")
                .setLastname("test")
                .setEmailadress("dries@gmail.com")
                .setPhonenumber("")
                .build();
        CustomerDto customerDto = customerMapper.toCustomerDto(actual);

        Assertions.assertThatThrownBy(() -> customerController.createCostumer(customerDto));
    }

    @Test
    void createCostumer_WrongName_throws() {

        Customer actual = CustomerFactory.buildCustomer()
                .setAddress("kerkstraat")
                .setFirstname("ddd10")
                .setLastname("test")
                .setEmailadress("dries@gmail.com")
                .setPhonenumber("01352485")
                .build();
        CustomerDto customerDto = customerMapper.toCustomerDto(actual);

        Assertions.assertThatThrownBy(() -> customerController.createCostumer(customerDto));
    }

    @Test
    void createCostumer() {

        Customer actual = CustomerFactory.buildCustomer()
                .setAddress("kerkstraat")
                .setFirstname("eee")
                .setLastname("test")
                .setEmailadress("dries@gmail.com")
                .setPhonenumber("013426238")
                .build();
        CustomerDto expectedCustomer = customerController.createCostumer(customerMapper.toCustomerDto(actual));

        Assertions.assertThat(actual).isEqualTo(customerMapper.toCustomer(expectedCustomer));
    }

    @Test
    void createCostumer_WithSprinboottest() throws UnsupportedEncodingException {
        CustomerDto expected = CustomerFactory.buildCustomer()
                .setAddress("kerkstraat")
                .setFirstname("fff")
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
                .expectStatus().isCreated()
                .expectBody(CustomerDto.class)
                .isEqualTo(expected);
    }

    @Test
    void getCostumers_WithSprinboottest() throws UnsupportedEncodingException {
        CustomerDto expected = CustomerFactory.buildCustomer()
                .setAddress("kerkstraat")
                .setFirstname("ggg")
                .setLastname("test")
                .setEmailadress("dries@gmail.com")
                .setPhonenumber("013426238")
                .buildCustomerDto();
        customerController.createCostumer(expected);
        String url = "customers";

        testClient.get()
                .uri(url)
                .header("Authorization", "Basic " + Base64Utils
                        .encodeToString(("admin" + ":" + "admin").getBytes(StandardCharsets.UTF_8)))
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(CustomerDto.class)
                .contains(expected);

    }

    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    @Test
    void getCostumer_WithSprinboottest() throws UnsupportedEncodingException {
        CustomerDto expected = CustomerFactory.buildCustomer()
                .setAddress("kerkstraat")
                .setFirstname("admin")
                .setLastname("bodaer")
                .setEmailadress("dries@gmail.com")
                .setPhonenumber("013426238")
                .buildCustomerDto();

        String url = "customers/1";

        testClient.get()
                .uri(url)
                .header("Authorization", "Basic " + Base64Utils
                        .encodeToString(("admin" + ":" + "admin").getBytes(StandardCharsets.UTF_8)))
                .exchange()
                .expectStatus().isOk()
                .expectBody(CustomerDto.class)
                .isEqualTo(expected);
    }


}