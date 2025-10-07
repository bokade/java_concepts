package com.example.javaIO.model;
import java.io.Serializable;

public class Config implements Serializable {
    private static final long serialVersionUID = 1L;

    private String backupDir;
    private String lastUser;
    private boolean autoCompress;

    // Getters and Setters
    public String getBackupDir() { return backupDir; }
    public void setBackupDir(String backupDir) { this.backupDir = backupDir; }

    public String getLastUser() { return lastUser; }
    public void setLastUser(String lastUser) { this.lastUser = lastUser; }

    public boolean isAutoCompress() { return autoCompress; }
    public void setAutoCompress(boolean autoCompress) { this.autoCompress = autoCompress; }

    @Override
    public String toString() {
        return "Config [backupDir=" + backupDir + ", lastUser=" + lastUser + ", autoCompress=" + autoCompress + "]";
    }
}

