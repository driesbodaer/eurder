package com.eurder.api.controllers;

import com.eurder.domain.classes.Customer;
import com.eurder.domain.dto.CustomerDto;
import com.eurder.domain.mapper.CustomerMapper;
import com.eurder.domain.repository.CustomerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "customers")
public class CustomerController {
    public static final String JSON = "application/json";
    private final Logger LOGGER = LoggerFactory.getLogger(CustomerController.class);
    private final CustomerMapper customerMapper;
    private final CustomerRepository customerRepository;

    @Autowired
    public CustomerController(CustomerMapper customerMapper, CustomerRepository customerRepository) {
        this.customerMapper = customerMapper;
        this.customerRepository = customerRepository;
    }

    @PostMapping (produces = JSON , consumes = JSON)
    @ResponseStatus(HttpStatus.OK)
    public Customer createCostumer(@RequestBody CustomerDto customerDto) {
        Customer customer = customerMapper.toCustomer(customerDto);
        customerRepository.addCustomer(customer);
      return customer;
    }
}
