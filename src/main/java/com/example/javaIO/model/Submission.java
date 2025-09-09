package com.example.javaIO.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Document(collection="submissions")
public class Submission {

    @Id
    private String id;

    private String subject;
    private String body;
    private Instant receivedAt;

    public Submission(String subject, String body, Instant receivedAt) {
        this.subject = subject;
        this.body = body;
        this.receivedAt = receivedAt;
    }
}
