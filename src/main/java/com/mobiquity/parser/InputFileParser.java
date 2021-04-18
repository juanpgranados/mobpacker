package com.mobiquity.parser;

import com.mobiquity.exception.APIException;
import com.mobiquity.model.Item;
import com.mobiquity.model.PackerInput;

import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class InputFileParser {

    static final String ITEM_REGEX = "\\((.*?)\\)";
    static final String CURRENCY_SYMBOL = "€";

    /**
     * Parse an input file with packaging problem test cases
     * @param inputFilePath Input file absolute path
     * @return
     * @throws FileNotFoundException
     */
    public static List<PackerInput> parse(String inputFilePath) throws FileNotFoundException, APIException {
        File inputFile = new File(inputFilePath);
        Scanner reader = new Scanner(inputFile, StandardCharsets.UTF_8.name());
        List<PackerInput> inputList = new ArrayList<>();
        if(!reader.hasNextLine()) throw new APIException("Empty input file");
        while (reader.hasNextLine()) {
            //Read the file
            String data = reader.nextLine();
            //Parse Data
            String[] splitData = data.split(" : ");
            if(splitData.length != 2) throw new APIException("Invalid input format");
            BigInteger maxWeight = new BigInteger(splitData[0]);
            //Parse items
            List<String> itemDataList = new ArrayList<>();
            Pattern regex = Pattern.compile(ITEM_REGEX);
            Matcher regexMatcher = regex.matcher(splitData[1]);
            while (regexMatcher.find()) {
                itemDataList.add(regexMatcher.group(1));
            }
            //Load Data in models
            List<Item> itemList = new ArrayList<>();
            itemDataList.stream().forEach(i->{
                String[] itemData = i.split(",");
                int index = Integer.parseInt(itemData[0]);
                BigDecimal weight = new BigDecimal(itemData[1]);
                BigDecimal cost = new BigDecimal(itemData[2].replace(CURRENCY_SYMBOL,""));
                itemList.add(new Item(index, weight, cost));
            });

            inputList.add(new PackerInput(maxWeight, itemList));
        }
        reader.close();
        return inputList;
    }

    private InputFileParser() {
    }
}
