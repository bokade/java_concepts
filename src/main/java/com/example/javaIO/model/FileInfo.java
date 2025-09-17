package com.example.javaIO.model;
public class FileInfo {
    private String name;
    private String relativePath;
    private String absolutePath;
    private boolean directory;
    private boolean file;
    private boolean hidden;
    private long sizeBytes;
    private String sizeHuman;
    private long lastModified;
    private String lastModifiedIso;

    public FileInfo() {}

    public FileInfo(String name, String relativePath, String absolutePath,
                    boolean directory, boolean file, boolean hidden,
                    long sizeBytes, String sizeHuman, long lastModified, String lastModifiedIso) {
        this.name = name;
        this.relativePath = relativePath;
        this.absolutePath = absolutePath;
        this.directory = directory;
        this.file = file;
        this.hidden = hidden;
        this.sizeBytes = sizeBytes;
        this.sizeHuman = sizeHuman;
        this.lastModified = lastModified;
        this.lastModifiedIso = lastModifiedIso;
    }

    // --- getters & setters ---
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getRelativePath() { return relativePath; }
    public void setRelativePath(String relativePath) { this.relativePath = relativePath; }
    public String getAbsolutePath() { return absolutePath; }
    public void setAbsolutePath(String absolutePath) { this.absolutePath = absolutePath; }
    public boolean isDirectory() { return directory; }
    public void setDirectory(boolean directory) { this.directory = directory; }
    public boolean isFile() { return file; }
    public void setFile(boolean file) { this.file = file; }
    public boolean isHidden() { return hidden; }
    public void setHidden(boolean hidden) { this.hidden = hidden; }
    public long getSizeBytes() { return sizeBytes; }
    public void setSizeBytes(long sizeBytes) { this.sizeBytes = sizeBytes; }
    public String getSizeHuman() { return sizeHuman; }
    public void setSizeHuman(String sizeHuman) { this.sizeHuman = sizeHuman; }
    public long getLastModified() { return lastModified; }
    public void setLastModified(long lastModified) { this.lastModified = lastModified; }
    public String getLastModifiedIso() { return lastModifiedIso; }
    public void setLastModifiedIso(String lastModifiedIso) { this.lastModifiedIso = lastModifiedIso; }
}
