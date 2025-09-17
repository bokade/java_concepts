package com.example.javaIO.controller;


import com.example.javaIO.model.FileInfo;
import com.example.javaIO.model.FilePathRequest;
import com.example.javaIO.model.PromptRequest;
import com.example.javaIO.service.FileService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
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

}