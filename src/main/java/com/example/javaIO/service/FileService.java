package com.example.javaIO.service;
import com.example.javaIO.model.*;
import com.example.javaIO.repository.JwtSecretRepository;
import com.example.javaIO.repository.SubmissionRepository;
import com.example.javaIO.repository.UserRepository;
import com.example.utils.ZipUtils;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import javax.crypto.SecretKey;
import java.io.*;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.time.Instant;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Stream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

@Service
public class FileService {

    public FileService() throws IOException {
        if (!Files.exists(baseDir)) {
            Files.createDirectory(baseDir);
        }
    }

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtSecretRepository secretRepository;

    @Autowired
    private  SubmissionRepository repo;

    @Autowired
    private RestTemplate restTemplate;



    private static final String FILE_PATH = "hello_io.txt";
    private static final String FILES_PATH = "data.txt";
    private static final String N8N_WEBHOOK_URL = "https://n8n.planbow.com/webhook-test/send-email";
    private static final String FILEss_PATH = "sample.txt";
    private final String BASE_PATH = "D:/file-storage"; // yahan sab files banenge
    private final String FILE_PATH_RANDOM = "D:/Practical_Java/JavaConcepts/sample.txt"; // update path
    private final String FILE_PATH_DATA = "numbers.bin";
    private final Path baseDir = Paths.get("files"); // project ke andar "files" folder


    //    D:\Practical_Java\JavaConcepts\sample.txt
    @Value("${n8n.webhook.url}")
    private String n8nWebhookUrl;

    @Value("${n8n.webhook.chat-url}")
    private String n8nWebhookUrlChat;

    @Value("${n8n.webhook.secret}")
    private String n8nWebhookSecret;

    @Value("${n8n.webhook.url.chat.jwt}")
    private String n8nWebhookUrlChatJwt;

    @Value("${n8n.jwt.secret}")
    private String n8nJwtSecret;

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

    public List<FileInfo> listFiles(String dirPath, boolean recursive) throws IOException {
        File base = new File(dirPath);
        if (!base.exists()) {
            throw new IllegalArgumentException("Path does not exist: " + dirPath);
        }
        if (!base.isDirectory()) {
            throw new IllegalArgumentException("Provided path is not a directory: " + dirPath);
        }
        List<FileInfo> out = new ArrayList<>();
        traverse(base, base, out, recursive);
        return out;
    }

    private void traverse(File base, File dir, List<FileInfo> out, boolean recursive) throws IOException {
        File[] children = dir.listFiles();
        if (children == null) {
            // could be permissions issue or IO error; just return silently or log
            return;
        }
        for (File f : children) {
            boolean isHidden;
            try {
                isHidden = f.isHidden();
            } catch (Exception ex) {
                // fallback: unix-style hidden file check
                isHidden = f.getName().startsWith(".");
            }

            boolean isDir = f.isDirectory();
            boolean isFile = f.isFile();
            long size = isFile ? f.length() : 0L;

            // relative path relative to base
            String relative;
            try {
                Path basePath = base.toPath().toRealPath();
                Path filePath = f.toPath().toRealPath();
                relative = basePath.relativize(filePath).toString();
            } catch (IOException e) {
                // fallback to name if canonicalization fails
                relative = f.getName();
            }

            FileInfo fi = new FileInfo(
                    f.getName(),
                    relative,
                    f.getAbsolutePath(),
                    isDir,
                    isFile,
                    isHidden,
                    size,
                    humanReadableByteCount(size),
                    f.lastModified(),
                    isoFromEpochMillis(f.lastModified())
            );
            out.add(fi);

            if (isDir && recursive) {
                traverse(base, f, out, true);
            }
        }
    }

    private static String humanReadableByteCount(long bytes) {
        if (bytes < 1024) return bytes + " B";
        int exp = (int) (Math.log(bytes) / Math.log(1024));
        String pre = "KMGTPE".charAt(exp - 1) + "";
        return String.format("%.1f %sB", bytes / Math.pow(1024, exp), pre);
    }

    private static String isoFromEpochMillis(long epochMillis) {
        if (epochMillis <= 0) return "";
        Instant t = Instant.ofEpochMilli(epochMillis);
        return DateTimeFormatter.ISO_INSTANT.withZone(ZoneOffset.UTC).format(t);
    }




