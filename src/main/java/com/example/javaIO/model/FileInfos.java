package com.example.javaIO.model;

public class FileInfos {

    private String name;
    private String type;
    private long size;
    private String path;

    public FileInfos(String name, String type, long size, String path) {
        this.name = name;
        this.type = type;
        this.size = size;
        this.path = path;
    }

    // getters and setters
    public String getName() { return name; }
    public String getType() { return type; }
    public long getSize() { return size; }
    public String getPath() { return path; }

}
