package com.example.javaIO.controller;
import com.example.javaIO.service.CompressionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping("/api/compression")
public class CompressionController {

    @Autowired
    private CompressionService compressionService;

    // ✅ API to compress folder
    @PostMapping("/compress")
    public String compressFolder(@RequestParam String sourceFolder,
                                 @RequestParam String zipFilePath) {
        try {
            return compressionService.compressFolder(sourceFolder, zipFilePath);
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }

    // ✅ API to extract zip
    @PostMapping("/extract")
    public String extractZip(@RequestParam String zipFilePath,
                             @RequestParam String destFolder) {
        try {
            return compressionService.extractZip(zipFilePath, destFolder);
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }
}
