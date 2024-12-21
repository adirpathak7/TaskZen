/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.it.taskzen.repositories;

import com.it.taskzen.entities.ClientProjectEntity;
import com.it.taskzen.entities.FreelancerMasterEntity;
import com.it.taskzen.entities.PostEntity;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
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
            + "WHERE p.client_id.client_id = :client_id AND p.status = 'pending'")
    List<PostEntity> findProjectsAndFreelancersByClientId(@Param("client_id") Long client_id);

    @Modifying
    @Transactional
    @Query("UPDATE PostEntity p "
            + "SET p.status = 'accepted' "
            + "WHERE p.client_id.client_id = :client_id "
            + "AND p.clientProject = :clientProject "
            + "AND p.freelancer = :freelancer")
    int approveFreelancerProjectStatus(
            @Param("client_id") Long client_id,
            @Param("clientProject") ClientProjectEntity clientProject,
            @Param("freelancer") FreelancerMasterEntity freelancer);

    @Modifying
    @Transactional
    @Query("UPDATE PostEntity p SET p.status = 'rejected' WHERE p.client_id = :client_id AND p.clientProject = :clientProject")
    int rejectFreelancerProjectStatus(@Param("client_id") Long client_id, @Param("clientProject") ClientProjectEntity clientProject);

    @Query("SELECT p FROM PostEntity p WHERE p.clientProject = :clientProject")
    Optional<PostEntity> findByClientProjectId(@Param("clientProject") ClientProjectEntity clientProject);

}
