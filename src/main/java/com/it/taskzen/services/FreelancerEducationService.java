/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.it.taskzen.services;

import com.it.taskzen.entities.FreelancerEducationEntity;
import com.it.taskzen.entities.FreelancerMasterEntity;
import com.it.taskzen.jwt.JWTService;
import com.it.taskzen.repositories.FreelancerEducationRepository;
import com.it.taskzen.repositories.FreelancerMstRepository;
import java.io.IOException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Aditya Pathak R
 */
@Service
public class FreelancerEducationService {

    @Autowired
    private JWTService jWTService;

    @Autowired
    private FreelancerMstRepository freelancerMstRepository;

    @Autowired
    private FreelancerEducationRepository freelancerEducationRepository;

    public FreelancerEducationEntity addfreelancerEducationDetails(String token, FreelancerEducationEntity freelancerEducationEntity) throws IOException {
        Long freelancer_id = jWTService.extractUserId(token);
        System.out.println("User ID from JWT: " + freelancer_id);

        FreelancerMasterEntity freelancerMasterEntity = freelancerMstRepository.findByFreelancerId(freelancer_id);
//                .orElseThrow(() -> new IllegalStateException("Please create your profile first."));

        freelancerEducationEntity.setFreelancer_id(freelancerMasterEntity);

        return freelancerEducationRepository.save(freelancerEducationEntity);
    }

    public List<FreelancerEducationEntity> getEducationDetailsByToken(String token) {
        Long freelancer_id = jWTService.extractUserId(token);
        return freelancerEducationRepository.findByFreelancerId(freelancer_id);
    }

    public void updateEducationDetails(Long educationId, String university, String course, String startDate, String endDate) {
        FreelancerEducationEntity educationEntity = freelancerEducationRepository.findById(educationId)
                .orElseThrow(() -> new IllegalStateException("Education record not found with ID: " + educationId));

        educationEntity.setUniversity(university);
        educationEntity.setCourse(course);
        educationEntity.setStart_date(startDate);
        educationEntity.setEnd_date(endDate);

        freelancerEducationRepository.save(educationEntity);
    }

    public List<FreelancerEducationEntity> findByFreelancerEducationByFreelancerId(String token) {
        Long freelancer_id = jWTService.extractFreelancerId(token);
        return freelancerEducationRepository.findByFreelancerEducationByFreelancerId(freelancer_id);
    }

    public List<FreelancerEducationEntity> getFreelancerEducationDetailsById(Long freelancer_id) {
        List<FreelancerEducationEntity> educationDetails = freelancerEducationRepository.findByFreelancerId(freelancer_id);
        return educationDetails;
    }

}
