/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.it.taskzen.repositories;

import com.it.taskzen.entities.ClientProjectEntity;
import com.it.taskzen.entities.FreelancerMasterEntity;
import com.it.taskzen.entities.PostEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Aditya Pathak R
 */
@Repository
public interface PostRepository extends JpaRepository<PostEntity, Long> {

    boolean existsByFreelancerAndClientProject(FreelancerMasterEntity freelancer, ClientProjectEntity clientProject);

    @Query("SELECT p FROM PostEntity p WHERE p.freelancer.freelancer_id = :freelancer_id")
    List<PostEntity> findByFreelancer(@Param("freelancer_id") Long freelancer_id);

    @Query("SELECT p FROM PostEntity p "
            + "JOIN FETCH p.freelancer f "
            + "WHERE p.client_id.client_id = :client_id")
    List<PostEntity> findProjectsAndFreelancersByClientId(@Param("client_id") Long client_id);
}
