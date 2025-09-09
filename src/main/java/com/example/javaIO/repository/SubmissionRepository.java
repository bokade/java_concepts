package com.example.javaIO.repository;

import com.example.javaIO.model.Submission;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface SubmissionRepository extends MongoRepository<Submission, String> {
}
