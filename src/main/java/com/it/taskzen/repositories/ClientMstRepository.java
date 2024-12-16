/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.it.taskzen.repositories;

import com.it.taskzen.entities.ClientMasterEntity;
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
public interface ClientMstRepository extends JpaRepository<ClientMasterEntity, Long> {

    @Query("SELECT c FROM ClientMasterEntity c WHERE c.user_id.user_id = :user_id")
    Optional<ClientMasterEntity> findByUser_Id(@Param("user_id") Long user_id);

    @Query("SELECT c FROM ClientMasterEntity c WHERE c.status = 'pending'")
    List<ClientMasterEntity> findClientByPendingStatus();

    @Modifying
    @Transactional
    @Query("UPDATE ClientMasterEntity c SET c.status = 'approved' WHERE c.client_id = :client_id")
    int approveClientStatus(@Param("client_id") Long client_id);

    @Query("SELECT c FROM ClientMasterEntity c WHERE c.client_id.client_id = :client_id")
    List<ClientMasterEntity> findClientById(@Param("client_id") Long client_id);

}