    // Create directory and files
    public String createDirWithFiles(String dirName, String[] fileNames) throws IOException {
        File dir = new File(BASE_PATH + "/" + dirName);

        if (!dir.exists()) {
            boolean created = dir.mkdirs();
            if (!created) {
                throw new IOException("Directory could not be created");
            }
        }

        StringBuilder result = new StringBuilder("Directory created: " + dir.getAbsolutePath() + "\n");

        for (String fileName : fileNames) {
            File file = new File(dir, fileName);
            if (file.createNewFile()) {
                result.append("File created: ").append(file.getName()).append("\n");
            } else {
                result.append("File already exists: ").append(file.getName()).append("\n");
            }
        }
        return result.toString();
    }

    // Recursive delete directory
    public String deleteDirectory(String dirName) {
        File dir = new File(BASE_PATH + "/" + dirName);

        if (!dir.exists()) {
            return "Directory does not exist: " + dirName;
        }

        deleteRecursively(dir);
        return "Directory deleted: " + dirName;
    }

    private void deleteRecursively(File file) {
        if (file.isDirectory()) {
            for (File child : file.listFiles()) {
                deleteRecursively(child);
            }
        }
        file.delete();
    }




    // Update 5th character
    public String updateFifthCharacter(char newChar) throws Exception {
        try (RandomAccessFile raf = new RandomAccessFile(FILE_PATH_RANDOM, "rw")) {
            if (raf.length() < 5) {
                return "File is too short!";
            }
            raf.seek(4); // move to 5th character (index 4)
            raf.writeByte(newChar);
            return "5th character updated successfully to '" + newChar + "'";
        }
    }

    // Read last 10 bytes
    public String readLast10Bytes() throws Exception {
        try (RandomAccessFile raf = new RandomAccessFile(FILE_PATH_RANDOM, "r")) {
            long length = raf.length();
            if (length < 10) {
                raf.seek(0); // file smaller than 10 bytes → read full
            } else {
                raf.seek(length - 10); // move pointer to last 10 bytes
            }

            byte[] buffer = new byte[(int) (raf.length() < 10 ? raf.length() : 10)];
            raf.readFully(buffer);
            return new String(buffer, StandardCharsets.UTF_8);
        }
    }


    // Traverse directory recursively
    public List<FileInfos> listFiles(String dirPath) {
        List<FileInfos> fileList = new ArrayList<>();
        File dir = new File(dirPath);

        if (!dir.exists() || !dir.isDirectory()) {
            return fileList;
        }

        traverseDirectory(dir, fileList);
        return fileList;
    }

    private void traverseDirectory(File dir, List<FileInfos> fileList) {
        for (File file : dir.listFiles()) {
            String type = file.isDirectory() ? "Directory" : "File";
            long size = file.isDirectory() ? 0 : file.length();
            fileList.add(new FileInfos(file.getName(), type, size, file.getAbsolutePath()));

            if (file.isDirectory()) {
                traverseDirectory(file, fileList);
            }
        }
    }

    // Create file
    public String createFiles(String path) throws IOException {
        File file = new File(path);
        if (file.exists()) {
            return "File already exists: " + path;
        }
        boolean created = file.createNewFile();
        return created ? "File created: " + path : "Failed to create file";
    }

