package com.mobiquity.knapsack;

import com.mobiquity.model.Item;
import com.mobiquity.model.PackerInput;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;
import java.util.TreeSet;
import java.util.stream.Collectors;

/**
 * DynamicProgrammingProcessor is an implementation of Dynamic programming for solving the knapsack problem
 */
public class DynamicProgrammingProcessor implements KnapsackProcessor {

    @Override
    public String solve(PackerInput packerInput) {
        int items = packerInput.getItemList().size();
        int maxW = packerInput.getMaxWeight().intValue();
        List<Item> sortedItems = packerInput.getItemList().stream().sorted(Comparator.comparing(Item::getWeight)).
                collect(Collectors.toList());
        BigDecimal[][] solution = new BigDecimal[items+1][maxW+1];
        BigDecimal[][] packageWeight = new BigDecimal[items+1][maxW+1];
        //init first row
        for (int j = 0; j <= maxW; j++) {
            solution[0][j] = new BigDecimal(0);
            packageWeight[0][j] = new BigDecimal(0);
        }//init first column
        for (int i = 0; i <= items; i++) {
            solution[i][0] = new BigDecimal(0);
            packageWeight[i][0] = new BigDecimal(0);
        }
        for (int i = 1; i <= items; i++) {
            for (int j = 1; j <= maxW; j++) {
                BigDecimal bdJ = new BigDecimal(j);
                if (sortedItems.get(i - 1).getWeight().compareTo(bdJ) > 0) {//if item cannot be put into the package
                    solution[i][j] = solution[i - 1][j];
                    packageWeight[i][j] = packageWeight[i - 1][j];
                } else {
                    BigDecimal weightLeft = bdJ.subtract(sortedItems.get(i-1).getWeight());
                    BigDecimal weightLeftSolution, usedWeight;
                    if(packageWeight[i - 1][weightLeft.intValue()+1].compareTo(weightLeft)<=0){
                        weightLeftSolution = solution[i - 1][weightLeft.intValue()+1];
                        usedWeight = packageWeight[i - 1][weightLeft.intValue()+1];
                    }else{
                        weightLeftSolution = solution[i - 1][weightLeft.intValue()];
                        usedWeight = packageWeight[i - 1][weightLeft.intValue()];
                    }
                    solution[i][j] = solution[i - 1][j]
                            .max(weightLeftSolution.add(sortedItems.get(i-1).getCost()));
                    if(solution[i][j].compareTo(solution[i - 1][j])==0){
                        packageWeight[i][j] = packageWeight[i - 1][j];
                    }else{
                        packageWeight[i][j] = usedWeight.add(sortedItems.get(i-1).getWeight());
                    }
                }
            }
        }
        if(solution[items][maxW].compareTo(BigDecimal.ZERO) == 0){
            return "-";
        }
        int n = items;
        int k = maxW;
        TreeSet<Integer> packageItems = new TreeSet<>();
        while (n>0 && k>0){
            if(solution[n][k].compareTo(solution[n-1][k]) != 0){
                packageItems.add(sortedItems.get(n-1).getIndex());
                BigDecimal weightLeft = (new BigDecimal(k)).subtract(sortedItems.get(n-1).getWeight());
                if(packageWeight[n - 1][weightLeft.intValue()+1].compareTo(weightLeft)<=0){
                    k = weightLeft.intValue()+1;
                }else {
                    k = weightLeft.intValue();
                }
            }
            n--;
        }
        return packageItems.stream().map(index->index.toString()).collect(Collectors.joining(","));
    }
}
