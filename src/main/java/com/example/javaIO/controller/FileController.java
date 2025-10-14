package com.example.javaIO.controller;


import com.example.javaIO.model.*;
import com.example.javaIO.service.FileService;
import com.example.javaIO.service.StudentService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.crypto.SecretKey;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/io")
public class FileController {

    private final FileService fileService;

    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    @Autowired
    private StudentService studentService;



    // ✅ Write endpoint
    @PostMapping("/write")
    public String writeFile(@RequestParam(defaultValue = "Hello Java I/O") String content) {
        return fileService.writeToFile(content);
    }

    // ✅ Read endpoint
    @GetMapping("/read")
    public String readFile() {
        return fileService.readFromFile();
    }

    @GetMapping("/copyFile")
    public ResponseEntity<String> copyFile(@RequestParam String sourcePath, @RequestParam String destinationPath) {
        try {
            fileService.copyFile(sourcePath, destinationPath);
            return ResponseEntity.ok("File copied successfully from " + sourcePath + " to " + destinationPath);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("File copy failed: " + e.getMessage());
        }
    }

    // POST → write content to file
    @PostMapping("/writes")
    public String writesFile(@RequestBody String content) {
        return fileService.writesToFile(content);
    }

    // GET → read file content
    @GetMapping("/reads")
    public String readsFile() {
        return fileService.readsFromFile();
    }


    @PostMapping("/sendEmail")
    public String sendEmail(@RequestBody Map<String, String> request) {
        String subject = request.get("subject");
        String message = request.get("message");
        return fileService.triggerWorkflow(subject, message);
    }

    // GET API -> Read file character by character
    @GetMapping("/readss")
    public String readFille() {
        return fileService.readFile();
    }

    // POST API -> Write file line by line
    @PostMapping("/writee")
    public String writeFile(@RequestBody List<String> lines) {
        return fileService.writeFile(lines);
    }

    // Create sample text file
    @PostMapping("/create")
    public String createFile(@RequestParam String path) throws IOException {
        return fileService.createSampleTextFile(path);
    }

    // Copy text file using BufferedReader/Writer
    @PostMapping("/copy-text")
    public String copyText(@RequestParam String source, @RequestParam String dest) throws IOException {
        return fileService.copyTextFile(source, dest);
    }

    // Copy large file WITH buffer
    @PostMapping("/copy-large-buffered")
    public String copyLargeBuffered(@RequestParam String source, @RequestParam String dest) throws IOException {
        return fileService.copyLargeFileWithBuffer(source, dest);
    }

    // Copy large file WITHOUT buffer
    @PostMapping("/copy-large-unbuffered")
    public String copyLargeUnbuffered(@RequestParam String source, @RequestParam String dest) throws IOException {
        return fileService.copyLargeFileWithoutBuffer(source, dest);
    }

    // ✅ API 1: Process string (StringReader -> StringWriter)
    @GetMapping("/string-process")
    public String processString(@RequestParam String input) throws IOException {
        return fileService.processString(input);
    }

    // ✅ API 2: Format file (File -> PrintWriter)
    @GetMapping("/file-format")
    public String formatFile() throws IOException {
        return fileService.formatFile();
    }


    // 🔹 Copy File API (JSON Body)
    @PostMapping("/copy")
    public String copyFile(@RequestBody Map<String, String> request) {
        try {
            String source = request.get("source");
            String destination = request.get("destination");
            return fileService.copyyFile(source, destination);
        } catch (Exception e) {
            return "Error while copying: " + e.getMessage();
        }
    }

    // 🔹 Merge Files API (JSON Body)
    @PostMapping("/merge")
    public String mergeFiles(@RequestBody Map<String, String> request) {
        try {
            String file1 = request.get("file1");
            String file2 = request.get("file2");
            String destination = request.get("destination");
            return fileService.mergeFiles(file1, file2, destination);
        } catch (Exception e) {
            return "Error while merging: " + e.getMessage();
        }
    }




