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

    private BigDecimal[][] solution;
    private BigDecimal[][] packageWeight;

    @Override
    public String solve(PackerInput packerInput) {
        int items = packerInput.getItemList().size();
        int maxW = packerInput.getMaxWeight().intValue();
        List<Item> sortedItems = packerInput.getItemList().stream().sorted(Comparator.comparing(Item::getWeight)).
                collect(Collectors.toList());
        solution = new BigDecimal[items+1][maxW+1];
        packageWeight = new BigDecimal[items+1][maxW+1];

        initMatrix(items, maxW);

        for (int i = 1; i <= items; i++) {
            for (int j = 1; j <= maxW; j++) {
                BigDecimal bdJ = new BigDecimal(j);
                if (sortedItems.get(i - 1).getWeight().compareTo(bdJ) > 0) {//if item cannot be put into the package
                    solution[i][j] = solution[i - 1][j];
                    packageWeight[i][j] = packageWeight[i - 1][j];
                } else {
                    BigDecimal weightLeft = bdJ.subtract(sortedItems.get(i-1).getWeight());
                    BigDecimal weightLeftSolution;
                    BigDecimal usedWeight;
                    // decide which cell it should take based on weight left
                    int bestColumn = bestWeightLeftSolution(weightLeft,i);
                    weightLeftSolution = solution[i - 1][bestColumn];
                    usedWeight = packageWeight[i - 1][bestColumn];
                    // choose better solution between previous cell and current calculated one
                    solution[i][j] = solution[i - 1][j]
                            .max(weightLeftSolution.add(sortedItems.get(i-1).getCost()));

                    // hold the package weight on each possible solution
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

        TreeSet<Integer> packageItems = getPackageItems(items, maxW, sortedItems);

        return packageItems.stream().map(Object::toString).collect(Collectors.joining(","));
    }

    /**
     * Initialize the first row and first column of the solution matrices in order to prepare them for algorithm execution
     * @param x The number of rows of the matrix
     * @param y The number of columns of the matrix
     */
    private void initMatrix(int x, int y){
        //init first row
        for (int j = 0; j <= y; j++) {
            solution[0][j] = new BigDecimal(0);
            packageWeight[0][j] = new BigDecimal(0);
        }//init first column
        for (int i = 0; i <= x; i++) {
            solution[i][0] = new BigDecimal(0);
            packageWeight[i][0] = new BigDecimal(0);
        }
    }

    /**
     * Iterate over the matrix looking for the item list of the solution
     * @param items Number of items of the problem
     * @param weight Maximum weight of the problem
     * @param itemLList Sorted item list
     * @return Item list which can be put into package
     */
    private TreeSet<Integer> getPackageItems(int items, int weight, List<Item> itemLList){
        int n = items;
        int k = weight;
        TreeSet<Integer> packageItems = new TreeSet<>();
        while (n>0 && k>0){
            if(solution[n][k].compareTo(solution[n-1][k]) != 0){
                packageItems.add(itemLList.get(n-1).getIndex());
                BigDecimal weightLeft = (new BigDecimal(k)).subtract(itemLList.get(n-1).getWeight());
                if(packageWeight[n - 1][weightLeft.intValue()+1].compareTo(weightLeft)<=0){
                    k = weightLeft.intValue()+1;
                }else {
                    k = weightLeft.intValue();
                }
            }
            n--;
        }
        return packageItems;
    }

    /**
     * Decide which cell it should take based on weight left
     * @param weightLeft Weight left in the package
     * @param currentRow Current row of the matrix
     * @return best column
     */
    private int bestWeightLeftSolution(BigDecimal weightLeft, int currentRow){
        if(packageWeight[currentRow - 1][weightLeft.intValue()+1].compareTo(weightLeft)<=0){
            return weightLeft.intValue()+1;
        }else{
            return weightLeft.intValue();
        }
    }
}
