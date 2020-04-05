package com.eurder.domain.classes;

import org.springframework.stereotype.Component;

import java.util.Objects;


public class Item {
    private String name;
    private String description;
    private Price price;
    private int amount;
    private Urgency urgency;

    public Item(String name, String description, Price price, int amount) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.amount = amount;
        calculateUrgency();
    }

    public Urgency getUrgency() {
        return urgency;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPrice(Price price) {
        this.price = price;
    }

    public void setUrgency(Urgency urgency) {
        this.urgency = urgency;
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


    public Price getPrice() {
        return price;
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

    public void setAmount(int amount) {
        this.amount = amount;
        calculateUrgency();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Item item = (Item) o;
        return amount == item.amount &&
                Objects.equals(name, item.name) &&
                Objects.equals(description, item.description) &&
                Objects.equals(price, item.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, description, price, amount);
    }

    @Override
    public String toString() {
        return "Item{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", amount=" + amount +
                '}';
    }
}
