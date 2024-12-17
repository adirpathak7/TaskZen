/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.it.taskzen.repositories;

import com.it.taskzen.entities.FreelancerMasterEntity;
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
public interface FreelancerMstRepository extends JpaRepository<FreelancerMasterEntity, Long> {

    @Query("SELECT f FROM FreelancerMasterEntity f WHERE f.freelancer_id = :freelancer_id")
    Optional<FreelancerMasterEntity> findByFreelancerId(@Param("freelancer_id") Long freelancer_id);

    @Query("SELECT c FROM FreelancerMasterEntity c WHERE c.user.user_id = :user_id")
    FreelancerMasterEntity findByUserId(@Param("user_id") Long user_id);

    @Query("SELECT f FROM FreelancerMasterEntity f WHERE f.status = 'pending'")
    List<FreelancerMasterEntity> findFreelancerByPendingStatus();

    @Modifying
    @Transactional
    @Query("UPDATE FreelancerMasterEntity c SET c.status = 'approved' WHERE c.freelancer_id = :freelancer_id")
    int approveFreelancerStatus(@Param("freelancer_id") Long freelancer_id);

}
