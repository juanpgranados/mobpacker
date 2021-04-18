package com.mobiquity.knapsack;

import com.mobiquity.model.Item;
import com.mobiquity.model.PackerInput;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

class TestDynamicProgrammingProcessor {
    @Test
    void testSolveKnapsack(){
        //Given
        KnapsackProcessor knapsackProcessor = new DynamicProgrammingProcessor();
        String expectedSolution = "";
        List<Item> itemList = new ArrayList<>();
        itemList.add(new Item(1, new BigDecimal("53.38"), new BigDecimal("45")));
        itemList.add(new Item(2, new BigDecimal("27.53"), new BigDecimal("98")));
        itemList.add(new Item(3, new BigDecimal("88.62"), new BigDecimal("80")));
        //When
        String currentSolution = knapsackProcessor.solve(new PackerInput(new BigInteger("81"), itemList));
        //Then
        assertEquals("1,2",currentSolution);
    }

    private void assertEquals(String s, String currentSolution) {
    }

    @Test
    void testSolveKnapsackBestGainBetweenTwoSolutions(){
        //Given
        KnapsackProcessor knapsackProcessor = new DynamicProgrammingProcessor();
        List<Item> itemList = new ArrayList<>();
        itemList.add(new Item(1, new BigDecimal("53.38"), new BigDecimal("98")));
        itemList.add(new Item(2, new BigDecimal("80.62"), new BigDecimal("98")));
        itemList.add(new Item(3, new BigDecimal("27.53"), new BigDecimal("98")));
        //When
        String currentSolution = knapsackProcessor.solve(new PackerInput(new BigInteger("81"), itemList));
        //Then
        assertEquals("1,3", currentSolution);
    }
}
