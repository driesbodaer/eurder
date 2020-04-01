package com.eurder.domain.classes;

import java.time.LocalDate;

public class ItemGroup {
    private Item item;
    private int amount;
    private LocalDate shippingdate;
    private double price;

    public ItemGroup(Item item, int amount) {
        this.item = item;
        this.amount = amount;
        this.price = item.getPrice() * amount;
        this.shippingdate = LocalDate.now().plusDays(1);
    }


}
