package com.eurder.service;

import com.eurder.domain.classes.Customer;
import com.eurder.domain.classes.Order;
import com.eurder.domain.dto.OrderDto;
import com.eurder.domain.mapper.OrderMapper;
import com.eurder.domain.repository.CustomerRepository;
import com.eurder.domain.repository.ItemRepository;
import com.eurder.domain.repository.OrderRepository;
import com.eurder.infrastructure.EurderAuthenticationEntryPoint;
import com.eurder.infrastructure.EurderAuthenticationProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

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

    public Order placeOrder(OrderDto orderDto) {
        if (hasAnyEmptyFields(orderDto)) {
            throw new NotEverythingFilledInExeption("fill in everything");
        }
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        Customer customerThatOrdered = customerRepository.getCustomerBasedOnName( auth.getName());
        Customer customerThatOrdered = customerRepository.getCustomerList().get(0);
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



}
