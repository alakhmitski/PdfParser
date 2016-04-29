package com.parser.pdf.utils;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;

import java.nio.file.Path;
import java.nio.file.Paths;

public class FileParserUtils {

    public static String formattingContent(String content) {
        String[] splitContent = StringUtils.split(content, "\n");
        String formattedContent = StringUtils.join(splitContent, ", ");
        return formattedContent;
    }


    public static String getContent(String text, String startPoint, String endPoint) {
        if (text == null || text.isEmpty()) {
            return text;
        }
        String content = StringUtils.substringBetween(text, startPoint, endPoint);
        return content;
    }

    public static String getFileName(String file) {
        Path path = Paths.get(file);
        String fileName = path.getFileName().toString();
        fileName = FilenameUtils.removeExtension(fileName);
        return fileName.toString();
    }
}