    @PostMapping("/reading")
    public String readFile(@RequestBody String path) {
        try {
            // JSON body me agar string aayegi, to uske andar quotes honge → unhe trim karo
            path = path.replace("\"", "").trim();
            return fileService.readFileContent(path);
        } catch (Exception e) {
            return "Error reading file: " + e.getMessage();
        }
    }

    @PostMapping("/listFiles")
    public ResponseEntity<?> listFiles(
            @RequestBody FilePathRequest request,
            @RequestParam(name = "recursive", defaultValue = "false") boolean recursive) {

        try {
            List<FileInfo> files = fileService.listFiles(request.getPath(), recursive);
            return ResponseEntity.ok(files);
        } catch (IllegalArgumentException iae) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", iae.getMessage()));
        } catch (IOException ioe) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "I/O Error: " + ioe.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Unexpected error: " + e.getMessage()));
        }
    }


    // Create directory and files
    @PostMapping("/create-dir")
    public String createDirectory(@RequestBody Map<String, Object> request) throws IOException {
        String dirName = (String) request.get("dirName");
        String[] fileNames = ((java.util.List<String>) request.get("fileNames")).toArray(new String[0]);

        return fileService.createDirWithFiles(dirName, fileNames);
    }

    // Delete directory recursively
    @DeleteMapping("/delete/{dirName}")
    public String deleteDirectory(@PathVariable String dirName) {
        return fileService.deleteDirectory(dirName);
    }


    // Update 5th character → PUT request
    @PutMapping("/update-char")
    public String updateChar(@RequestParam char newChar) {
        try {
            return fileService.updateFifthCharacter(newChar);
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }

    // Read last 10 bytes → GET request
    @GetMapping("/read-last-bytes")
    public String readLastBytes() {
        try {
            return fileService.readLast10Bytes();
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }



    // GET - list files in directory
    @GetMapping("/lists")
    public List<FileInfos> listFiless(@RequestParam String path) {
        return fileService.listFiles(path);
    }

    // POST - create new file
    @PostMapping("/creates")
    public String createFiles(@RequestParam String path) throws IOException {
        return fileService.createFiles(path);
    }

    // DELETE - delete file
    @DeleteMapping("/deletes")
    public String deleteFile(@RequestParam String path) {
        return fileService.deleteFiles(path);
    }


    @PostMapping("/sendEmails")
    public ResponseEntity<String> receive(@RequestBody Map<String, String> req) {
        String subject = req.get("subject");
        String body = req.get("body");
        if (subject == null || body == null) {
            return ResponseEntity.badRequest().body("subject and body are required");
        }
        fileService.process(subject, body);
        return ResponseEntity.ok("Saved locally & pushed to n8n");
    }

/*
    @PostMapping("/chat")
    public ResponseEntity<Map<String, Object>> chat(@RequestBody PromptRequest request) {
        String answer = fileService.sendPromptToN8n(request.getPrompt());
        Map<String, Object> out = new HashMap<>();
        out.put("answer", answer);
        return ResponseEntity.ok(out);
    }

*/
    //giving wrong status code
    @PostMapping("/chat-secure")
    public ResponseEntity<Map<String, Object>> chatSecure(@RequestBody PromptRequest request) {
        ResponseEntity<Map<String, Object>> answer = fileService.sendPromptToN8n(request.getPrompt());
        Map<String, Object> out = new HashMap<>();
        out.put("answer", answer);
        System.out.println("response: " + answer);
        return ResponseEntity.ok(out);
    }


    @PostMapping("/chat-secure-header")
    public ResponseEntity<Map<String, Object>> chatSecureHeader(@RequestBody PromptRequest request) {
        // Directly return the service ResponseEntity
        return fileService.sendPromptToN8n(request.getPrompt());
    }

    @PostMapping("/chat-secure-jwt")
    public ResponseEntity<Map<String, Object>> chatSecureJwt(@RequestBody PromptRequest request) {
        return fileService.sendPromptToN8nJwt(request.getPrompt());
    }


    @Value("${n8n.jwt.secret}")
    private String n8nJwtSecret;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> body) {
        String username = body.get("username");
        String password = body.get("password");

        // Dummy check (real project me DB se validate karna hoga)
        if (!"swapnil".equals(username) || !"swapnil123".equals(password)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", "Invalid credentials"));
        }

        byte[] keyBytes = n8nJwtSecret.getBytes(StandardCharsets.UTF_8);
        SecretKey key = Keys.hmacShaKeyFor(keyBytes);

        Instant now = Instant.now();
        String token = Jwts.builder()
                .setIssuer("springboot-app")
                .setSubject(username)
                .setIssuedAt(Date.from(now))
                .setExpiration(Date.from(now.plusSeconds(300))) // 5 min valid
                .claim("role", "USER") // custom claim
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();

        return ResponseEntity.ok(Map.of("token", token));
    }


    //@PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/chat-secure-jwt-with-header-token")
    public ResponseEntity<Map<String, Object>> chatSecure(@RequestBody Map<String, String> body,
                                                          @RequestHeader("Authorization") String authHeader) {
        String prompt = body.get("prompt");
        return fileService.sendPromptToN8nJwt(prompt, authHeader);
    }


    @PostMapping("/login-n8n")
    public ResponseEntity<?> loginN8n(@RequestBody Map<String, String> body) {
        try {
            String token = fileService.login(body.get("username"), body.get("password"));
            return ResponseEntity.ok(Map.of("token", token));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", e.getMessage()));
        }
    }

    @PostMapping("/write-data")
    public String writeData(@RequestBody NumberArraysDTO dto) {
        try {
            fileService.writeData(dto);
            return "Data written successfully!";
        } catch (IOException e) {
            return "Error writing data: " + e.getMessage();
        }
    }

    @GetMapping("/read-data")
    public NumberArraysDTO readData() {
        try {
            return fileService.readData();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }


    // ➕ Add Student
    @PostMapping("/add-student")
    public Student addStudent(@RequestBody Student student) {
        return studentService.addStudent(student);
    }

    // 📃 Get all students
    @GetMapping("/get-students")
    public List<Student> getAllStudents() {
        return studentService.getAllStudents();
    }

    // 🔍 Search by ID
    @GetMapping("/get-student-by-id/{id}")
    public Student getStudentById(@PathVariable int id) {
        return studentService.getStudentById(id);
    }

    @GetMapping("/start")
    public String startPipe() throws Exception {
        return fileService.startPipe();
    }


    // ✅ Compress folder
    @PostMapping("/compress")
    public String compress(@RequestParam String sourceFolder,
                           @RequestParam String zipFilePath) {
        try {
            return fileService.compress(sourceFolder, zipFilePath);
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }

    // ✅ Extract zip
    @PostMapping("/extract")
    public String extract(@RequestParam String zipFilePath,
                          @RequestParam String destFolder) {
        try {
            return fileService.extract(zipFilePath, destFolder);
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }

    // ✅ Auto-backup
    @PostMapping("/backup")
    public String backup(@RequestParam String sourceFolder,
                         @RequestParam String backupDir) {
        try {
            return fileService.backup(sourceFolder, backupDir);
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }



    @PostMapping("/create-nio")
    public ResponseEntity<String> createFileNio(@RequestParam String filename) throws IOException {
        return ResponseEntity.ok(fileService.createFileNio(filename));
    }

    @GetMapping("/read-nio")
    public ResponseEntity<List<String>> readFileNio(@RequestParam String filename) throws IOException {
        return ResponseEntity.ok(fileService.readFileNio(filename));
    }

    @PostMapping("/copy-nio")
    public ResponseEntity<String> copyFileNio(@RequestParam String source, @RequestParam String target) throws IOException {
        return ResponseEntity.ok(fileService.copyFileNio(source, target));
    }

    @PostMapping("/move-nio")
    public ResponseEntity<String> moveFileNio(@RequestParam String source, @RequestParam String target) throws IOException {
        return ResponseEntity.ok(fileService.moveFileNio(source, target));
    }

    @DeleteMapping("/delete-nio")
    public ResponseEntity<String> deleteFileNio(@RequestParam String filename) throws IOException {
        return ResponseEntity.ok(fileService.deleteFileNio(filename));
    }


    @PostMapping("/copy-buffered-channel")
    public ResponseEntity<String> copyFileChannel(@RequestParam("source") String source, @RequestParam("dest") String dest) {
        try {
            fileService.copyFileUsingChannel(source, dest);
            return ResponseEntity.ok("File copied successfully!");
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error: " + e.getMessage());
        }
    }

    @GetMapping("/readonly-buffer")
    public ResponseEntity<List<Byte>> getReadOnlyBufferDemo() {
        return ResponseEntity.ok(fileService.readOnlyBufferDemo());
    }

    @GetMapping("/read-file-nioo")
    public String readFileNioAdv(@RequestParam String path) throws Exception {
        return fileService.readLargeFile(path);
    }


    // 1. Log Analyzer
    @GetMapping("/analyze-log")
    public List<String> analyzeLog(
            @RequestParam String filePath,
            @RequestParam String keyword,
            @RequestParam(defaultValue = "10") int limit
    ) throws IOException {
        return fileService.searchKeyword(filePath, keyword, limit);
    }

    // 2. Directory Watcher
    @PostMapping("/watch-dir")
    public String watchDirectory(@RequestParam String dirPath) throws IOException {
        fileService.watchDirectory(dirPath);
        return "Started watching directory: " + dirPath;
    }


    // File Copy Endpoint
    @PostMapping("/copy-nio-secure")
    public String copyFileSecure(@RequestParam String source, @RequestParam String destination) {
        return fileService.copyFileSecurely(source, destination);
    }

    // Encoding Comparison Endpoint
    @GetMapping("/compare-encoding-nio-secure")
    public String compareEncoding(@RequestParam String filePath) {
        return fileService.compareEncoding(filePath);
    }




    // for java io interview purpose

    // 1️⃣ File Copy
    @PostMapping("/copy-interview")
    public String copyFileInterview(@RequestParam String source, @RequestParam String destination) {
        return fileService.copyFileInterview(source, destination);
    }

    // 2️⃣ Word Count
    @GetMapping("/word-count-interview")
    public String wordCountInterview(@RequestParam String filePath) {
        return fileService.wordCountInterview(filePath);
    }

    // 3️⃣ Serialization
    @PostMapping("/serialize-interview")
    public String serializeObjectInterview(@RequestParam String filePath) {
        return fileService.serializeObjectInterview(filePath);
    }

    // 4️⃣ Compression
    @PostMapping("/compress-interview")
    public String compressFileInterview(@RequestParam String source, @RequestParam String zipFile) {
        return fileService.compressFileInterview(source, zipFile);
    }


    // 5️⃣ Log Parser
    @GetMapping("/parse-log-interview")
    public String parseLogInterview(@RequestParam String filePath, @RequestParam String keyword) {
        return fileService.parseLogInterview(filePath, keyword);
    }


    @PostMapping("/copy-final")
    public String copyFileFinal(@RequestParam String source, @RequestParam String destination) {
        return fileService.copyFileFinal(source, destination);
    }

    @PostMapping("/move-final")
    public String moveFile(@RequestParam String source, @RequestParam String destination) {
        return fileService.moveFile(source, destination);
    }

    @GetMapping("/search-final")
    public List<String> searchFiles(@RequestParam String dir, @RequestParam String keyword) {
        return fileService.searchFiles(dir, keyword);
    }

    @PostMapping("/compress-final")
    public String compressFinal(@RequestParam String source, @RequestParam String zipFile) {
        return fileService.compressFile(source, zipFile);
    }

    @PostMapping("/decompress-final")
    public String decompress(@RequestParam String zipFile, @RequestParam String destDir) {
        return fileService.decompressFile(zipFile, destDir);
    }

    @PostMapping("/save-config-final")
    public String saveConfig(@RequestBody Config config, @RequestParam String path) {
        return fileService.saveConfig(config, path);
    }

    @GetMapping("/load-config-final")
    public Config loadConfig(@RequestParam String path) {
        return fileService.loadConfig(path);
    }

    @GetMapping("/watch-final")
    public String watchDirFinal(@RequestParam String dirPath) {
        return fileService.watchDirectoryFinal(dirPath);
    }
}