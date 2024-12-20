/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.it.taskzen.controllers;

import com.it.taskzen.entities.FreelancerExperienceEntity;
import com.it.taskzen.services.FreelancerExperienceService;
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
public class FreelancerExperienceController {

    @Autowired
    private FreelancerExperienceService freelancerExperienceService;

    @PostMapping(value = "/addExperienceDetails", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Map<String, String>> addFreelancerExperienceDetails(
            @RequestParam("company_name") String company_name,
            @RequestParam("designation") String designation,
            @RequestParam("starting_date") String starting_date,
            @RequestParam("ending_date") String ending_date,
            @RequestHeader("Authorization") String token) throws IOException {

        FreelancerExperienceEntity freelancerExperienceEntity = new FreelancerExperienceEntity(company_name, designation, starting_date, ending_date);

        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7).trim();
        }
        freelancerExperienceService.addFreelancerExperience(token, freelancerExperienceEntity);

        Map<String, String> response = new HashMap<>();
        response.put("message", "Freelancer experience details added successfully.");
        response.put("data", "1");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/getExperienceDetails")
    public ResponseEntity<Map<String, Object>> getFreelancerExperienceDetails(@RequestHeader("Authorization") String token) throws IOException {
        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7).trim();
        }

        List<FreelancerExperienceEntity> experienceDetails = freelancerExperienceService.getFreelancerExperienceByToken(token);

        Map<String, Object> response = new HashMap<>();
        response.put("message", "Freelancer experience details fetched successfully.");
        response.put("data", experienceDetails);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping(value = "/editExperienceDetails/{freelancer_experience_id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Map<String, String>> updateFreelancerExperienceDetails(
            @PathVariable("freelancer_experience_id") Long freelancer_experience_id,
            @RequestParam("company_name") String company_name,
            @RequestParam("designation") String designation,
            @RequestParam("starting_date") String starting_date,
            @RequestParam("ending_date") String ending_date) {

        freelancerExperienceService.updateFreelancerExperienceDetails(freelancer_experience_id, company_name, designation, starting_date, ending_date);

        Map<String, String> response = new HashMap<>();
        response.put("message", "Experince details updated successfully.");
        response.put("data", "1");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/getFreelancerExperienceByFreelancerId")
    public ResponseEntity<Map<String, Object>> getFreelancerExperienceByFreelancerId(@RequestHeader("Authorization") String token) throws IOException {
        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7).trim();
        }

        List<FreelancerExperienceEntity> experience = freelancerExperienceService.findByFreelancerExperienceByFreelancerId(token);

        Map<String, Object> response = new HashMap<>();
        response.put("message", "Freelancer experience details fetched successfully.");
        response.put("data", experience);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping(value = "/getFreelancerExperienceDetailsById/{freelancer_id}")
    public ResponseEntity<Map<String, Object>> getFreelancerExperienceDetailsById(
            @PathVariable("freelancer_id") Long freelancer_id) {
        try {
            List<FreelancerExperienceEntity> experienceDetails = freelancerExperienceService.getFreelancerExperienceDetailsById(freelancer_id);
            if (experienceDetails.isEmpty()) {
                return new ResponseEntity<>(Map.of("message", "No experience details found for this freelancer."), HttpStatus.NOT_FOUND);
            }
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Freelancer experience details fetched successfully.");
            response.put("data", experienceDetails);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(Map.of("error", "An error occurred: " + e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

}
