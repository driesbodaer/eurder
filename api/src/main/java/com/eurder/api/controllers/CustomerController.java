package com.eurder.api.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "customers")
public class CustomerController {
    private final Logger LOGGER = LoggerFactory.getLogger(CustomerController.class);
}
