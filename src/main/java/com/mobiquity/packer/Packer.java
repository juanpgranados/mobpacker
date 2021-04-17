package com.mobiquity.packer;

import com.mobiquity.exception.APIException;
import com.mobiquity.knapsack.DynamicProgrammingProcessor;
import com.mobiquity.knapsack.KnapsackProcessor;
import com.mobiquity.parser.InputFileParser;
import com.mobiquity.model.PackerInput;

import java.io.FileNotFoundException;
import java.util.List;

public class Packer {

    private Packer() {
    }

    public static String pack(String filePath) throws APIException {
        try {
            List<PackerInput> parsedInput = InputFileParser.parse(filePath);
            KnapsackProcessor processor = new DynamicProgrammingProcessor();
            StringBuilder outputBuilder = new StringBuilder();
            parsedInput.stream().forEach(input->{
                if(outputBuilder.length()>0) outputBuilder.append(System.lineSeparator());
                outputBuilder.append(processor.solve(input));
            });
            return outputBuilder.toString();
        }catch (FileNotFoundException e){
            throw new APIException("Input file not found", e);
        }
    }
}
