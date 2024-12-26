/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.it.taskzen.repositories;

import com.it.taskzen.entities.ClientProjectEntity;
import java.util.List;
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
public interface ClientProjectRepository extends JpaRepository<ClientProjectEntity, Long> {

    @Query("SELECT cp FROM ClientProjectEntity cp WHERE cp.client_id.client_id = :client_id AND cp.status <> 'remove' ORDER BY cp.created_at ASC")
    List<ClientProjectEntity> findByProjectByClientId(@Param("client_id") Long client_id);

    @Modifying
    @Transactional
    @Query("UPDATE ClientProjectEntity c SET c.status = 'inProgress' WHERE c.client_project_id.client_project_id = :client_project_id")
    int updateStatusToInProgress(@Param("client_project_id") Long client_project_id);

    @Modifying
    @Transactional
    @Query("UPDATE ClientProjectEntity c SET c.status = 'halfCompleted' WHERE c.client_project_id.client_project_id = :client_project_id")
    int updateStatusToHalfCompleted(@Param("client_project_id") Long client_project_id);

    @Modifying
    @Transactional
    @Query("UPDATE ClientProjectEntity c SET c.status = 'completed' WHERE c.client_project_id.client_project_id = :client_project_id")
    int updateStatusToCompleted(@Param("client_project_id") Long client_project_id);

    @Modifying
    @Transactional
    @Query("UPDATE ClientProjectEntity c SET c.status = 'remove' WHERE c.status = 'pending' AND c.client_project_id.client_project_id = :client_project_id")
    int updateStatusToRemove(@Param("client_project_id") Long client_project_id);

    @Modifying
    @Transactional
    @Query("UPDATE ClientProjectEntity c SET c.status = 'done' WHERE c.client_project_id.client_project_id = :client_project_id")
    int updateStatusToDone(@Param("client_project_id") Long client_project_id);

    @Query("SELECT COUNT(cp) FROM ClientProjectEntity cp WHERE cp.client_id.client_id = :client_id")
    Long countProjectsByClientId(@Param("client_id") Long client_id);

    @Query("SELECT SUM(CAST(cp.minimum_range AS double)) + SUM(CAST(cp.maximum_range AS double)) "
            + "FROM ClientProjectEntity cp WHERE cp.client_id.client_id = :client_id")
    Double sumMinMaxRangeByClientId(@Param("client_id") Long client_id);

}
