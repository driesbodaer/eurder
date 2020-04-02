package com.eurder.domain.dto;

import com.eurder.domain.classes.Customer;
import com.eurder.domain.classes.ItemGroup;
import com.eurder.domain.classes.Price;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class OrderDto {
    private List<ItemGroup> itemGroupList;
    private Price totalPrice;
    private Customer customer;

    public OrderDto(List<ItemGroup> itemGroupList, Price totalPrice, Customer customer) {
        this.itemGroupList = new ArrayList<>(itemGroupList);
        this.totalPrice = totalPrice;
        this.customer = customer;
    }

    public List<ItemGroup> getItemGroupList() {
        return itemGroupList;
    }

    public Price getTotalPrice() {
        return totalPrice;
    }

    public Customer getCustomer() {
        return customer;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderDto orderDto = (OrderDto) o;
        return Objects.equals(itemGroupList, orderDto.itemGroupList) &&
                Objects.equals(totalPrice, orderDto.totalPrice) &&
                Objects.equals(customer, orderDto.customer);
    }

    @Override
    public int hashCode() {
        return Objects.hash(itemGroupList, totalPrice, customer);
    }
}