    // Delete file
    public String deleteFiles(String path) {
        File file = new File(path);
        if (!file.exists()) {
            return "File not found: " + path;
        }
        boolean deleted = file.delete();
        return deleted ? "File deleted: " + path : "Failed to delete file";
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

    /*
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


*/

    /*
    public String sendPromptToN8n(String prompt) {
        Map<String, Object> payload = new HashMap<>();
        payload.put("prompt", prompt);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("X-API-KEY", n8nWebhookSecret); // <-- header with secret

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(payload, headers);

        ResponseEntity<String> response;
        try {
            // 👇 ab Map ki jagah String use kar
            response = restTemplate.postForEntity(n8nWebhookUrlChat, request, String.class);
             System.out.println("Raw response: " + response.getBody());
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
*/
/*
    public ResponseEntity<Map<String, Object>> sendPromptToN8n(String prompt) {
        Map<String, Object> payload = new HashMap<>();
        payload.put("prompt", prompt);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        //headers.set("X-API-KEY", n8nWebhookSecret); // Use this if you have key

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(payload, headers);

        ResponseEntity<String> response;
        try {
            response = restTemplate.postForEntity(n8nWebhookUrlChat, request, String.class);
            System.out.println("Raw response: " + response.getBody());
        } catch (ResourceAccessException ex) {
            // Network timeout etc.
            Map<String, Object> error = new HashMap<>();
            error.put("error", "Network error: " + ex.getMessage());
            return ResponseEntity.status(HttpStatus.GATEWAY_TIMEOUT).body(error);
        }

        // Now handle non-2xx status codes manually
        if (!response.getStatusCode().is2xxSuccessful()) {
            Map<String, Object> error = new HashMap<>();
            error.put("error", "n8n returned " + response.getStatusCodeValue() + " - " + response.getBody());
            return ResponseEntity.status(response.getStatusCode()).body(error);
        }

        // 2xx successful response
        Map<String, Object> out = new HashMap<>();
        out.put("answer", response.getBody());
        return ResponseEntity.ok(out);
    }
*/

    public ResponseEntity<Map<String, Object>> sendPromptToN8n(String prompt) {
        Map<String, Object> payload = new HashMap<>();
        payload.put("prompt", prompt);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        // Uncomment and set the secret if you have one
         headers.set("X-API-KEY", n8nWebhookSecret);

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(payload, headers);

        ResponseEntity<String> response;
        try {
            response = restTemplate.postForEntity(n8nWebhookUrlChat, request, String.class);
            System.out.println("Raw response: " + response.getBody());
        } catch (ResourceAccessException ex) {
            // Network timeout or connectivity error
            Map<String, Object> error = new HashMap<>();
            error.put("error", "Network error: " + ex.getMessage());
            return ResponseEntity.status(HttpStatus.GATEWAY_TIMEOUT).body(error);
        }

        // Handle non-2xx status codes manually
        if (!response.getStatusCode().is2xxSuccessful()) {
            Map<String, Object> error = new HashMap<>();
            error.put("error", "n8n returned " + response.getStatusCodeValue() +
                    (response.getBody() != null ? " - " + response.getBody() : ""));
            return ResponseEntity.status(response.getStatusCode()).body(error);
        }

        // Successful 2xx response
        Map<String, Object> out = new HashMap<>();
        out.put("answer", response.getBody());
        return ResponseEntity.ok(out);
    }


    public ResponseEntity<Map<String, Object>> sendPromptToN8nJwt(String prompt) {
        // payload
        Map<String, Object> payload = new HashMap<>();
        payload.put("prompt", prompt);

        // create signing key (HS256)
        byte[] keyBytes = n8nJwtSecret.getBytes(StandardCharsets.UTF_8);
         // byte[] keyBytes = Base64.getDecoder().decode(n8nJwtSecret);
          SecretKey key = Keys.hmacShaKeyFor(keyBytes);

        Instant now = Instant.now();
        String token = Jwts.builder()
                .setIssuer("springboot-app")
                .setSubject("n8n-call")
                .setIssuedAt(Date.from(now))
                .setExpiration(Date.from(now.plusSeconds(120))) // short validity
                .claim("app", "springboot")
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(token); // sets Authorization: Bearer <token>

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(payload, headers);

        ResponseEntity<String> response;
        try {
            response = restTemplate.postForEntity(n8nWebhookUrlChatJwt, request, String.class);
            System.out.println("Raw response: " + response.getBody());
            System.out.println(" response: " + response);
        } catch (ResourceAccessException ex) {
            Map<String, Object> error = new HashMap<>();
            error.put("error", "Network error: " + ex.getMessage());
            return ResponseEntity.status(HttpStatus.GATEWAY_TIMEOUT).body(error);
        }

// n8n JWT Node failed → invalid
        if (response.getStatusCodeValue() != 200) {
            Map<String, Object> error = new HashMap<>();
            error.put("error", "n8n returned " + response.getStatusCodeValue() +
                    (response.getBody() != null ? " - " + response.getBody() : ""));
            // if n8n JWT invalid → send 401 Unauthorized
            if (response.getBody() != null && response.getBody().contains("can't be verified")) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error);
            }
            return ResponseEntity.status(response.getStatusCode()).body(error);
        }
        Map<String, Object> out = new HashMap<>();
        if(response.getBody() == null){
            out.put("error", "something went wrong");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(out);
        }
// success

