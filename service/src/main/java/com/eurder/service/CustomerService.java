package com.eurder.service;

import com.eurder.domain.classes.Customer;
import com.eurder.domain.dto.CustomerDto;
import com.eurder.domain.mapper.CustomerMapper;
import com.eurder.domain.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {

    private final CustomerMapper customerMapper;
    private final CustomerRepository customerRepository;

    @Autowired
    public CustomerService(CustomerMapper customerMapper, CustomerRepository customerRepository) {
        this.customerMapper = customerMapper;
        this.customerRepository = customerRepository;
    }

    public Customer createCustomer(CustomerDto customerDto) {
        Customer customer = customerMapper.toCustomer(customerDto);
        customerRepository.addCustomer(customer);
        return customer;
    }

    public CustomerRepository getCustomerRepository() {
        return customerRepository;
    }
}
