package com.eurder.service;

import com.eurder.domain.classes.Customer;
import com.eurder.domain.classes.Order;
import com.eurder.domain.dto.OrderDto;
import com.eurder.domain.mapper.OrderMapper;
import com.eurder.domain.repository.CustomerRepository;
import com.eurder.domain.repository.ItemRepository;
import com.eurder.domain.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.function.ServerRequest;

import java.util.List;

@Service
public class OrderService {
    private final OrderMapper orderMapper;
    private final OrderRepository orderRepository;
    private final ItemRepository itemRepository;
    private final CustomerRepository customerRepository;


    @Autowired
    public OrderService(OrderMapper orderMapper, OrderRepository orderRepository, ItemRepository itemRepository, CustomerRepository customerRepository) {
        this.orderMapper = orderMapper;
        this.orderRepository = orderRepository;
        this.itemRepository = itemRepository;
        this.customerRepository = customerRepository;
    }

    public Order placeOrder(OrderDto orderDto, String username) {
        if (hasAnyEmptyFields(orderDto)) {
            throw new NotEverythingFilledInExeption("fill in everything");
        }

        Customer customerThatOrdered = customerRepository.getCustomerBasedOnName(username);
        Order order = orderMapper.toOrder(orderDto, customerThatOrdered);
        orderRepository.placeOrder(order);
        return order;
    }


    public boolean hasAnyEmptyFields(OrderDto orderDto) {
        return orderDto.getItemGroupDtoList().isEmpty();
    }

    public OrderRepository getOrderRepository() {
        return orderRepository;
    }


    public List<Order> getAllOrders() {
        return orderRepository.getOrderList();
    }
}