        out.put("answer", response.getBody());
        return ResponseEntity.ok(out);
    }


    public ResponseEntity<Map<String, Object>> sendPromptToN8nJwt(String prompt, String authHeader) {
        Map<String, Object> payload = new HashMap<>();
        payload.put("prompt", prompt);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", authHeader); // token client ne bheja
        System.out.println("Authorization "+ authHeader);
        HttpEntity<Map<String, Object>> request = new HttpEntity<>(payload, headers);

        ResponseEntity<String> response;
        try {
            response = restTemplate.postForEntity(n8nWebhookUrlChatJwt, request, String.class);
            System.out.println("response "+ response);
        } catch (ResourceAccessException ex) {
            return ResponseEntity.status(HttpStatus.GATEWAY_TIMEOUT)
                    .body(Map.of("error", "Network error: " + ex.getMessage()));
        }

        if (response.getStatusCodeValue() != 200) {
            return ResponseEntity.status(response.getStatusCode())
                    .body(Map.of("error", response.getBody()));
        }

        Map<String, Object> out = new HashMap<>();
        if(response.getBody() == null){
            out.put("error", "something went wrong");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(out);
        }
        return ResponseEntity.ok(Map.of("answer", response.getBody()));
    }


    public String login(String username, String password) {
        UserEntity user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!user.getPassword().equals(password)) {
            throw new RuntimeException("Invalid credentials");
        }

        JwtSecretEntity secretEntity = secretRepository.findById("default")
                .orElseThrow(() -> new RuntimeException("Secret not found"));

        byte[] keyBytes = secretEntity.getSecret().getBytes(StandardCharsets.UTF_8);
        SecretKey key = Keys.hmacShaKeyFor(keyBytes);

        Instant now = Instant.now();

        return Jwts.builder()
                .setIssuer(secretEntity.getIssuer())
                .setSubject(user.getUsername())
                .claim("role", user.getRole())   // 👈 DB se role aa gaya
                .setIssuedAt(Date.from(now))
                .setExpiration(Date.from(now.plusSeconds(300)))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }




    public void writeData(NumberArraysDTO dto) throws IOException {
        try (DataOutputStream dos = new DataOutputStream(new FileOutputStream(FILE_PATH_DATA))) {
            for (int i : dto.getIntArray()) dos.writeInt(i);
            for (double d : dto.getDoubleArray()) dos.writeDouble(d);
        }
    }

    public NumberArraysDTO readData() throws IOException {
        NumberArraysDTO dto = new NumberArraysDTO();
        try (DataInputStream dis = new DataInputStream(new FileInputStream(FILE_PATH_DATA))) {
            int[] intArray = new int[3]; // Adjust size accordingly
            double[] doubleArray = new double[3];
            for (int i = 0; i < intArray.length; i++) intArray[i] = dis.readInt();
            for (int i = 0; i < doubleArray.length; i++) doubleArray[i] = dis.readDouble();
            dto.setIntArray(intArray);
            dto.setDoubleArray(doubleArray);
        }
        return dto;
    }


    public String startPipe() throws Exception {
        // Create piped streams
        PipedInputStream pis = new PipedInputStream();
        PipedOutputStream pos = new PipedOutputStream(pis);

        // Producer thread (file se read karega)
        Thread producer = new Thread(() -> {
            try (BufferedReader br = new BufferedReader(new FileReader("sample.txt"))) {
                String line;
                while ((line = br.readLine()) != null) {
                    pos.write(line.getBytes());
                    pos.write("\n".getBytes());
                }
                pos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        // Consumer thread (console pe print karega)
        Thread consumer = new Thread(() -> {
            try {
                int data;
                while ((data = pis.read()) != -1) {
                    System.out.print((char) data);
                }
                pis.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        producer.start();
        consumer.start();

        producer.join();
        consumer.join();

        return "Piped stream communication started! Check console logs.";
    }


    // Compress folder
    public String compress(String sourceFolder, String zipFilePath) throws Exception {
        ZipUtils.compressFolder(sourceFolder, zipFilePath);
        return "Compressed successfully: " + zipFilePath;
    }

    // Extract zip
    public String extract(String zipFilePath, String destFolder) throws Exception {
        ZipUtils.extractZip(zipFilePath, destFolder);
        return "Extracted successfully to: " + destFolder;
    }

    // Auto-backup
    public String backup(String sourceFolder, String backupDir) throws Exception {
        String backupFile = ZipUtils.autoBackup(sourceFolder, backupDir);
        return "Backup created: " + backupFile;
    }

    public String createFileNio(String filename) throws IOException {
        Path filePath = baseDir.resolve(filename);
        if (Files.exists(filePath)) {
            return "File already exists: " + filename;
        }
        Files.createFile(filePath);
        return "File created: " + filename;
    }

    public List<String> readFileNio(String filename) throws IOException {
        Path filePath = baseDir.resolve(filename);
        if (!Files.exists(filePath)) {
            throw new IOException("File not found: " + filename);
        }
        return Files.readAllLines(filePath);
    }

    public String copyFileNio(String source, String target) throws IOException {
        Path sourcePath = baseDir.resolve(source);
        Path targetPath = baseDir.resolve(target);
        Files.copy(sourcePath, targetPath, StandardCopyOption.REPLACE_EXISTING);
        return "File copied from " + source + " to " + target;
    }

    public String moveFileNio(String source, String target) throws IOException {
        Path sourcePath = baseDir.resolve(source);
        Path targetPath = baseDir.resolve(target);
        Files.move(sourcePath, targetPath, StandardCopyOption.REPLACE_EXISTING);
        return "File moved from " + source + " to " + target;
    }

    public String deleteFileNio(String filename) throws IOException {
        Path filePath = baseDir.resolve(filename);
        Files.deleteIfExists(filePath);
        return "File deleted: " + filename;
    }

    public void copyFileUsingChannel(String sourcePath, String destPath) throws IOException {
        try (FileChannel sourceChannel = new FileInputStream(sourcePath).getChannel();
             FileChannel destChannel = new FileOutputStream(destPath).getChannel()) {

            ByteBuffer buffer = ByteBuffer.allocateDirect(1024);
            while (sourceChannel.read(buffer) > 0) {
                buffer.flip();
                destChannel.write(buffer);
                buffer.clear();
            }
        }
    }

    public List<Byte> readOnlyBufferDemo() {
        ByteBuffer buffer = ByteBuffer.allocate(5);
        for (int i = 0; i < buffer.capacity(); i++) {
            buffer.put((byte) i);
        }

        ByteBuffer readOnly = buffer.asReadOnlyBuffer();
        readOnly.flip();

        List<Byte> result = new ArrayList<>();
        while (readOnly.hasRemaining()) {
            result.add(readOnly.get());
        }
        return result;
    }


    public String readLargeFile(String filePath) throws IOException {
        File file = new File(filePath);
        try (FileChannel fileChannel = new FileInputStream(file).getChannel()) {
            long fileSize = file.length();

            // Memory Mapped File
            MappedByteBuffer buffer = fileChannel.map(FileChannel.MapMode.READ_ONLY, 0, fileSize);

            // Convert buffer into string
            byte[] bytes = new byte[(int) fileSize];
            buffer.get(bytes);

            return new String(bytes, StandardCharsets.UTF_8);
        }
    }

    public List<String> searchKeyword(String filePath, String keyword, int limit) throws IOException {
        List<String> results = new ArrayList<>();

        Path path = Paths.get(filePath);
        try (FileChannel fileChannel = FileChannel.open(path, StandardOpenOption.READ)) {
            ByteBuffer buffer = ByteBuffer.allocate(1024 * 8); // 8KB buffer
            StringBuilder sb = new StringBuilder();
            int bytesRead;

            while ((bytesRead = fileChannel.read(buffer)) != -1) {
                buffer.flip();
                while (buffer.hasRemaining()) {
                    char c = (char) buffer.get();
                    sb.append(c);
                    if (c == '\n') {
                        String line = sb.toString();
                        if (line.contains(keyword)) {
                            results.add(line.trim());
                            if (results.size() >= limit) {
                                return results; // Stop after limit
                            }
                        }
                        sb.setLength(0);
                    }
                }
                buffer.clear();
            }
        }
        return results;
    }


    public void watchDirectory(String dirPath) throws IOException {
        Path path = Paths.get(dirPath);
        WatchService watchService = FileSystems.getDefault().newWatchService();

        path.register(
                watchService,
                StandardWatchEventKinds.ENTRY_CREATE,
                StandardWatchEventKinds.ENTRY_DELETE,
                StandardWatchEventKinds.ENTRY_MODIFY
        );

        // Run in background thread
        Thread thread = new Thread(() -> {
            try {
                while (true) {
                    WatchKey key = watchService.take(); // blocking call
                    for (WatchEvent<?> event : key.pollEvents()) {
                        System.out.println("Event: " + event.kind() +
                                " -> " + event.context());
                    }
                    key.reset();
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });
        thread.setDaemon(true);
        thread.start();
    }


    // ✅ Safe File Copy using try-with-resources
    public String copyFileSecurely(String sourcePath, String destPath) {
        Path source = Paths.get(sourcePath);
        Path destination = Paths.get(destPath);

        try (InputStream in = Files.newInputStream(source);
             OutputStream out = Files.newOutputStream(destination, StandardOpenOption.CREATE)) {

            byte[] buffer = new byte[8192]; // 8KB buffer
            int bytesRead;

            while ((bytesRead = in.read(buffer)) != -1) {
                out.write(buffer, 0, bytesRead);
            }

            return "File copied successfully from " + source + " to " + destination;

        } catch (IOException e) {
            return "Error copying file: " + e.getMessage();
        }
    }

    // ✅ Encoding Comparison (UTF-8 vs ISO-8859-1)
    public String compareEncoding(String filePath) {
        Path path = Paths.get(filePath);
        StringBuilder result = new StringBuilder();

        try {
            // Read as UTF-8
            String utf8Content = Files.readString(path, StandardCharsets.UTF_8);

            // Read as ISO-8859-1
            String isoContent = Files.readString(path, StandardCharsets.ISO_8859_1);

            result.append("UTF-8 Content: ").append(utf8Content).append("\n\n");
            result.append("ISO-8859-1 Content: ").append(isoContent);

        } catch (IOException e) {
            return "Error reading file: " + e.getMessage();
        }

        return result.toString();
    }


    // 1️⃣ File Copy using try-with-resources
    public String copyFileInterview(String source, String destination) {
        try (InputStream in = Files.newInputStream(Paths.get(source));
             OutputStream out = Files.newOutputStream(Paths.get(destination), StandardOpenOption.CREATE)) {

            byte[] buffer = new byte[8192];
            int bytesRead;
            while ((bytesRead = in.read(buffer)) != -1) {
                out.write(buffer, 0, bytesRead);
            }
            return "✅ File copied successfully.";

        } catch (IOException e) {
            return "❌ Error copying file: " + e.getMessage();
        }
    }

    // 2️⃣ Word Count Program
    public String wordCountInterview(String filePath) {
        try (BufferedReader br = Files.newBufferedReader(Paths.get(filePath))) {
            int lines = 0, words = 0, chars = 0;
            String line;
            while ((line = br.readLine()) != null) {
                lines++;
                words += line.split("\\s+").length;
                chars += line.length();
            }
            return String.format("Lines: %d, Words: %d, Characters: %d", lines, words, chars);
        } catch (IOException e) {
            return "❌ Error: " + e.getMessage();
        }
    }

    // 3️⃣ Object Serialization Example
    public String serializeObjectInterview(String filePath) {
        Employee emp = new Employee(1, "Swapnil", "Java Developer", "secret123");
        try (ObjectOutputStream oos = new ObjectOutputStream(Files.newOutputStream(Paths.get(filePath)))) {
            oos.writeObject(emp);
            return "✅ Employee serialized successfully to: " + filePath;
        } catch (IOException e) {
            return "❌ Serialization error: " + e.getMessage();
        }
    }

    // 4️⃣ File Compression (ZIP)
    public String compressFileInterview(String source, String zipFile) {
        try (FileOutputStream fos = new FileOutputStream(zipFile);
             ZipOutputStream zos = new ZipOutputStream(fos);
             FileInputStream fis = new FileInputStream(source)) {

            ZipEntry entry = new ZipEntry(Paths.get(source).getFileName().toString());
            zos.putNextEntry(entry);

            byte[] buffer = new byte[1024];
            int len;
            while ((len = fis.read(buffer)) > 0) {
                zos.write(buffer, 0, len);
            }

            zos.closeEntry();
            return "✅ File compressed to: " + zipFile;

        } catch (IOException e) {
            return "❌ Compression failed: " + e.getMessage();
        }
    }

    // 5️⃣ Log Parser - Search keyword in large log file
    public String parseLogInterview(String filePath, String keyword) {
        StringBuilder result = new StringBuilder();
        try (BufferedReader br = Files.newBufferedReader(Paths.get(filePath))) {
            String line;
            int count = 0;
            while ((line = br.readLine()) != null) {
                if (line.contains(keyword)) {
                    count++;
                    result.append(line).append("\n");
                }
            }
            return "✅ Found " + count + " occurrences:\n" + result;
        } catch (IOException e) {
            return "❌ Error parsing log: " + e.getMessage();
        }
    }

    // Inner Serializable class for demo
    private static class Employee implements Serializable {
        private static final long serialVersionUID = 1L;
        private int id;
        private String name;
        private String role;
        private transient String password;

        public Employee(int id, String name, String role, String password) {
            this.id = id;
            this.name = name;
            this.role = role;
            this.password = password;
        }
    }


    // ✅ 1. File Copy
    public String copyFileFinal(String source, String destination) {
        try {
            Path copied = Files.copy(Paths.get(source), Paths.get(destination), StandardCopyOption.REPLACE_EXISTING);
            return "✅ File copied to: " + copied;
        } catch (IOException e) {
            return "❌ Copy failed: " + e.getMessage();
        }
    }

    // ✅ 2. File Move
    public String moveFile(String source, String destination) {
        try {
            Path moved = Files.move(Paths.get(source), Paths.get(destination), StandardCopyOption.REPLACE_EXISTING);
            return "✅ File moved to: " + moved;
        } catch (IOException e) {
            return "❌ Move failed: " + e.getMessage();
        }
    }

    // ✅ 3. File Search
    public List<String> searchFiles(String dir, String keyword) {
        List<String> found = new ArrayList<>();
        try {
            Files.walk(Paths.get(dir))
                    .filter(p -> p.toFile().isFile())
                    .filter(p -> p.getFileName().toString().contains(keyword))
                    .forEach(p -> found.add(p.toString()));
        } catch (IOException e) {
            found.add("❌ Search failed: " + e.getMessage());
        }
        return found;
    }

    // ✅ 4. Compression
    public String compressFile(String source, String zipFile) {
        try (FileOutputStream fos = new FileOutputStream(zipFile);
             ZipOutputStream zos = new ZipOutputStream(fos)) {

            Path src = Paths.get(source);
            if (Files.isDirectory(src)) {
                Files.walk(src).filter(Files::isRegularFile).forEach(path -> {
                    try {
                        ZipEntry entry = new ZipEntry(src.relativize(path).toString());
                        zos.putNextEntry(entry);
                        Files.copy(path, zos);
                        zos.closeEntry();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
            } else {
                ZipEntry entry = new ZipEntry(src.getFileName().toString());
                zos.putNextEntry(entry);
                Files.copy(src, zos);
                zos.closeEntry();
            }

            return "✅ Compressed: " + zipFile;
        } catch (IOException e) {
            return "❌ Compression failed: " + e.getMessage();
        }
    }

    // ✅ 5. Decompression
    public String decompressFile(String zipFile, String destDir) {
        try (ZipInputStream zis = new ZipInputStream(new FileInputStream(zipFile))) {
            ZipEntry entry;
            while ((entry = zis.getNextEntry()) != null) {
                Path filePath = Paths.get(destDir, entry.getName());
                Files.createDirectories(filePath.getParent());
                Files.copy(zis, filePath, StandardCopyOption.REPLACE_EXISTING);
            }
            return "✅ Decompressed to: " + destDir;
        } catch (IOException e) {
            return "❌ Decompression failed: " + e.getMessage();
        }
    }

    // ✅ 6. Save Config (Serialization)
    public String saveConfig(Config config, String filePath) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath))) {
            oos.writeObject(config);
            return "✅ Config saved at: " + filePath;
        } catch (IOException e) {
            return "❌ Save failed: " + e.getMessage();
        }
    }

    // ✅ 7. Load Config
    public Config loadConfig(String filePath) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filePath))) {
            return (Config) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    // ✅ 8. Directory Watch
    public String watchDirectoryFinal(String dirPath) {
        try {
            WatchService watchService = FileSystems.getDefault().newWatchService();
            Path path = Paths.get(dirPath);
            path.register(watchService, StandardWatchEventKinds.ENTRY_CREATE,
                    StandardWatchEventKinds.ENTRY_DELETE,
                    StandardWatchEventKinds.ENTRY_MODIFY);

            new Thread(() -> {
                try {
                    WatchKey key;
                    while ((key = watchService.take()) != null) {
                        for (WatchEvent<?> event : key.pollEvents()) {
                            System.out.println("🔔 Event: " + event.kind() + " - " + event.context());
                        }
                        key.reset();
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }).start();

            return "👀 Watching directory: " + dirPath;
        } catch (IOException e) {
            return "❌ Watch failed: " + e.getMessage();
        }
    }

}