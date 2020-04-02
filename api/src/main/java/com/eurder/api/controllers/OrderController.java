package com.eurder.api.controllers;

import com.eurder.domain.classes.Customer;
import com.eurder.domain.classes.Order;
import com.eurder.domain.dto.CustomerDto;
import com.eurder.domain.dto.OrderDto;
import com.eurder.service.CustomerService;
import com.eurder.service.ItemService;
import com.eurder.service.NotEverythingFilledInExeption;
import com.eurder.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

import static com.eurder.api.controllers.CustomerController.JSON;


@RestController
@RequestMapping(path = "orders")
public class OrderController {
    private final Logger LOGGER = LoggerFactory.getLogger(OrderController.class);
    private final com.eurder.service.OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping(produces = JSON , consumes = JSON)
    @ResponseStatus(HttpStatus.OK)
    public Order placeOrder(@RequestBody OrderDto orderDto) {
        return orderService.placeOrder(orderDto);
    }

    public OrderService getOrderService() {
        return orderService;
    }


    @ExceptionHandler(NotEverythingFilledInExeption.class)
    protected void noteverythingfilledinexeption(NotEverythingFilledInExeption e, HttpServletResponse response) throws IOException {
        LOGGER.error(e.getMessage());
        response.sendError(403, "some fields are empty");

    }
}
