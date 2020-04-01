package com.eurder.api.controllers;

import com.eurder.domain.classes.Customer;
import com.eurder.domain.dto.CustomerDto;
import com.eurder.service.CustomerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "customers")
public class CustomerController {
    public static final String JSON = "application/json";
    private final Logger LOGGER = LoggerFactory.getLogger(CustomerController.class);
    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping (produces = JSON , consumes = JSON)
    @ResponseStatus(HttpStatus.OK)
    public Customer createCostumer(@RequestBody CustomerDto customerDto) {
       return customerService.createCustomer(customerDto);
    }

    public CustomerService getCustomerService() {
        return customerService;
    }
}
