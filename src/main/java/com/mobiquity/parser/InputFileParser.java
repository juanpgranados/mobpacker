package com.mobiquity.parser;

import com.mobiquity.pojo.Item;

import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigDecimal;
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

    static public void parse(String inputFilePath) throws FileNotFoundException {
        File inputFile = new File(inputFilePath);
        Scanner reader = new Scanner(inputFile);
        while (reader.hasNextLine()) {
            String data = reader.nextLine();
            System.out.println(data);
            String[] splitData = data.split(" : ");
            BigDecimal maxWeight = new BigDecimal(splitData[0]);

            List<String> itemDataList = new ArrayList<String>();
            Pattern regex = Pattern.compile("\\((.*?)\\)");
            Matcher regexMatcher = regex.matcher(splitData[1]);

            while (regexMatcher.find()) {//Finds Matching Pattern in String
                itemDataList.add(regexMatcher.group(1));//Fetching Group from String
            }

            List<Item> itemList = new ArrayList<Item>();
            itemDataList.stream().forEach((i)->{
                String[] itemData = i.split(",");
                int index = Integer.parseInt(itemData[0]);
                BigDecimal weight = new BigDecimal(itemData[1]);
                BigDecimal cost = new BigDecimal(itemData[2].replace("€",""));
                itemList.add(new Item(index, weight, cost));
            });
        }
    }
}
