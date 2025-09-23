package com.example.javaIO.repository;

import com.example.javaIO.model.JwtSecretEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JwtSecretRepository extends MongoRepository<JwtSecretEntity, String> {
    // tumhe mostly findById("default") use karna padega
}

