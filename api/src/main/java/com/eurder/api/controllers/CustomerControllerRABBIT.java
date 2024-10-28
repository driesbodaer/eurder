package com.eurder.api.controllers;
import com.eurder.domain.dto.CustomerDtoRABBIT;
import com.eurder.service.CustomerService;
import com.eurder.service.NotEverythingFilledInExeption;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping(path = "customers")
public class CustomerControllerRABBIT {
    public static final String JSON = "application/json";
    private final Logger LOGGER = LoggerFactory.getLogger(CustomerControllerRABBIT.class);
    private final CustomerService customerService;

    @Autowired
    public CustomerControllerRABBIT(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping(produces = JSON, consumes = JSON)
    @ResponseStatus(HttpStatus.CREATED)
    public CustomerDtoRABBIT createCostumer(@RequestBody CustomerDtoRABBIT customerDtoRABBIT) {
        return customerService.createCustomer(customerDtoRABBIT);
    }

    @GetMapping(produces = JSON)
    @ResponseStatus(HttpStatus.OK)
    public List<CustomerDtoRABBIT> getAllCustomer() {
        return customerService.getAllCustomers();
    }

    @GetMapping(produces = JSON, path = "{id}")
    @ResponseStatus(HttpStatus.OK)
    public CustomerDtoRABBIT getCustomer(@PathVariable int id) {
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
