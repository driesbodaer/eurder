package com.eurder.domain.repository;

import com.eurder.domain.classes.Item;

import java.util.List;

public class ItemRepository {

    private List<Item> itemList;

    public ItemRepository() {
        this.itemList = List.of(new Item("kaas", "camenbert", 1.5, 10));
    }
}
