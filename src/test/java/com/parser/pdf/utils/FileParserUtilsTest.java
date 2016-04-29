package com.parser.pdf.utils;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class FileParserUtilsTest {

    @Test
    public void testGetContent() {
        StringBuilder text = new StringBuilder();
        text.append("This is test text\n");
        text.append("Start point\n");
        text.append("text row 3\n");
        text.append("text row 4\n");
        text.append("text row 5\n");
        text.append("Stop point\n");
        text.append("text row 7\n");

        String actualContent = FileParserUtils.getContent(text.toString(), "Start point", "Stop point");

        StringBuilder expectedContent = new StringBuilder();
        expectedContent.append("\n");
        expectedContent.append("text row 3\n");
        expectedContent.append("text row 4\n");
        expectedContent.append("text row 5\n");

        assertEquals(expectedContent.toString(), actualContent);
    }

    @Test
    public void testFormattingContent() {
        StringBuilder text = new StringBuilder();
        text.append("This is test text\n");
        text.append("Start point\n");
        text.append("text row 3\n");
        text.append("text row 4\n");
        text.append("text row 5\n");
        text.append("Stop point\n");
        text.append("text row 7\n");

        String actualContent = FileParserUtils.getContent(text.toString(), "Start point", "Stop point");
        String actualFormattingContent = FileParserUtils.formattingContent(actualContent);

        text = new StringBuilder();
        text.append("text row 3, ");
        text.append("text row 4, ");
        text.append("text row 5");

        assertEquals(text.toString(), actualFormattingContent);

    }

    @Test
    public void testGetFileName() {
        String originalFileName = "testFile.pdf";
        String actualFileName = FileParserUtils.getFileName(originalFileName);
        assertEquals("testFile", actualFileName);
    }

}
