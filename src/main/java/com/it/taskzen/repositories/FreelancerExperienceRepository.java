/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.it.taskzen.repositories;

import com.it.taskzen.entities.FreelancerExperienceEntity;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Aditya Pathak R
 */
@Repository
public interface FreelancerExperienceRepository extends JpaRepository<FreelancerExperienceEntity, Long> {

    @Query("SELECT e FROM FreelancerExperienceEntity e WHERE e.freelancer_id.freelancer_id = :freelancer_id")
    List<FreelancerExperienceEntity> findByFreelancerId(@Param("freelancer_id") Long freelancer_id);

    @Query("SELECT e FROM FreelancerExperienceEntity e WHERE e.freelancer_experience_id = :freelancer_experience_id AND e.freelancer_id.freelancer_id = :freelancer_id")
    Optional<FreelancerExperienceEntity> findByIdAndFreelancerId(@Param("freelancer_experience_id") Long freelancer_education_id, @Param("freelancer_id") Long freelancer_id);

    @Query("SELECT fe FROM FreelancerExperienceEntity fe WHERE fe.freelancer_experience_id.freelancer_experience_id = :freelancer_experience_id")
    List<FreelancerExperienceEntity> findByFreelancerExperienceByFreelancerId(@Param("freelancer_experience_id") Long freelancer_experience_id);
}
