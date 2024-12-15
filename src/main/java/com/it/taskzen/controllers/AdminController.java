/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.it.taskzen.controllers;

import com.it.taskzen.entities.AdminEntity;
import com.it.taskzen.repositories.AdminRepository;
import com.it.taskzen.services.AdminService;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Aditya Pathak R
 */
@RestController
@CrossOrigin(origins = "http://localhost:8080", allowCredentials = "true")
@RequestMapping(value = "/api/taskzen/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @RequestMapping(value = "/helloAdmin", method = RequestMethod.GET)
    public String hello() {
        return "Hello from admin side";
    }

    @PostMapping(value = "/addAdmin", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Map<String, String>> signUpUser(
            @RequestParam("email") String email,
            @RequestParam("password") String password) {

        AdminEntity adminEntity = new AdminEntity(email, password);
        adminService.addAdmin(adminEntity);

        Map<String, String> signUpResponse = new HashMap<>();
        signUpResponse.put("message", "Admin added.");
        signUpResponse.put("data", "1");

        return new ResponseEntity<>(signUpResponse, HttpStatus.OK);
    }

    @PostMapping(value = "/login", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Map<String, String>> loginUser(
            @RequestParam("email") String email,
            @RequestParam("password") String password) {

        AdminEntity adminEntity = new AdminEntity(email, password);
        Map<String, String> adminResponse = adminService.loginAdmin(adminEntity);

        return new ResponseEntity<>(adminResponse, HttpStatus.OK);
    }
}
