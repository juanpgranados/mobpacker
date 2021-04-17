package com.mobiquity.pojo;

import java.math.BigInteger;
import java.util.List;

public class PackerInput {
    final BigInteger maxWeight;
    final List<Item> itemList;

    public PackerInput(BigInteger maxWeight, List<Item> itemList) {
        this.itemList = itemList;
        this.maxWeight = maxWeight;
    }

    public List<Item> getItemList() {
        return itemList;
    }

    public BigInteger getMaxWeight() {
        return maxWeight;
    }
}
