package com.example.javaIO.service;

import com.example.javaIO.model.Employee;
import org.springframework.stereotype.Service;

import java.io.*;

@Service
public class EmployeeService {

    private static final String FILE_NAME = "employee.ser";

    // Serialize employee object to file
    public String saveEmployee(Employee emp) {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            out.writeObject(emp);
            return "Employee serialized and saved to file!";
        } catch (IOException e) {
            e.printStackTrace();
            return "Error while saving employee!";
        }
    }

    // Deserialize employee object from file
    public Employee getEmployee() {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
            return (Employee) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }
}
