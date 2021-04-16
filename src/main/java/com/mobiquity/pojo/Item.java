package com.mobiquity.pojo;

import java.math.BigDecimal;

public class Item {

    private final int index;
    private final BigDecimal weight;
    private final BigDecimal cost;

    public Item(int index, BigDecimal weight, BigDecimal cost) {
        this.index = index;
        this.weight = weight;
        this.cost = cost;
    }

    public int getIndex() {
        return index;
    }

    public BigDecimal getWeight() {
        return weight;
    }

    public BigDecimal getCost() {
        return cost;
    }
}
