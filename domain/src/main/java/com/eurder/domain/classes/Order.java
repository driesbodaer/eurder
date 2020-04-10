package com.eurder.domain.classes;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Order {
    private final int id;
    private List<ItemGroup> itemGroupList;
    private final Price totalPrice;
    private Customer customer;
    private static int counter = 100;


    public Order(List<ItemGroup> itemGroupList, Customer customer) {
        this.id = counter++;
        this.itemGroupList = new ArrayList<>(itemGroupList);
        this.totalPrice = calculateTotalPrice();
        this.customer = customer;
    }

    public Price calculateTotalPrice() {
        double price = 0;
        for (ItemGroup itemGroup : itemGroupList) {
            price += itemGroup.getPrice();
        }
        return new Price(price, "eur");
    }

    public int getId() {
        return id;
    }

    public List<ItemGroup> getItemGroupList() {
        return itemGroupList;
    }

    public Customer getCustomer() {
        return customer;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return Objects.equals(itemGroupList, order.itemGroupList) &&
                Objects.equals(totalPrice, order.totalPrice) &&
                Objects.equals(customer, order.customer);
    }

    @Override
    public int hashCode() {
        return Objects.hash(itemGroupList, totalPrice, customer);
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", itemGroupList=" + itemGroupList +
                ", totalPrice=" + totalPrice +
                ", customer=" + customer +
                '}';
    }
}
