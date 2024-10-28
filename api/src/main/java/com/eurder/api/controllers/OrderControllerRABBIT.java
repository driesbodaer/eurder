package com.eurder.api.controllers;
import com.eurder.domain.classes.ItemGroupWithadress;
import com.eurder.domain.classes.Order;
import com.eurder.domain.dto.OrderDto;
import com.eurder.domain.dto.ReportDto;
import com.eurder.service.NotCorrectUserException;
import com.eurder.service.NotEverythingFilledInExeption;
import com.eurder.service.OrderServiceRABBIT;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.Principal;
import java.util.List;
import java.util.Objects;
import static com.eurder.api.controllers.CustomerControllerRABBIT.JSON;


@RestController
@RequestMapping(path = "orders")
public class OrderControllerRABBIT {
    private final Logger LOGGER = LoggerFactory.getLogger(OrderControllerRABBIT.class);
    private final OrderServiceRABBIT orderServiceRABBIT;

    @Autowired
    public OrderControllerRABBIT(OrderServiceRABBIT orderServiceRABBIT) {
        this.orderServiceRABBIT = orderServiceRABBIT;
    }

    @PostMapping(produces = JSON, consumes = JSON)
    @ResponseStatus(HttpStatus.CREATED)
    public OrderDto placeOrder(@RequestBody OrderDto orderDto, Principal principal) {
        return orderServiceRABBIT.placeOrder(orderDto, principal.getName());
    }

    @PostMapping(produces = JSON, consumes = JSON, params = {"orderID"})
    @ResponseStatus(HttpStatus.CREATED)
    public Order placeOrderById(Principal principal, @RequestParam("orderID") int orderID) {
        if (orderServiceRABBIT.getCustomer(principal) == orderServiceRABBIT.getOrder(orderID).getCustomer()) {
            return orderServiceRABBIT.placeExistingOrder(orderID, principal.getName());
        } else throw new NotCorrectUserException("you cannot access other customers data");
    }

    @GetMapping(produces = JSON)
    @ResponseStatus(HttpStatus.OK)
    public List<OrderDto> getAllOrders() {
        return orderServiceRABBIT.getAllOrders();
    }

    @GetMapping(produces = JSON, path = "shippingtoday")
    @PreAuthorize("hasAuthority('ADMIN_ONLY')")
    @ResponseStatus(HttpStatus.OK)
    public List<ItemGroupWithadress> getOrdersToShipToday() {
        return orderServiceRABBIT.getToShipToday();
    }

    @GetMapping(produces = JSON, path = "{id}")
    @ResponseStatus(HttpStatus.OK)
    public ReportDto getAllOrdersByCustomer(@PathVariable int id, Principal principal) {
        if (loggedInCustomerOwnsData(id, principal)) {
            System.out.println(orderServiceRABBIT.getOrderReportByID(id));
            return orderServiceRABBIT.getOrderReportByID(id);
        } else throw new NotCorrectUserException("you cannot access other customers data");
    }

    private boolean loggedInCustomerOwnsData(@PathVariable int id, Principal principal) {
        return principal.getName().equals(Objects.requireNonNull(orderServiceRABBIT.getCustomerById(id)).getFirstname());
    }

    public OrderServiceRABBIT getOrderService() {
        return orderServiceRABBIT;
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
