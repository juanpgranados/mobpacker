package com.mobiquity.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class Item implements Serializable {

    private final int index;
    private final BigDecimal weight;
    private final BigDecimal cost;

}
