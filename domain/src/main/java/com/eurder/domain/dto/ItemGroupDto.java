package com.eurder.domain.dto;

import com.eurder.domain.classes.Item;
import com.fasterxml.jackson.annotation.JsonCreator;
import org.springframework.stereotype.Component;

import java.util.Objects;


public class ItemGroupDto {
    private Item item;
    private int amount;
    private double price;

    public ItemGroupDto(Item item, int amount) {
        this.item = item;
        this.amount = amount;
        this.price = item.getPrice().getPrice() * amount;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getPrice() {
        return price;
    }

    public Item getItem() {
        return item;
    }

    public int getAmount() {
        return amount;
    }

    @Override
    public String toString() {
        return "ItemGroupDto{" +
                "item=" + item +
                ", amount=" + amount +
                ", price=" + price +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ItemGroupDto that = (ItemGroupDto) o;
        return amount == that.amount &&
                Double.compare(that.price, price) == 0 &&
                Objects.equals(item, that.item);
    }

    @Override
    public int hashCode() {
        return Objects.hash(item, amount, price);
    }
}
