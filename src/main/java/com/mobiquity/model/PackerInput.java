package com.mobiquity.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigInteger;
import java.util.List;

@Data
@AllArgsConstructor
public class PackerInput {
    private final BigInteger maxWeight;
    private final List<Item> itemList;
}
