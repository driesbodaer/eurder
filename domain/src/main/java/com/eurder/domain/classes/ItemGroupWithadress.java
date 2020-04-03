package com.eurder.domain.classes;

import java.util.List;

public class ItemGroupWithadress {
    ItemGroup itemGroup;
    String adress;

    public ItemGroupWithadress(ItemGroup itemGroup, String adress) {
        this.itemGroup = itemGroup;
        this.adress = adress;
    }

    public ItemGroup getItemGroup() {
        return itemGroup;
    }

    public String getAdress() {
        return adress;
    }
}
