package com.eurder.api.controllers;

import com.eurder.domain.classes.Customer;
import com.eurder.domain.classes.ItemGroupWithadress;
import com.eurder.domain.classes.Order;
import com.eurder.domain.classes.Urgency;
import com.eurder.domain.dto.CustomerDto;
import com.eurder.domain.dto.ItemDto;
import com.eurder.domain.dto.OrderDto;
import com.eurder.domain.dto.ReportDto;
import com.eurder.service.CustomerService;
import com.eurder.service.ItemService;
import com.eurder.service.NotEverythingFilledInExeption;
import com.eurder.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.security.Principal;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.eurder.api.controllers.CustomerController.JSON;


@RestController
@RequestMapping(path = "orders")
public class OrderController {
    private final Logger LOGGER = LoggerFactory.getLogger(OrderController.class);
    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping(produces = JSON, consumes = JSON)
    @ResponseStatus(HttpStatus.CREATED)
    public OrderDto placeOrder(@RequestBody OrderDto orderDto, Principal principal) {
        return orderService.placeOrder(orderDto, principal.getName());
    }

    @PostMapping(produces = JSON, consumes = JSON, params = {"orderID"})
    @ResponseStatus(HttpStatus.CREATED)
    public Order placeOrderById(Principal principal, @RequestParam("orderID") int orderID) {
        if (orderService.getCustomerRepository().getCustomerList().stream().filter(x -> x.getFirstname().equals(principal.getName())).findFirst().orElse(null) ==
                orderService.getOrderRepository().getOrderList().stream().filter(x->x.getId() == orderID).findFirst().orElse(null).getCustomer()) {
            return orderService.placeExistingOrder(orderID, principal.getName());
        }else throw new NotCorrectUserException("you cannot access other customers data");
    }

    @GetMapping(produces = JSON)
    @ResponseStatus(HttpStatus.OK)
    public List<OrderDto> getAllOrders() {
        return orderService.getAllOrders();
    }

    @GetMapping(produces = JSON, path = "shippingtoday")
    @PreAuthorize("hasAuthority('ADMIN_ONLY')")
    @ResponseStatus(HttpStatus.OK)
    public List<ItemGroupWithadress> getOrdersToShipToday() {
        return orderService.getToShipToday();
    }

    @GetMapping(produces = JSON, path = "{id}")
    @ResponseStatus(HttpStatus.OK)
    public ReportDto getAllOrdersByCustomer(@PathVariable int id, Principal principal ) {
        if (principal.getName().equals(Objects.requireNonNull(orderService.getCustomerRepository().getCustomerList().stream().filter(x -> x.getId() == id).findFirst().orElse(null)).getFirstname())) {
            return orderService.getOrderByID(id);
        }else throw new NotCorrectUserException("you cannot access other customers data");
    }

    public OrderService getOrderService() {
        return orderService;
    }

    @ExceptionHandler(NotEverythingFilledInExeption.class)
    protected void noteverythingfilledinexeption(NotEverythingFilledInExeption e, HttpServletResponse response) throws IOException {
        LOGGER.error(e.getMessage());
        response.sendError(403, "some fields are empty");

    }

    @ExceptionHandler(NotCorrectUserException.class)
    protected void notCorrectUserExeptoin(NotCorrectUserException e, HttpServletResponse response) throws IOException {
        LOGGER.error(e.getMessage());
        response.sendError(403, "only have acces to your own data");

    }

}
