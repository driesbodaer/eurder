package com.eurder.domain.dto;

import com.eurder.domain.classes.Price;
import com.eurder.domain.classes.Urgency;

import java.util.Objects;

public class ItemDto {
    private String name;
    private String description;
    private Price price;
    private int amount;
    private Urgency urgency;

    public ItemDto(String name, String description, Price price, int amount) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.amount = amount;
        calculateUrgency();
    }

    public Urgency getUrgency() {
        return urgency;
    }

    public void calculateUrgency() {
        if (amount <= 10 && amount > 5) {
            this.urgency = Urgency.STOCK_MEDIUM;
            return;
        }
        if (amount <= 5) {
            this.urgency = Urgency.STOCK_LOW;
            return;
        }
        this.urgency = Urgency.STOCK_HIGH;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getAmount() {
        return amount;
    }

    public Price getPrice() {
        return price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ItemDto itemDto = (ItemDto) o;
        return amount == itemDto.amount &&
                Objects.equals(name, itemDto.name) &&
                Objects.equals(description, itemDto.description) &&
                Objects.equals(price, itemDto.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, description, price, amount);
    }
}
