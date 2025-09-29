package com.example.utils;

import java.io.*;
import java.nio.file.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.zip.*;

public class ZipUtils {

    // ✅ Compress a folder into zip
    public static void compressFolder(String sourceFolderPath, String zipFilePath) throws IOException {
        Path sourcePath = Paths.get(sourceFolderPath);
        Path zipPath = Paths.get(zipFilePath);

        // ensure parent dirs exist
        Files.createDirectories(zipPath.getParent());

        try (FileOutputStream fos = new FileOutputStream(zipPath.toFile());
             ZipOutputStream zos = new ZipOutputStream(fos)) {

            Files.walk(sourcePath).forEach(path -> {
                try {
                    if (!Files.isDirectory(path)) {
                        ZipEntry entry = new ZipEntry(sourcePath.relativize(path).toString());
                        zos.putNextEntry(entry);
                        Files.copy(path, zos);
                        zos.closeEntry();
                    }
                } catch (IOException e) {
                    throw new RuntimeException("Error compressing " + path, e);
                }
            });
        }
    }

    // ✅ Extract a zip into folder
    public static void extractZip(String zipFilePath, String destDir) throws IOException {
        File dir = new File(destDir);
        if (!dir.exists()) dir.mkdirs();

        try (FileInputStream fis = new FileInputStream(zipFilePath);
             ZipInputStream zis = new ZipInputStream(fis)) {

            ZipEntry entry;
            while ((entry = zis.getNextEntry()) != null) {
                File newFile = new File(destDir, entry.getName());
                new File(newFile.getParent()).mkdirs();

                try (FileOutputStream fos = new FileOutputStream(newFile)) {
                    byte[] buffer = new byte[1024];
                    int len;
                    while ((len = zis.read(buffer)) > 0) {
                        fos.write(buffer, 0, len);
                    }
                }
                zis.closeEntry();
            }
        }
    }

    // ✅ Auto-backup utility
    public static String autoBackup(String sourceFolder, String backupDir) throws IOException {
        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String backupFile = backupDir + "/backup_" + timestamp + ".zip";

        compressFolder(sourceFolder, backupFile);
        return backupFile;
    }
}
