package com.example.javaIO.service;

import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.IOException;

@Service
public class FileService {

    private static final String FILE_PATH = "hello_io.txt";

    // ✅ File me likhna
    public String writeToFile(String content) {
        try (FileWriter writer = new FileWriter(FILE_PATH)) {
            writer.write(content);
            return "✅ Successfully wrote to file: " + FILE_PATH;
        } catch (IOException e) {
            return "❌ Error while writing: " + e.getMessage();
        }
    }

    // ✅ File se read karna
    public String readFromFile() {
        File file = new File(FILE_PATH);
        if (!file.exists()) {
            return "⚠️ File does not exist. Please write first!";
        }

        StringBuilder content = new StringBuilder();
        try (FileReader reader = new FileReader(FILE_PATH)) {
            int ch;
            while ((ch = reader.read()) != -1) {
                content.append((char) ch);
            }
            return "📖 File Content: " + content.toString();
        } catch (IOException e) {
            return "❌ Error while reading: " + e.getMessage();
        }
    }
}