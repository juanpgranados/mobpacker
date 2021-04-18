package com.mobiquity.parser;

import com.mobiquity.exception.APIException;
import com.mobiquity.model.Item;
import com.mobiquity.model.PackerInput;
import com.mobiquity.packer.Packer;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

public class TestInputFileParser {
    @Test
    void testEmptyFile(){
        //Given
        ClassLoader classLoader = getClass().getClassLoader();
        File inputFile = new File(classLoader.getResource("empty_input").getFile());
        // When
        APIException apiException = assertThrows(APIException.class, () -> {
            Packer.pack(inputFile.getPath());
        });
        //Then
        assertEquals("Empty input file", apiException.getMessage());
    }

    @Test
    void testWrongFormatFile(){
        //Given
        ClassLoader classLoader = getClass().getClassLoader();
        File inputFile = new File(classLoader.getResource("wrong_format_input").getFile());
        // When
        APIException apiException = assertThrows(APIException.class, () -> {
            Packer.pack(inputFile.getPath());
        });
        //Then
        assertEquals("Invalid input format", apiException.getMessage());
    }

    @Test
    void testParser(){
        try {
            //Given
            ClassLoader classLoader = getClass().getClassLoader();
            List<PackerInput> expectedParsedList = new ArrayList<>();
            List<Item> itemList = new ArrayList<>();

            itemList.add(new Item(1, new BigDecimal("53.38"), new BigDecimal("45")));
            itemList.add(new Item(2, new BigDecimal("88.62"), new BigDecimal("98")));
            expectedParsedList.add(new PackerInput(new BigInteger("81"), itemList));

            File inputFile = new File(classLoader.getResource("single_input").getFile());
            //When
            List<PackerInput> parsedInput = InputFileParser.parse(inputFile.getPath());
            //Then
            assertEquals(expectedParsedList, parsedInput);
        } catch (FileNotFoundException e) {
            fail("Parser throws a FileNotFoundException");
        }catch (APIException e) {
            fail("Parser throws an APIException");
        }
    }
}
