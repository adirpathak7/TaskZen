/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.it.taskzen.repositories;

import com.it.taskzen.entities.ClientProjectEntity;
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
public interface ClientProjectRepository extends JpaRepository<ClientProjectEntity, Long> {

    @Query("SELECT cp FROM ClientProjectEntity cp WHERE cp.client_id.client_id = :client_id")
    List<ClientProjectEntity> findByProjectByClientId(@Param("client_id") Long client_id);
}

