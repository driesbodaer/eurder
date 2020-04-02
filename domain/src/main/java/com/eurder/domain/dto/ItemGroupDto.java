package com.eurder.domain.dto;

import com.eurder.domain.classes.Item;

import java.time.LocalDate;

public class ItemGroupDto {
    private Item item;
    private int amount;
    private double price;

    public ItemGroupDto(Item item, int amount) {
        this.item = item;
        this.amount = amount;
        this.price = item.getPrice().getPrice() * amount;
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

}
