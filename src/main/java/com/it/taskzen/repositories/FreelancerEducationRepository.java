/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.it.taskzen.repositories;

import com.it.taskzen.entities.FreelancerEducationEntity;
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
public interface FreelancerEducationRepository extends JpaRepository<FreelancerEducationEntity, Object> {

    @Query("SELECT e FROM FreelancerEducationEntity e WHERE e.freelancer_id.freelancer_id = :freelancer_id")
    List<FreelancerEducationEntity> findByFreelancerId(@Param("freelancer_id") Long freelancer_id);
    
    @Query("SELECT e FROM FreelancerEducationEntity e WHERE e.freelancer_education_id = :freelancer_education_id AND e.freelancer_id.freelancer_id = :freelancer_id")
    Optional<FreelancerEducationEntity> findByIdAndFreelancerId(@Param("freelancer_education_id") Long freelancer_education_id, @Param("freelancer_id") Long freelancer_id);
}
