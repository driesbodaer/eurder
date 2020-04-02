package com.eurder.domain.classes;

import java.time.LocalDate;
import java.util.Objects;

public class ItemGroup {
    private Item item;
    private int amount;
    private LocalDate shippingdate;
    private double price;
    private boolean inStock;

    public ItemGroup(Item item, int amount, boolean inStock) {
        this.item = item;
        this.amount = amount;
        this.price = item.getPrice().getPrice() * amount;
        this.inStock = inStock;
        this.shippingdate = getShippingdate();
    }

    private LocalDate getShippingdate() {
        if (inStock){
        return LocalDate.now().plusDays(1);}
        return LocalDate.now().plusDays(7);
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ItemGroup itemGroup = (ItemGroup) o;
        return amount == itemGroup.amount &&
                Double.compare(itemGroup.price, price) == 0 &&
                inStock == itemGroup.inStock &&
                Objects.equals(item, itemGroup.item) &&
                Objects.equals(shippingdate, itemGroup.shippingdate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(item, amount, shippingdate, price, inStock);
    }

    @Override
    public String toString() {
        return "ItemGroup{" +
                "item=" + item +
                ", amount=" + amount +
                ", shippingdate=" + shippingdate +
                ", price=" + price +
                ", inStock=" + inStock +
                '}';
    }
}
