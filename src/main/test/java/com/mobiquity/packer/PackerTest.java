package com.mobiquity.packer;

import com.mobiquity.exception.APIException;
import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PackerTest {

    @Test
    void test(){
        try {
            ClassLoader classLoader = getClass().getClassLoader();
            File inputFile = new File(classLoader.getResource("example_input").getFile());

            String outputData = IOUtils.toString(
                    classLoader.getResource("example_output"),
                    "UTF-8"
            );

            String result = Packer.pack(inputFile.getPath());
            assertEquals(outputData,result);
        }
        catch (IOException e){}
        catch (APIException e){}

    }
}
