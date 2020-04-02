package com.eurder.domain.repository;

import com.eurder.domain.classes.Customer;
import com.eurder.domain.mapper.CustomerFactory;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class CustomerRepository {

    private List<Customer> customerList;

    public CustomerRepository() {
        this.customerList = new ArrayList<>();
        customerList.add(CustomerFactory.buildCustomer()
                .setAddress("kerkstraat")
                .setFirstname("dries")
                .setLastname("bodaer")
                .setAddress("kerkstraat")
                .setEmailadress("dries@gmail.com")
                .setPhonenumber("013426238")
                .build());
    }

    public void addCustomer(Customer customer) {
        customerList.add(customer);
    }

    public List<Customer> getCustomerList() {
        return customerList;
    }

    public Customer getCustomerBasedOnName(String name) {
        return customerList.stream().filter(x -> x.getFirstname().equals(name)).findFirst().orElse(null);
    }
}
