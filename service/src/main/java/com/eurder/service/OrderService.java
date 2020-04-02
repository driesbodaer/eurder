package com.eurder.service;

import com.eurder.domain.classes.Order;
import com.eurder.domain.dto.ItemDto;
import com.eurder.domain.dto.OrderDto;
import com.eurder.domain.mapper.OrderMapper;
import com.eurder.domain.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderService {
    private OrderMapper orderMapper;
    private OrderRepository orderRepository;

    @Autowired
    public OrderService(OrderMapper orderMapper, OrderRepository orderRepository) {
        this.orderMapper = orderMapper;
        this.orderRepository = orderRepository;
    }

    public Order placeOrder(OrderDto orderDto) {
        if (hasAnyEmptyFields(orderDto)) {
            throw new NotEverythingFilledInExeption("fill in everything");
        }
        Order order = orderMapper.toOrder(orderDto);
        orderRepository.placeOrder(order);
        return order;
    }

    public boolean hasAnyEmptyFields(OrderDto orderDto) {
        return orderDto.getItemGroupList().isEmpty() || orderDto.getCustomer() == null || orderDto.getTotalPrice().getPrice() == 0.0;
    }

    public OrderRepository getOrderRepository() {
        return orderRepository;
    }
}
