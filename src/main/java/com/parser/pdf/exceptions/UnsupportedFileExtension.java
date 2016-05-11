package com.parser.pdf.exceptions;

/**
 * Thrown when uploaded file is not pdf
 * */
public class UnsupportedFileExtension extends Exception {

    public UnsupportedFileExtension(String message){
        super(message);
    }

}
