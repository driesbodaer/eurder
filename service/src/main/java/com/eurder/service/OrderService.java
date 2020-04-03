package com.eurder.service;

import com.eurder.domain.classes.Customer;
import com.eurder.domain.classes.Order;
import com.eurder.domain.dto.OrderDto;
import com.eurder.domain.dto.ReportDto;
import com.eurder.domain.mapper.OrderMapper;
import com.eurder.domain.repository.CustomerRepository;
import com.eurder.domain.repository.ItemRepository;
import com.eurder.domain.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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

    public OrderDto placeOrder(OrderDto orderDto, String username)  {
        if (hasAnyEmptyFields(orderDto)) {
            throw new NotEverythingFilledInExeption("fill in everything");
        }

        Customer customerThatOrdered = customerRepository.getCustomerBasedOnName(username);
        Order order = orderMapper.toOrder(orderDto, customerThatOrdered);
        customerThatOrdered.getReportDto().addOrder(order);
        orderRepository.placeOrder(order);
        return orderDto;
    }


    public boolean hasAnyEmptyFields(OrderDto orderDto) {
        return orderDto.getItemGroupDtoList().isEmpty();
    }

    public OrderRepository getOrderRepository() {
        return orderRepository;
    }


    public List<OrderDto> getAllOrders() {
        ArrayList<OrderDto> returnList = new ArrayList<>();
        for (Order order : orderRepository.getOrderList()) {
            returnList.add(orderMapper.toOrderDto(order));
        }
        return returnList;
    }

    public CustomerRepository getCustomerRepository() {
        return customerRepository;
    }

    public ReportDto getOrderByID(int id) {
        return Objects.requireNonNull(customerRepository.getCustomerList().stream().filter(x -> x.getId() == id).findFirst().orElse(null)).getReportDto();
    }
}
