package com.mobiquity.packer;

import com.mobiquity.exception.APIException;
import com.mobiquity.parser.InputFileParser;

import java.io.FileNotFoundException;

public class Packer {

    private Packer() {
    }

    public static String pack(String filePath) throws APIException {
        try {
            InputFileParser.parse(filePath);
            return null;
        }catch (FileNotFoundException e){
            throw new APIException("Input file not found", e);
        }
    }
}
