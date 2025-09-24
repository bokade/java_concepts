package com.example.javaIO.controller;

import com.example.javaIO.model.Employee;
import com.example.javaIO.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/employee")
public class EmployeeController {

    @Autowired
    private EmployeeService service;

    // Save employee (serialize)
    @PostMapping("/save")
    public String saveEmployee(@RequestBody Employee emp) {
        return service.saveEmployee(emp);
    }

    // Get employee (deserialize)
    @GetMapping("/get")
    public Employee getEmployee() {
        return service.getEmployee();
    }
}

