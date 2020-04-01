package com.eurder.domain.classes;

import java.util.ArrayList;
import java.util.List;

public class Order {
    private final int id;
    private List<ItemGroup> itemGroupList;
    private double totalPrice;
    private Customer customer;
    private static int counter=1;

    public Order(List<ItemGroup> itemGroupList, double totalPrice, Customer customer) {
        this.id = counter++;
        this.itemGroupList = new ArrayList<>(itemGroupList);
        this.totalPrice = totalPrice;
        this.customer = customer;
    }
}
