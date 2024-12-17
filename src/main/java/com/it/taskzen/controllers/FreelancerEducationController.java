/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.it.taskzen.controllers;

import com.it.taskzen.entities.FreelancerEducationEntity;
import com.it.taskzen.services.FreelancerEducationService;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Aditya Pathak R
 */
    @RestController
@CrossOrigin(origins = "http://localhost:8080", allowCredentials = "true")
@RequestMapping(value = "/api/freelancer")
public class FreelancerEducationController {

    @Autowired
    private FreelancerEducationService freelancerEducationService;

    @GetMapping(value = "/freelancerEducation")
    public String freelancerEducation() {
        return "Hello Freelancer Education";
    }

    @PostMapping(value = "/addEducationDetails", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Map<String, String>> addfreelancerEducationDetails(
            @RequestParam("university") String university,
            @RequestParam("course") String course,
            @RequestParam("start_date") String start_date,
            @RequestParam("end_date") String end_date,
            @RequestHeader("Authorization") String token) throws IOException {

        FreelancerEducationEntity freelancerEducationEntity = new FreelancerEducationEntity(university, course, start_date, end_date);

        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7).trim();
        }
        freelancerEducationService.addfreelancerEducationDetails(token, freelancerEducationEntity);

        Map<String, String> response = new HashMap<>();
        response.put("message", "Freelancer education details added successfully.");
        response.put("data", "1");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/getEducationDetails")
    public ResponseEntity<Map<String, Object>> getFreelancerEducationDetails(@RequestHeader("Authorization") String token) throws IOException {
        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7).trim();
        }

        List<FreelancerEducationEntity> educationDetails = freelancerEducationService.getEducationDetailsByToken(token);

        Map<String, Object> response = new HashMap<>();
        response.put("message", "Freelancer education details fetched successfully.");
        response.put("data", educationDetails);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping(value = "/editEducationDetails/{freelancer_education_id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Map<String, String>> updateEducationDetails(
            @PathVariable("freelancer_education_id") Long educationId,
            @RequestParam("university") String university,
            @RequestParam("course") String course,
            @RequestParam("start_date") String startDate,
            @RequestParam("end_date") String endDate) {

        freelancerEducationService.updateEducationDetails(educationId, university, course, startDate, endDate);

        Map<String, String> response = new HashMap<>();
        response.put("message", "Education details updated successfully.");
        response.put("data", "1");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/getFreelancerEducationByFreelancerId")
    public ResponseEntity<Map<String, Object>> getFreelancerEducationByFreelancerId(@RequestHeader("Authorization") String token) throws IOException {
        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7).trim();
        }

        List<FreelancerEducationEntity> education = freelancerEducationService.findByFreelancerEducationByFreelancerId(token);

        Map<String, Object> response = new HashMap<>();
        response.put("message", "Freelancer education details fetched successfully.");
        response.put("data", education);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
