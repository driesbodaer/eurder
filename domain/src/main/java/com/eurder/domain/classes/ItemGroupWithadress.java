package com.eurder.domain.classes;

public class ItemGroupWithadress {
    ItemGroup itemGroup;
    String adress;

    public ItemGroupWithadress(ItemGroup itemGroup, String adress) {
        this.itemGroup = itemGroup;
        this.adress = adress;
    }

    public void setItemGroup(ItemGroup itemGroup) {
        this.itemGroup = itemGroup;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public ItemGroup getItemGroup() {
        return itemGroup;
    }

}
