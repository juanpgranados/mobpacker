package com.mobiquity.parser;

import com.mobiquity.exception.APIException;
import com.mobiquity.packer.Packer;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

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
}
