package com.eurder.domain.repository;

import com.eurder.domain.classes.Customer;
import com.eurder.domain.mapper.CustomerFactory;

import java.util.List;


public class CustomerRepository {

   private List<Customer> customerList;

    public CustomerRepository() {
        this.customerList = List.of(CustomerFactory.buildCustomer()
                .setAddress("kerkstraat")
                .setFirstname("dries")
                .setLastname("bodaer")
                .setAddress("kerkstraat")
                .setEmailadress("dries@gmail.com")
                .setPhonenumber("013426238")
                .build());
    }
}
