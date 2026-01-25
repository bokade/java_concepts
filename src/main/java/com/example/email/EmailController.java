package com.example.email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/email")
public class EmailController {

    @Autowired
    private EmailService emailService;


    @PostMapping("/send")
    public String sendEmail(@RequestBody EmailRequest request) {
        emailService.sendDemoEmail(request.getName(), request.getPhone(), request.getSendTo());
        return "Email sent successfully";
    }
}