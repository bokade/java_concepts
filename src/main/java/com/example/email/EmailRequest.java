package com.example.email;

import lombok.Data;

@Data
public class EmailRequest {
    private String name;
    private String phone;
    private String sendTo;
}
