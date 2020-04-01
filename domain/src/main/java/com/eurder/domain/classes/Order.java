package com.eurder.domain.classes;

import java.util.ArrayList;
import java.util.List;

public class Order {
   private List<ItemGroup> itemGroupList;
   private double totalPrice;
   private Customer customer;

    public Order(List<ItemGroup> itemGroupList, double totalPrice, Customer customer) {
        this.itemGroupList = new ArrayList<>(itemGroupList);
        this.totalPrice = totalPrice;
        this.customer = customer;
    }
}
