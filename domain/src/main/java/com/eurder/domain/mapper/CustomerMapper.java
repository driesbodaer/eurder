package com.eurder.domain.mapper;

import com.eurder.domain.classes.Customer;
import com.eurder.domain.dto.CustomerDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CustomerMapper {

    public Customer toCustomer(CustomerDto customerDto) {
        return CustomerFactory.buildCustomer()
                .setAddress(customerDto.getAddress())
                .setFirstname(customerDto.getFirstname())
                .setLastname(customerDto.getLastname())
                .setAddress(customerDto.getAddress())
                .setEmailadress(customerDto.getEmailadress())
                .setPhonenumber(customerDto.getPhonenumber())
                .build();
    }

    public CustomerDto toCustomerDto(Customer customer) {
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
