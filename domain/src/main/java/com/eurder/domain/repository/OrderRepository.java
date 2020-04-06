package com.eurder.domain.repository;

import com.eurder.domain.classes.Item;
import com.eurder.domain.classes.ItemGroup;
import com.eurder.domain.classes.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class OrderRepository {
    List<Order> orderList;
    ItemRepository itemRepository;
    CustomerRepository customerRepository;

    @Autowired
    public OrderRepository(ItemRepository itemRepository, CustomerRepository customerRepository) throws CloneNotSupportedException {
        this.customerRepository = customerRepository;
        this.itemRepository = itemRepository;
        this.orderList = new ArrayList<>();
        orderList.add(new Order(List.of(new ItemGroup(itemRepository.getItemList().get(0), 2, true)), customerRepository.getCustomerList().get(0)));
        orderList.get(0).calculateTotalPrice();
    }

    public List<Order> getOrderList() {
        return orderList;
    }

    public void placeOrder(Order order) {
        orderList.add(order);
    }
}
