package com.eurder.domain.mapper;

import com.eurder.domain.classes.ItemGroup;
import com.eurder.domain.classes.Order;
import com.eurder.domain.dto.OrderDto;
import com.eurder.domain.repository.CustomerRepository;
import com.eurder.domain.repository.ItemRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest(classes = {OrderMapper.class, ItemRepository.class, ItemGroupMapper.class, CustomerRepository.class})
class OrderMapperTest {

    ItemGroupMapper itemGroupMapper;
    ItemRepository itemRepository;
    CustomerRepository customerRepository;
    OrderMapper orderMapper;

    @Autowired
    public OrderMapperTest(ItemGroupMapper itemGroupMapper, ItemRepository itemRepository, OrderMapper orderMapper, CustomerRepository customerRepository) {
        this.itemGroupMapper = itemGroupMapper;
        this.itemRepository = itemRepository;
        this.orderMapper = orderMapper;
        this.customerRepository = customerRepository;
    }

    private Order getOrder()  {
        return new Order(List.of(new ItemGroup(itemRepository.getItemList().get(0), 2, true)), customerRepository.getCustomerList().get(0));
    }

    private OrderDto getOrderDto()  {
        return new OrderDto(List.of(itemGroupMapper.toItemGroupDto(new ItemGroup(itemRepository.getItemList().get(0), 2, true))));
    }

    @Test
    void toOrder() {
        Assertions.assertEquals(orderMapper.toOrderDto(getOrder()), getOrderDto());
    }


}