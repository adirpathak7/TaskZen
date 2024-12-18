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
        Long userId = jWTService.extractUserId(token);
//        System.out.println("The user_id extracted from JWT: " + userId);

        UserEntity userEntity = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with ID: " + userId));
        freelancerMasterEntity.setUser(userEntity);

        FreelancerMasterEntity existingFreelancer = freelancerMstRepository.findByUserId(userId);

        Map uploadResult = cloudinary.uploader().upload(profilePicture.getBytes(), ObjectUtils.emptyMap());
        String photoUrl = uploadResult.get("url").toString();
        if (photoUrl == null || photoUrl.isEmpty()) {
            throw new IllegalArgumentException("Profile picture can't be empty!");
        }

        if (existingFreelancer != null) {
            existingFreelancer.setContact(freelancerMasterEntity.getContact());
            existingFreelancer.setCountry(freelancerMasterEntity.getCountry());
            existingFreelancer.setDob(freelancerMasterEntity.getDob());
            existingFreelancer.setGender(freelancerMasterEntity.getGender());
            existingFreelancer.setGithub_link(freelancerMasterEntity.getGithub_link());
            existingFreelancer.setLinkedin_link(freelancerMasterEntity.getLinkedin_link());
            existingFreelancer.setPortfolio_link(freelancerMasterEntity.getPortfolio_link());

            return freelancerMstRepository.save(existingFreelancer);
        } else {
            freelancerMasterEntity.setProfile_picture(photoUrl);
            return freelancerMstRepository.save(freelancerMasterEntity);
        }

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

    public List<FreelancerMasterEntity> getFreelancerStatusPending() {
        return freelancerMstRepository.findFreelancerByPendingStatus();
    }

    public void approveFreelancerStatus(Long freelancer_id) {
        int updatedRows = freelancerMstRepository.approveFreelancerStatus(freelancer_id);

        if (updatedRows == 0) {
            throw new ResourceNotFoundException("Freelancer not found with id: " + freelancer_id);
        }
    }

    public FreelancerMasterEntity getFreelancerDetailsByToken(String token) {
        Long freelancer_id = jWTService.extractUserId(token);
        return freelancerMstRepository.findByUserId(freelancer_id);
    }

}
