package com.example.javaIO.service;
import com.example.javaIO.model.Submission;
import com.example.javaIO.repository.SubmissionRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class FileService {
    private final SubmissionRepository repo;
    private final RestTemplate restTemplate;

    private static final String FILE_PATH = "hello_io.txt";
    private static final String FILES_PATH = "data.txt";
    private static final String N8N_WEBHOOK_URL = "https://n8n.planbow.com/webhook-test/send-email";
    private static final String FILEss_PATH = "sample.txt";

    @Value("${n8n.webhook.url}")
    private String n8nWebhookUrl;

    @Value("${n8n.webhook.chat-url}")
    private String n8nWebhookUrlChat;

 public FileService(SubmissionRepository repo, RestTemplate restTemplate){
     this.repo = repo;
     this.restTemplate = restTemplate;
 }
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


    public void process(String subject, String body) {
        Instant now = Instant.now();

        // 1) Save to local MongoDB
        Submission s = new Submission(subject, body, now);
        repo.save(s);

        // 2) Trigger n8n webhook with subject, body, receivedAt
        try {
            Map<String,String> payload = new HashMap<>();
            payload.put("subject", subject);
            payload.put("body", body);
            payload.put("receivedAt", now.toString());

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<Map<String,String>> request = new HttpEntity<>(payload, headers);

            ResponseEntity<String> response = restTemplate.postForEntity(n8nWebhookUrl, request, String.class);
            System.out.println("n8n response: " + response.getStatusCode() + " body: " + response.getBody());
        } catch (Exception e) {
            System.out.println("Failed to call n8n: " + e.getMessage());
        }
    }


    // 1. StringReader -> StringWriter
    public String processString(String input) throws IOException {
        StringReader reader = new StringReader(input);
        StringWriter writer = new StringWriter();

        int data;
        while ((data = reader.read()) != -1) {
            writer.write(Character.toUpperCase(data)); // convert to uppercase
        }

        return writer.toString();
    }

    // 2. File -> Formatted output using PrintWriter
    public String formatFile() throws IOException {
        Path inputPath = Paths.get("sample.txt");
        Path outputPath = Paths.get("output.txt");

        try (
                BufferedReader br = Files.newBufferedReader(inputPath);
                PrintWriter pw = new PrintWriter(Files.newBufferedWriter(outputPath))
        ) {
            String line;
            int lineNum = 1;
            while ((line = br.readLine()) != null) {
                pw.printf("Line %02d: %s%n", lineNum++, line.toUpperCase());
            }
        }

        return "File formatted successfully. Check output.txt";
    }

    public String readFileContent(String filePath) throws IOException {
        StringBuilder content = new StringBuilder();

        // Reader with encoding
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(new FileInputStream(filePath), StandardCharsets.UTF_8))) {

            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line); // ✅ Console pe print
                content.append(line).append("\n"); // ✅ Response me bhi bhejna
            }
        }
        return content.toString();
    }

    public String copyyFile(String sourcePath, String destPath) throws IOException {
        Files.copy(Paths.get(sourcePath), Paths.get(destPath), StandardCopyOption.REPLACE_EXISTING);
        return "File copied from " + sourcePath + " to " + destPath;
    }

    // 🔹 Merge two text files into one

    public String mergeFiles(String file1Path, String file2Path, String destPath) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(destPath, true))) {
            // Write file1 content
            Files.lines(Paths.get(file1Path)).forEach(line -> {
                try { writer.write(line + System.lineSeparator()); } catch (IOException e) { e.printStackTrace(); }
            });
            // Write file2 content
            Files.lines(Paths.get(file2Path)).forEach(line -> {
                try { writer.write(line + System.lineSeparator()); } catch (IOException e) { e.printStackTrace(); }
            });
        }
        return "Files merged into " + destPath;
    }


/*

    public String sendPromptToN8n(String prompt) {
        Map<String, Object> payload = new HashMap<>();
        payload.put("prompt", prompt);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(payload, headers);

        ResponseEntity<Map> response;
        try {
            response = restTemplate.postForEntity(n8nWebhookUrlChat, request, Map.class);
            System.out.println("response: "+response);
        } catch (HttpStatusCodeException ex) {
            // include body for debugging
            throw new RuntimeException("n8n error: " + ex.getStatusCode() + " - " + ex.getResponseBodyAsString(), ex);
        } catch (ResourceAccessException ex) {
            throw new RuntimeException("Timeout / network error calling n8n: " + ex.getMessage(), ex);
        }

        if (!response.getStatusCode().is2xxSuccessful()) {
            throw new RuntimeException("n8n returned non-200: " + response.getStatusCodeValue());
        }

        Map<String, Object> respBody = response.getBody();
        System.out.println("respBody: "+response.getBody());
        if (respBody == null) return "";

        // prefer explicit "answer" field (we'll make n8n return this)
        Object answer = respBody.get("answer");
        if (answer != null) return answer.toString();

        // fallback: return entire body as string
        try {
            return new ObjectMapper().writeValueAsString(respBody);
        } catch (JsonProcessingException e) {
            return respBody.toString();
        }
    }

*/

    public String sendPromptToN8n(String prompt) {
        Map<String, Object> payload = new HashMap<>();
        payload.put("prompt", prompt);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(payload, headers);

        ResponseEntity<String> response;
        try {
            // 👇 ab Map ki jagah String use kar
            response = restTemplate.postForEntity(n8nWebhookUrlChat, request, String.class);
           // System.out.println("Raw response: " + response.getBody());
        } catch (HttpStatusCodeException ex) {
            throw new RuntimeException("n8n error: " + ex.getStatusCode() + " - " + ex.getResponseBodyAsString(), ex);
        } catch (ResourceAccessException ex) {
            throw new RuntimeException("Timeout / network error calling n8n: " + ex.getMessage(), ex);
        }

        if (!response.getStatusCode().is2xxSuccessful()) {
            throw new RuntimeException("n8n returned non-200: " + response.getStatusCodeValue());
        }

        String respBody = response.getBody();
        if (respBody == null) return "";

        // 🔹 Try parsing as JSON
        try {
            ObjectMapper mapper = new ObjectMapper();
            Map<String, Object> json = mapper.readValue(respBody, Map.class);

            Object answer = json.get("answer");
            if (answer != null) {
                return answer.toString();
            } else {
                return mapper.writeValueAsString(json); // fallback full json
            }
        } catch (Exception e) {
            // 🔹 If not JSON, return raw string
            return respBody;
        }
    }

}