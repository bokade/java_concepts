package com.example.javaIO.service;
import com.example.javaIO.model.Student;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Service
public class StudentService {
    private static final String FILE_PATH = "students.ser";
    private List<Student> students = new ArrayList<>();

    public StudentService() {
        loadStudents();
    }

    // ✅ Add new student
    public Student addStudent(Student student) {
        students.add(student);
        saveStudents();
        return student;
    }

    // ✅ Get all students
    public List<Student> getAllStudents() {
        return students;
    }

    // ✅ Search student by ID
    public Student getStudentById(int id) {
        return students.stream()
                .filter(s -> s.getId() == id)
                .findFirst()
                .orElse(null);
    }

    // 🔹 Save students (Serialization)
    private void saveStudents() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_PATH))) {
            oos.writeObject(students);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 🔹 Load students (Deserialization)
    private void loadStudents() {
        File file = new File(FILE_PATH);
        if (file.exists()) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_PATH))) {
                students = (List<Student>) ois.readObject();
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}
