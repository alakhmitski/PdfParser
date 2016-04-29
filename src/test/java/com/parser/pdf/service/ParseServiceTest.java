package com.parser.pdf.service;

import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.io.InputStream;

public class ParseServiceTest {

    private static ParserService parserService;
    private static final String TEST_FILE_PATH = "files/testFile.pdf";

    @BeforeClass
    public static void initParserService() {
        parserService = new ParserService();
    }


    @Test
    public void testParseFile() {
        StringBuilder expectedFileContent = new StringBuilder();
        expectedFileContent.append("This is test text \n").append(" \n");
        expectedFileContent.append("Start point \n");
        expectedFileContent.append("text row 3 \n");
        expectedFileContent.append("text row 4 \n");
        expectedFileContent.append("text row 5 \n");
        expectedFileContent.append("Stop point \n");
        expectedFileContent.append("text row 7 \n").append(" \n");
        expectedFileContent.append("End of Test file ");

        InputStream inputStream = null;
        try {
            ClassLoader loader = Thread.currentThread().getContextClassLoader();
            inputStream = loader.getResourceAsStream(TEST_FILE_PATH);
            String actualFileContent = parserService.parseFile(inputStream);
            assertEquals(expectedFileContent.toString(), actualFileContent);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
