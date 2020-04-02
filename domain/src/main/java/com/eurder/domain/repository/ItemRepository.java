package com.eurder.domain.repository;

import com.eurder.domain.classes.Item;
import com.eurder.domain.classes.Price;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class ItemRepository {

    private List<Item> itemList;

    public ItemRepository() {
        this.itemList = new ArrayList<>(List.of(new Item("kaas", "camenbert", new Price(1.5, "eur"), 10)));
        itemList.add(new Item("brood", "rozijnen", new Price(2.5, "eur"), 20));
    }

    public void addItem(Item item) {
        itemList.add(item);
    }

    public List<Item> getItemList() {
        return itemList;
    }
}
