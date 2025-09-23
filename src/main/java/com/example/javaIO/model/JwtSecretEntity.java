package com.example.javaIO.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "jwtSecrets")  // 👈 tumhara collection ka naam
public class JwtSecretEntity {

    @Id
    private String id;   // yaha _id = "default" map hoga

    private String secret;
    private String issuer;

    // getters and setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getSecret() { return secret; }
    public void setSecret(String secret) { this.secret = secret; }

    public String getIssuer() { return issuer; }
    public void setIssuer(String issuer) { this.issuer = issuer; }


}
