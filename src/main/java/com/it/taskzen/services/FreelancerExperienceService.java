/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.it.taskzen.services;

import com.it.taskzen.entities.FreelancerExperienceEntity;
import com.it.taskzen.entities.FreelancerMasterEntity;
import com.it.taskzen.jwt.JWTService;
import com.it.taskzen.repositories.FreelancerExperienceRepository;
import com.it.taskzen.repositories.FreelancerMstRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Aditya Pathak R
 */
@Service
public class FreelancerExperienceService {

    @Autowired
    private JWTService jWTService;

    @Autowired
    private FreelancerExperienceRepository freelancerExperienceRepository;

    @Autowired
    private FreelancerMstRepository freelancerMstRepository;

    public FreelancerExperienceEntity addFreelancerExperience(String token, FreelancerExperienceEntity freelancerExperienceEntity) {
        Long freelancerId = jWTService.extractUserId(token);
        FreelancerMasterEntity freelancerMasterEntity = freelancerMstRepository.findByFreelancerId(freelancerId)
                .orElseThrow(() -> new IllegalStateException("Freelancer not found!"));

        freelancerExperienceEntity.setFreelancer_id(freelancerMasterEntity);
        return freelancerExperienceRepository.save(freelancerExperienceEntity);
    }

    public List<FreelancerExperienceEntity> getFreelancerExperienceByToken(String token) {
        Long freelancer_id = jWTService.extractUserId(token);
        return freelancerExperienceRepository.findByFreelancerId(freelancer_id);
    }

    public void updateFreelancerExperienceDetails(Long freelancer_experience_id, String company_name, String designation, String starting_date, String ending_date) {
        FreelancerExperienceEntity freelancerExperienceEntity = freelancerExperienceRepository.findById(freelancer_experience_id)
                .orElseThrow(() -> new IllegalStateException("Education record not found with ID: " + freelancer_experience_id));

        freelancerExperienceEntity.setCompany_name(company_name);
        freelancerExperienceEntity.setDesignation(designation);
        freelancerExperienceEntity.setStarting_date(starting_date);
        freelancerExperienceEntity.setEnding_date(ending_date);
        freelancerExperienceRepository.save(freelancerExperienceEntity);
    }
}
