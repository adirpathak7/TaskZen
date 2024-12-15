/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.it.taskzen.services;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.it.taskzen.entities.FreelancerMasterEntity;
import com.it.taskzen.entities.SkillsEntity;
import com.it.taskzen.entities.UserEntity;
import com.it.taskzen.exceptions.ResourceNotFoundException;
import com.it.taskzen.jwt.JWTService;
import com.it.taskzen.repositories.FreelancerMstRepository;
import com.it.taskzen.repositories.UserRepository;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author Aditya Pathak R
 */
@Service
public class FreelancerMstService {

    @Autowired
    private FreelancerMstRepository freelancerMstRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JWTService jWTService;

    @Autowired
    private Cloudinary cloudinary;

    public List getAllFreelancersDetails() {
        return freelancerMstRepository.findAll();
    }

    public FreelancerMasterEntity freelancersAllDetails(String token, FreelancerMasterEntity freelancerMasterEntity, MultipartFile profilePicture) throws IOException {
        Long userId;
        try {
            // Extract the user_id from the JWT token
            userId = jWTService.extractUserId(token);
            System.out.println("Extracted user_id from token: " + userId);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid or inappropriate token. Ensure you are using a freelancer token.");
        }

        // Fetch the UserEntity using the user_id
        UserEntity userEntity = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with ID: " + userId));

        // Upload the profile picture (as before)
        Map uploadResult = cloudinary.uploader().upload(profilePicture.getBytes(), ObjectUtils.emptyMap());
        String photoUrl = uploadResult.get("url").toString();

        if (photoUrl == null || photoUrl.isEmpty()) {
            throw new IllegalArgumentException("Profile picture can't be empty!");
        }

        // Set the UserEntity in the FreelancerMasterEntity
        freelancerMasterEntity.setUser_id(userEntity);  // Set the entire UserEntity object here
        freelancerMasterEntity.setProfile_picture(photoUrl);

        // Save the freelancer profile
        return freelancerMstRepository.save(freelancerMasterEntity);
    }

    public Optional<FreelancerMasterEntity> searchFreelancerById(Long freelancerId) {
        return freelancerMstRepository.findById(freelancerId);
    }

    public FreelancerMasterEntity updateFreelancerDetail(
            Long freelancerId,
            String contact,
            Set<SkillsEntity> skills,
            String country,
            String dob,
            String gender,
            MultipartFile profilePicture,
            String githubLink,
            String linkedinLink,
            String portfolio_link) throws IOException {

        Optional<FreelancerMasterEntity> existingFreelancerOpt = freelancerMstRepository.findById(freelancerId);

        if (existingFreelancerOpt.isPresent()) {
            FreelancerMasterEntity existingFreelancer = existingFreelancerOpt.get();

            // Update fields
            existingFreelancer.setContact(contact);
            existingFreelancer.setCountry(country);
            existingFreelancer.setDob(dob);
            existingFreelancer.setGender(gender);
            existingFreelancer.setGithub_link(githubLink);
            existingFreelancer.setLinkedin_link(linkedinLink);
            existingFreelancer.setPortfolio_link(portfolio_link);

            // Update profile picture if provided
            if (profilePicture != null && !profilePicture.isEmpty()) {
                Map uploadResult = cloudinary.uploader().upload(profilePicture.getBytes(), ObjectUtils.emptyMap());
                existingFreelancer.setProfile_picture(uploadResult.get("url").toString());
            }

            // Save and return updated freelancer
            return freelancerMstRepository.save(existingFreelancer);
        } else {
            throw new ResourceNotFoundException("Freelancer not found on this id: " + freelancerId);
        }
    }

}
