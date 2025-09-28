package com.example.javaIO.service;

import org.springframework.stereotype.Service;
import java.io.*;
import java.nio.file.*;
import java.util.zip.*;

@Service
public class CompressionService {

    // ✅ Compress a folder into ZIP
    public String compressFolder(String sourceFolderPath, String zipFilePath) throws IOException {
        Path zipPath = Paths.get(zipFilePath);

        try (FileOutputStream fos = new FileOutputStream(zipPath.toFile());
             ZipOutputStream zos = new ZipOutputStream(fos)) {

            Path sourcePath = Paths.get(sourceFolderPath);

            Files.walk(sourcePath).forEach(path -> {
                try {
                    if (!Files.isDirectory(path)) {
                        ZipEntry zipEntry = new ZipEntry(sourcePath.relativize(path).toString());
                        zos.putNextEntry(zipEntry);
                        Files.copy(path, zos);
                        zos.closeEntry();
                    }
                } catch (IOException e) {
                    throw new RuntimeException("Error while compressing: " + path, e);
                }
            });
        }
        return "Folder compressed to: " + zipPath.toAbsolutePath();
    }

    // ✅ Extract ZIP file into folder
    public String extractZip(String zipFilePath, String destDir) throws IOException {
        File dir = new File(destDir);
        if (!dir.exists()) dir.mkdirs();

        try (FileInputStream fis = new FileInputStream(zipFilePath);
             ZipInputStream zis = new ZipInputStream(fis)) {

            ZipEntry entry;
            while ((entry = zis.getNextEntry()) != null) {
                File newFile = new File(destDir, entry.getName());

                // Agar parent folders exist nahi karte toh create karo
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
        return "ZIP extracted to: " + new File(destDir).getAbsolutePath();
    }
}

