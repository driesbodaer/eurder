package com.eurder.domain.repository;

import com.eurder.domain.classes.Item;
import com.eurder.domain.classes.ItemGroup;
import com.eurder.domain.classes.Order;

import java.util.ArrayList;
import java.util.List;

public class OrderRepository {
    List<Order> orderList;

    public OrderRepository() {
        this.orderList = new ArrayList<>();
    }
}
