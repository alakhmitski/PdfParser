package com.parser.pdf.entity;

import java.io.InputStream;
import java.io.Serializable;

public class Article implements Serializable {

    private Integer id;
    private String name;
    private String content;
    private byte[] binaryData;

    private Article() {
    }

    public Article(Integer id, String name, String content, byte[] binaryData) {
        this.id = id;
        this.name = name;
        this.content = content;
        this.binaryData = binaryData;
    }

    public Article(String name, String content, byte[] binaryData) {
        this.name = name;
        this.content = content;
        this.binaryData = binaryData;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public byte[] getBinaryData() {
        return binaryData;
    }

    public void setBinaryData(byte[] binaryData) {
        this.binaryData = binaryData;
    }

}
