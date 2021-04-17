package com.mobiquity.parser;

import com.mobiquity.pojo.Item;
import com.mobiquity.pojo.PackerInput;

import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class InputFileParser {

    static final String ITEM_REGEX = "\\((.*?)\\)";
    static final String CURRENCY_SYMBOL = "€";

    static public List<PackerInput> parse(String inputFilePath) throws FileNotFoundException {
        File inputFile = new File(inputFilePath);
        Scanner reader = new Scanner(inputFile);
        List<PackerInput> inputList = new ArrayList<PackerInput>();
        while (reader.hasNextLine()) {
            //Read the file
            String data = reader.nextLine();
            //Parse Data
            String[] splitData = data.split(" : ");
            BigInteger maxWeight = new BigInteger(splitData[0]);
            //Parse items
            List<String> itemDataList = new ArrayList<String>();
            Pattern regex = Pattern.compile(ITEM_REGEX);
            Matcher regexMatcher = regex.matcher(splitData[1]);
            while (regexMatcher.find()) {
                itemDataList.add(regexMatcher.group(1));
            }
            //Load Data in Pojos
            List<Item> itemList = new ArrayList<Item>();
            itemDataList.stream().forEach((i)->{
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
}
