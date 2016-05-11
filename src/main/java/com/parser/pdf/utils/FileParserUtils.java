package com.parser.pdf.utils;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;

import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Util class for working with uploaded files
 * */
public class FileParserUtils {

    /**
     * Replaces all newlines within content string with commas
     */
    public static String formattingContent(String content) {
        String[] splitContent = StringUtils.split(content, "\n");
        String formattedContent = StringUtils.join(splitContent, ", ");
        return formattedContent;
    }

    /**
     * Gets substring of a text string between startpoint and endpoint
     */
    public static String getContent(String text, String startPoint, String endPoint) {
        if (text == null || text.isEmpty()) {
            return text;
        }
        String content = StringUtils.substringBetween(text, startPoint, endPoint);
        return content;
    }

    /**
     * Gets file name without extension
     */
    public static String getFileName(String file) {
        Path path = Paths.get(file);
        String fileName = path.getFileName().toString();
        fileName = FilenameUtils.removeExtension(fileName);
        return fileName.toString();
    }

    /**
     * Gets file extension
     */
    public static String getFileExtension(String file) {
        return FilenameUtils.getExtension(file);
    }
}
