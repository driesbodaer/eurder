package com.eurder.api.controllers;

import com.eurder.domain.classes.Customer;
import com.eurder.domain.dto.CustomerDtoRABBIT;
import com.eurder.domain.mapper.CustomerFactory;
import com.eurder.domain.mapper.CustomerMapper;
import com.eurder.domain.repository.CustomerRepository;
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
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_CLASS)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CustomerControllerRABBITTest {
    CustomerControllerRABBIT customerControllerRABBIT;
    CustomerMapper customerMapper;
    WebTestClient testClient;
    CustomerRepository customerRepository;

    @Autowired
    public CustomerControllerRABBITTest(CustomerControllerRABBIT customerControllerRABBIT, WebTestClient webTestClient, CustomerMapper customerMapper, CustomerRepository customerRepository) {
        this.customerControllerRABBIT = customerControllerRABBIT;
        this.customerMapper = customerMapper;
        this.testClient = webTestClient;
        this.customerRepository = customerRepository;
    }



    @Test
    void createCostumer_customerIsAdded() {

        CustomerDtoRABBIT expected = CustomerFactory.buildCustomer()
                .setAddress("kerkstraat")
                .setFirstname("aaaaa")
                .setLastname("test")
                .setEmailadress("dries@gmail.com")
                .setPhonenumber("013426238")
                .buildCustomerDto();

        CustomerDtoRABBIT expectedCustomer = customerControllerRABBIT.createCostumer(expected);

        Assertions.assertThat(customerControllerRABBIT.getCustomerService().getCustomerRepository().getCustomerList().get(4)).isEqualTo(customerMapper.toCustomer(expectedCustomer));
    }

    @Test
    void createCostumer_customerIsAddedToSecurity() {

        CustomerDtoRABBIT expected = CustomerFactory.buildCustomer()
                .setAddress("kerkstraat")
                .setFirstname("bbb")
                .setLastname("test")
                .setEmailadress("dries@gmail.com")
                .setPhonenumber("013426238")
                .buildCustomerDto();

        CustomerDtoRABBIT expectedCustomer = customerControllerRABBIT.createCostumer(expected);

        Assertions.assertThat(customerControllerRABBIT.getCustomerService().getFakeAuthenticationService().getUser("bbb", "customer")).isEqualTo(new ExternalAuthentication().withUsername("bbb").withPassword("customer").withRoles(List.of(EurderRole.CUSTOMER)));
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
        CustomerDtoRABBIT customerDtoRABBIT = customerMapper.toCustomerDto(actual);

        Assertions.assertThatThrownBy(() -> customerControllerRABBIT.createCostumer(customerDtoRABBIT));
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
        CustomerDtoRABBIT customerDtoRABBIT = customerMapper.toCustomerDto(actual);

        Assertions.assertThatThrownBy(() -> customerControllerRABBIT.createCostumer(customerDtoRABBIT));
    }

    @Test
    void testmapper() {

        Customer actual = CustomerFactory.buildCustomer()
                .setAddress("kerkstraat")
                .setFirstname("eee")
                .setLastname("test")
                .setEmailadress("dries@gmail.com")
                .setPhonenumber("013426238")
                .build();
        CustomerDtoRABBIT expectedCustomer = customerControllerRABBIT.createCostumer(customerMapper.toCustomerDto(actual));

        Assertions.assertThat(actual).isEqualTo(customerMapper.toCustomer(expectedCustomer));
    }

    @Test
    void createCostumer_WithSprinboottest() throws UnsupportedEncodingException {
        CustomerDtoRABBIT expected = CustomerFactory.buildCustomer()
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
                .body(Mono.just(expected), CustomerDtoRABBIT.class)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(CustomerDtoRABBIT.class)
                .isEqualTo(expected);

        Assertions.assertThat(customerRepository.getCustomerList().get(1)).isEqualTo(customerMapper.toCustomer(expected));
    }

    @Test
    void getCostumers_WithSprinboottest_all() throws UnsupportedEncodingException {
        CustomerDtoRABBIT expected = CustomerFactory.buildCustomer()
                .setAddress("kerkstraat")
                .setFirstname("ggg")
                .setLastname("test")
                .setEmailadress("dries@gmail.com")
                .setPhonenumber("013426238")
                .buildCustomerDto();
        customerControllerRABBIT.createCostumer(expected);
        String url = "customers";

        testClient.get()
                .uri(url)
                .header("Authorization", "Basic " + Base64Utils
                        .encodeToString(("admin" + ":" + "admin").getBytes(StandardCharsets.UTF_8)))
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(CustomerDtoRABBIT.class)
                .contains(expected);

    }

//    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    @Test
    void getCostumer_WithSprinboottest_ByID() {
        CustomerDtoRABBIT expected = CustomerFactory.buildCustomer()
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
                .expectBody(CustomerDtoRABBIT.class)
                .isEqualTo(expected);
    }
}