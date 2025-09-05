package com.example.javaIO.controller;


import com.example.javaIO.service.FileService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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


}