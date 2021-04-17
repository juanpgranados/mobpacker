package com.mobiquity.knapsack;

import com.mobiquity.pojo.Item;
import com.mobiquity.pojo.PackerInput;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class DynamicProgrammingProcessor implements KnapsackProcessor {

    @Override
    public String solve(PackerInput packerInput) {
        int items = packerInput.getItemList().size();
        int maxW = packerInput.getMaxWeight().intValue();
        List<Item> sortedItems = packerInput.getItemList().stream().sorted(Comparator.comparing(Item::getWeight)).
                collect(Collectors.toList());
        BigDecimal[][] solution = new BigDecimal[items+1][maxW+1];
        //init first row
        for (int j = 0; j <= maxW; j++) {
            solution[0][j] = new BigDecimal(0);
        }
        for (int i = 1; i <= items; i++) {
            for (int j = 1; j <= maxW; j++) {
                BigDecimal bdJ = new BigDecimal(j)
                if (sortedItems.get(i-1).getWeight().compareTo(bdJ) == 1) {//if item cannot be put into
                    solution[i][j] = solution[i - 1][j];
                } else {
                    BigDecimal bdWeightLeft = bdJ.subtract(sortedItems.get(i-1).getWeight());
                    bdWeightLeft.setScale();
                    solution[i][j] = solution[i - 1][j]
                            .max(solution[i - 1][bdWeightLeft.intValue()].add(sortedItems.get(i-1).getCost()));
                }
            }
        }

        return "x";
    }
}
