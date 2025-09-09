package com.example.javaIO.service;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class FileService {

    private static final String FILE_PATH = "hello_io.txt";
    private static final String FILES_PATH = "data.txt";
    private static final String N8N_WEBHOOK_URL = "https://n8n.planbow.com/webhook-test/send-email";
    private static final String FILEss_PATH = "sample.txt";
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

    public void copyFile(String sourcePath, String destinationPath) throws IOException {
        try(FileInputStream inputStream = new FileInputStream(new File(sourcePath));
            FileOutputStream outputStream = new FileOutputStream(new File(destinationPath))) {

            byte[] buffer = new byte[1024];
            int length;
            while ((length = inputStream.read(buffer)) > 0) {
                outputStream.write(buffer, 0, length);
            }
        }
    }

    // Write content line by line
    public String writesToFile(String content) {
        try (FileWriter writer = new FileWriter(FILES_PATH, true)) { // append = true
            writer.write(content + "\n");
            return "Content written successfully!";
        } catch (IOException e) {
            return " Error writing file: " + e.getMessage();
        }
    }

    // Read content char by char
    public String readsFromFile() {
        StringBuilder sb = new StringBuilder();
        try (FileReader reader = new FileReader(FILES_PATH)) {
            int ch;
            while ((ch = reader.read()) != -1) {
                sb.append((char) ch);
            }
        } catch (IOException e) {
            return " Error reading file: " + e.getMessage();
        }
        return sb.toString();
    }

    // Read file character by character
    public String readFile() {
        StringBuilder content = new StringBuilder();
        try (Reader reader = new FileReader(FILEss_PATH, StandardCharsets.UTF_8)) {
            int ch;
            while ((ch = reader.read()) != -1) {
                content.append((char) ch);
            }
        } catch (IOException e) {
            return "Error reading file: " + e.getMessage();
        }
        return content.toString();
    }

    // Write file line by line
    public String writeFile(List<String> lines) {
        try (Writer writer = new FileWriter(FILEss_PATH, StandardCharsets.UTF_8, false)) {
            for (String line : lines) {
                writer.write(line + System.lineSeparator());
            }
        } catch (IOException e) {
            return "Error writing file: " + e.getMessage();
        }
        return "File written successfully!";
    }


    public String triggerWorkflow(String subject, String body) {
        RestTemplate restTemplate = new RestTemplate();

        Map<String, String> payload = new HashMap<>();
        payload.put("subject", subject);
        payload.put("body", body);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Map<String, String>> request = new HttpEntity<>(payload, headers);

        ResponseEntity<String> response = restTemplate.postForEntity(N8N_WEBHOOK_URL, request, String.class);

        System.out.println("n8n Response: " + response.getBody());

        if(response != null && response.getStatusCode().is2xxSuccessful()){
            return "Workflow triggered successfully!";
        } else {
            return "Failed to trigger workflow.";
        }
    }

    // 1. Copy text file using BufferedReader/Writer
    public String copyTextFile(String sourcePath, String destPath) throws IOException {
        long start = System.currentTimeMillis();

        try (BufferedReader reader = new BufferedReader(new FileReader(sourcePath));
             BufferedWriter writer = new BufferedWriter(new FileWriter(destPath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                writer.write(line);
                writer.newLine(); // preserve line breaks
            }
        }

        long end = System.currentTimeMillis();
        return "Text file copied successfully in " + (end - start) + " ms";
    }

    // 2. Copy large file with buffer
    public String copyLargeFileWithBuffer(String sourcePath, String destPath) throws IOException {
        long start = System.currentTimeMillis();

        try (BufferedInputStream bis = new BufferedInputStream(new FileInputStream(sourcePath));
             BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(destPath))) {
            byte[] buffer = new byte[8192]; // 8 KB buffer
            int bytesRead;
            while ((bytesRead = bis.read(buffer)) != -1) {
                bos.write(buffer, 0, bytesRead);
            }
        }

        long end = System.currentTimeMillis();
        return "Large file copied WITH buffer in " + (end - start) + " ms";
    }

    // 3. Copy large file without buffer
    public String copyLargeFileWithoutBuffer(String sourcePath, String destPath) throws IOException {
        long start = System.currentTimeMillis();

        try (FileInputStream fis = new FileInputStream(sourcePath);
             FileOutputStream fos = new FileOutputStream(destPath)) {
            int data;
            while ((data = fis.read()) != -1) {
                fos.write(data);
            }
        }

        long end = System.currentTimeMillis();
        return "Large file copied WITHOUT buffer in " + (end - start) + " ms";
    }

    // Utility - create test file
    public String createSampleTextFile(String path) throws IOException {
        Path file = Path.of(path);
        Files.writeString(file, "Hello\nThis is a test file\nBuffered Streams in Java\nSpring Boot Demo");
        return "Sample file created at " + file.toAbsolutePath();
    }



}