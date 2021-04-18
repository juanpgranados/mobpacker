package com.mobiquity.validator;

import com.mobiquity.exception.APIException;
import com.mobiquity.model.Item;
import com.mobiquity.model.PackerInput;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class TestInputValidator {
    @Test
    void testInvalidMaxWeight() {
        // Given
        List<Item> itemList = new ArrayList<>();
        itemList.add(new Item(1, new BigDecimal("53.38"), new BigDecimal("45")));
        // When
        APIException apiException = assertThrows(APIException.class, () -> {
            InputValidator.validate(new PackerInput(new BigInteger("101"), itemList));
        });
        //Then
        assertEquals("Invalid max weight 101",apiException.getMessage());
    }

    @Test
    void testInvalidNumberOfItems() {
        // Given
        List<Item> itemList = new ArrayList<>();
        for(int i=1; i<17;i++){
            itemList.add(new Item(i, new BigDecimal("53.38"), new BigDecimal("45")));
        }
        // When
        APIException apiException = assertThrows(APIException.class, () -> {
            InputValidator.validate(new PackerInput(new BigInteger("50"), itemList));
        });
        //Then
        assertEquals("Items quantity exceed max permitted",apiException.getMessage());
    }

    @Test
    void testInvalidCost() {
        // Given
        List<Item> itemList = new ArrayList<>();
        itemList.add(new Item(1, new BigDecimal("53.38"), new BigDecimal("150")));
        // When
        APIException apiException = assertThrows(APIException.class, () -> {
            InputValidator.validate(new PackerInput(new BigInteger("50"), itemList));
        });
        //Then
        assertEquals("Invalid item cost",apiException.getMessage());
    }

    @Test
    void testInvalidWeight() {
        // Given
        List<Item> itemList = new ArrayList<>();
        itemList.add(new Item(1, new BigDecimal("153.38"), new BigDecimal("40")));
        // When
        APIException apiException = assertThrows(APIException.class, () -> {
            InputValidator.validate(new PackerInput(new BigInteger("50"), itemList));
        });
        //Then
        assertEquals("Invalid item weight",apiException.getMessage());
    }

    @Test
    void testDuplicatedIndex() {
        // Given
        List<Item> itemList = new ArrayList<>();
        itemList.add(new Item(1, new BigDecimal("53.38"), new BigDecimal("45")));
        itemList.add(new Item(2, new BigDecimal("27.53"), new BigDecimal("98")));
        itemList.add(new Item(1, new BigDecimal("88.62"), new BigDecimal("80")));
        // When
        APIException apiException = assertThrows(APIException.class, () -> {
            InputValidator.validate(new PackerInput(new BigInteger("50"), itemList));
        });
        //Then
        assertEquals("Invalid input, duplicated index",apiException.getMessage());
    }
}
