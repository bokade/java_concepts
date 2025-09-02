package com.example.javaIO.controller;


import com.example.javaIO.service.FileService;
import org.springframework.web.bind.annotation.*;

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
}