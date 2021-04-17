package com.mobiquity.packer;

import com.mobiquity.exception.APIException;
import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class TestPacker {

    @Test
    void testPacker(){
        try {
            //Given
            ClassLoader classLoader = getClass().getClassLoader();
            File inputFile = new File(classLoader.getResource("example_input").getFile());

            String outputData = IOUtils.toString(
                    classLoader.getResource("example_output"),
                    "UTF-8"
            );
            String altData="x"+System.lineSeparator()+"x"+System.lineSeparator()+"x"+System.lineSeparator()+"x";
            //When
            String result = Packer.pack(inputFile.getPath());
            //Then
            assertEquals(altData,result);
        }
        catch (IOException e){
            fail("Cannot load test data");
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
