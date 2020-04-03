package com.eurder.domain.classes;

public enum Urgency {
    STOCK_LOW(1),
    STOCK_MEDIUM(2),
    STOCK_HIGH(3);

    int hierrachy;
    Urgency(int  hierrachy) {
        this.hierrachy = hierrachy;
    }

    public int gethierarchy(){
        return hierrachy;
    }
}
