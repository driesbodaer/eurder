package com.eurder.domain.mapper;

import com.eurder.domain.classes.Customer;
import com.eurder.domain.dto.CustomerDtoRABBIT;
import org.springframework.stereotype.Component;

@Component
public class CustomerMapper {

    public Customer toCustomer(CustomerDtoRABBIT customerDtoRABBIT) {
        return CustomerFactory.buildCustomer()
                .setAddress(customerDtoRABBIT.getAddress())
                .setFirstname(customerDtoRABBIT.getFirstname())
                .setLastname(customerDtoRABBIT.getLastname())
                .setAddress(customerDtoRABBIT.getAddress())
                .setEmailadress(customerDtoRABBIT.getEmailadress())
                .setPhonenumber(customerDtoRABBIT.getPhonenumber())
                .build();
    }

    public CustomerDtoRABBIT toCustomerDto(Customer customer) {
        return CustomerFactory.buildCustomer()
                .setAddress(customer.getAddress())
                .setFirstname(customer.getFirstname())
                .setLastname(customer.getLastname())
                .setAddress(customer.getAddress())
                .setEmailadress(customer.getEmailadress())
                .setPhonenumber(customer.getPhonenumber())
                .buildCustomerDto();
    }

}
