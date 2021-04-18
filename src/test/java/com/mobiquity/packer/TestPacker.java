package com.mobiquity.packer;

import com.mobiquity.exception.APIException;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

class TestPacker {

    @Test
    void testPacker(){
        try {
            //Given
            ClassLoader classLoader = getClass().getClassLoader();
            File inputFile = new File(classLoader.getResource("example_input").getFile());

            String outputData = "4"+System.lineSeparator()+
                    "-"+System.lineSeparator()+
                    "2,7"+System.lineSeparator()+
                    "8,9";
            //When
            String result = Packer.pack(inputFile.getPath());
            //Then
            assertEquals(outputData,result);
        }
        catch (APIException e){
            fail("Packer throws an APIException");
        }

    }

    @Test
    void testNonexistentFile(){
        String filePath = "any_file";
        assertThrows(APIException.class, () -> {
            Packer.pack(filePath);
        });
    }
}
