package com.eurder.domain.mapper;

import com.eurder.domain.classes.Item;
import com.eurder.domain.classes.Order;
import com.eurder.domain.dto.ItemDto;
import com.eurder.domain.dto.OrderDto;
import org.springframework.stereotype.Component;

@Component
public class OrderMapper {

    public Order toOrder(OrderDto orderDto) {
        return new Order(orderDto.getItemGroupList(), orderDto.getTotalPrice(), orderDto.getCustomer());
    }
    public OrderDto toOrderDto(Order order) {
        return new OrderDto(order.getItemGroupList(), order.getTotalPrice(), order.getCustomer());
    }
}
