package com.NexusPoint.controller;

import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.NexusPoint.model.*;
import com.NexusPoint.repository.employeeInterface;

import java.util.List;

@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping(value={"users"})
@Setter
@Getter
@Data

public class EmployeeController {
    
}
