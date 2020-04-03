package com.eurder.domain.mapper;

import com.eurder.domain.classes.Item;

import java.util.Comparator;

public class UrgencyComperator implements Comparator<Item> {

    @Override
    public int compare(Item o1, Item o2) {
        if (o1.getUrgency().gethierarchy() > o2.getUrgency().gethierarchy()) {
            return 1;
        }
        if (o1.getUrgency().gethierarchy() < o2.getUrgency().gethierarchy()) {
            return -1;
        }
        return 0;
    }
}
