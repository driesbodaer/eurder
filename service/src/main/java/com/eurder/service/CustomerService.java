package com.eurder.service;

import com.eurder.domain.classes.Customer;
import com.eurder.domain.dto.CustomerDto;
import com.eurder.domain.mapper.CustomerMapper;
import com.eurder.domain.repository.CustomerRepository;
import com.eurder.infrastructure.authentication.FakeAuthenticationService;
import com.eurder.infrastructure.eurderRoles.EurderRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {

    private final CustomerMapper customerMapper;
    private final CustomerRepository customerRepository;
    private final FakeAuthenticationService fakeAuthenticationService;

    @Autowired
    public CustomerService(CustomerMapper customerMapper, CustomerRepository customerRepository, FakeAuthenticationService fakeAuthenticationService) {
        this.customerMapper = customerMapper;
        this.customerRepository = customerRepository;
        this.fakeAuthenticationService = fakeAuthenticationService;
    }

    public Customer createCustomer(CustomerDto customerDto) {
        Customer customer = customerMapper.toCustomer(customerDto);
        customerRepository.addCustomer(customer);
        fakeAuthenticationService.addUser(customer.getFirstname(), "customer", List.of(EurderRole.CUSTOMER));
        return customer;
    }

    public CustomerRepository getCustomerRepository() {
        return customerRepository;
    }
}
