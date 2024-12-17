/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.it.taskzen.controllers;

import com.it.taskzen.entities.FreelancerMasterEntity;
import com.it.taskzen.entities.SkillsEntity;
import com.it.taskzen.entities.UserEntity;
import com.it.taskzen.exceptions.ResourceNotFoundException;
import com.it.taskzen.jwt.JWTService;
import com.it.taskzen.repositories.UserRepository;
import com.it.taskzen.services.FreelancerMstService;
import com.it.taskzen.services.UserService;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author Aditya Pathak R
 */
@RestController
@CrossOrigin(origins = "http://localhost:8080", allowCredentials = "true")
@RequestMapping(value = "/api")
public class FreelancerMstController {

    @Autowired
    private FreelancerMstService freelancerMstService;

    @Autowired
    private UserService userService;

    @Autowired
    private JWTService jWTService;

    @Autowired
    private UserRepository userRepository;

    @GetMapping(value = "/freelancerMst")
    public String freelancerMst() {
        return "Hello Freelancer";
    }

    @GetMapping(value = "/getAllFreelancersDetails")
    public ResponseEntity<List<FreelancerMasterEntity>> getAllFreelancersDetails() {
        List<FreelancerMasterEntity> freelancers = freelancerMstService.getAllFreelancersDetails();
        if (freelancers == null || freelancers.isEmpty()) {
            throw new ResourceNotFoundException("No freelancers found");
        }
        return new ResponseEntity<>(freelancers, HttpStatus.OK);
    }

    @PostMapping(value = "/freelancersAllDetails", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Map<String, String>> freelancersAllDetails(
            @RequestHeader("Authorization") String token,
            @RequestParam("contact") String contact,
            @RequestParam("profile_picture") MultipartFile profilePicture,
            @RequestParam("country") String country,
            @RequestParam("dob") String dob,
            @RequestParam("gender") String gender,
            @RequestParam(value = "github_link", required = false) String githubLink,
            @RequestParam(value = "linkedin_link", required = false) String linkedinLink,
            @RequestParam(value = "portfolio_link", required = false) String portfolio_link) throws IOException {

        Map<String, String> response = new HashMap<>();

        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7).trim();
        }

        if (token == null || token.isEmpty()) {
            response.put("message", "Authorization token is missing.");
            response.put("data", "0");
            return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
        }

        try {
            // Extract the user_id from the token
            Long userId = jWTService.extractUserId(token);
            System.out.println("Extracted user_id from token: " + userId);

            // Fetch the UserEntity based on user_id
            UserEntity userEntity = userRepository.findById(userId)
                    .orElseThrow(() -> new ResourceNotFoundException("User not found with ID: " + userId));

            // Create a new FreelancerMasterEntity
            FreelancerMasterEntity freelancerMasterEntity = new FreelancerMasterEntity(contact, country, dob, gender, githubLink, linkedinLink, portfolio_link);

            // Set the user_id (which is a UserEntity object) in the FreelancerMasterEntity
            freelancerMasterEntity.setUser(userEntity);

            // Now pass the entity and file to the service
            freelancerMstService.freelancersAllDetails(token, freelancerMasterEntity, profilePicture);

            response.put("message", "Freelancer details filled successfully.");
            response.put("data", "1");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            response.put("message", e.getMessage());
            response.put("data", "0");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "/searchFreelancerById/{freelancer_id}")
    public ResponseEntity searchFreelancer(@PathVariable Long freelancer_id) {
        Optional<FreelancerMasterEntity> existFreelancer = freelancerMstService.searchFreelancerById(freelancer_id);
        if (existFreelancer.isPresent()) {
            return new ResponseEntity(existFreelancer.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity("Freelancer not found!", HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping(value = "/updateFreelancerDetail/{freelancer_id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Map<String, String>> updateFreelancerDetail(
            @PathVariable("freelancer_id") Long freelancer_id,
            @RequestParam("contact") String contact,
            @RequestParam("skills") Set<SkillsEntity> skills,
            @RequestParam("country") String country,
            @RequestParam("dob") String dob,
            @RequestParam("gender") String gender,
            @RequestParam(value = "profile_picture", required = false) MultipartFile profilePicture,
            @RequestParam(value = "github_link", required = false) String githubLink,
            @RequestParam(value = "linkedin_link", required = false) String linkedinLink,
            @RequestParam(value = "portfolio_link", required = false) String portfolio_link) {
        try {
            freelancerMstService.updateFreelancerDetail(freelancer_id, contact, skills, country, dob, gender, profilePicture, githubLink, linkedinLink, portfolio_link);
            return new ResponseEntity("Freelancer updated successfully.", HttpStatus.OK);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
