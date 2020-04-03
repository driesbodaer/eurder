package com.eurder.api.controllers;

import com.eurder.domain.dto.CustomerDto;
import com.eurder.service.CustomerService;
import com.eurder.service.NotEverythingFilledInExeption;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping(path = "customers")
public class CustomerController {
    public static final String JSON = "application/json";
    private final Logger LOGGER = LoggerFactory.getLogger(CustomerController.class);
    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping(produces = JSON, consumes = JSON)
    @ResponseStatus(HttpStatus.CREATED)
    public CustomerDto createCostumer(@RequestBody CustomerDto customerDto) {
        return customerService.createCustomer(customerDto);
    }

    @GetMapping(produces = JSON)
    @ResponseStatus(HttpStatus.OK)
    public List<CustomerDto> getAllCustomer() {
        return customerService.getAllCustomers();
    }

    @GetMapping(produces = JSON, path = "{id}")
    @ResponseStatus(HttpStatus.OK)
    public CustomerDto getCustomer(@PathVariable int id) {
        return customerService.getCustomer(id);
    }

    public CustomerService getCustomerService() {
        return customerService;
    }

    @ExceptionHandler(NotEverythingFilledInExeption.class)
    protected void noteverythingfilledinexeption(NotEverythingFilledInExeption e, HttpServletResponse response) throws IOException {
        LOGGER.error(e.getMessage());
        response.sendError(403, "some fields are empty");

    }
}
