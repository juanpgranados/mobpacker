package com.mobiquity.validator;

import com.mobiquity.exception.APIException;
import com.mobiquity.model.PackerInput;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.HashSet;
import java.util.Set;

public class InputValidator {

    /**
     * Validate input constraints
     * @param input Input object which holds the data
     * @throws APIException
     */
    public static void validate(PackerInput input) throws APIException {
        if (input.getMaxWeight().compareTo(BigInteger.valueOf(100)) > 0) {
            throw new APIException("Invalid max weight " + input.getMaxWeight().toString());
        }
        if (input.getItemList().size() > 15) {
            throw new APIException("Items quantity exceed max permitted");
        }
        Long invalidCostItems = input.getItemList().stream().filter(
                item -> item.getCost().compareTo(BigDecimal.valueOf(100)) > 0
        ).count();
        if (invalidCostItems > 0) {
            throw new APIException("Invalid item cost");
        }
        Long invalidWeightItems = input.getItemList().stream().filter(
                item -> item.getWeight().compareTo(BigDecimal.valueOf(100)) > 0
        ).count();
        if (invalidWeightItems > 0) {
            throw new APIException("Invalid item weight");
        }
        Set<Integer> items = new HashSet<>();
        Long duplicatedIndexes = input.getItemList().stream().filter(i->!items.add(i.getIndex())).count();
        if (duplicatedIndexes>0){
            throw new APIException("Invalid input, duplicated index");
        }
    }

    private InputValidator() {
    }
}
