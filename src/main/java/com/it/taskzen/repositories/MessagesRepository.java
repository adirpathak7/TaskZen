/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.it.taskzen.repositories;

import com.it.taskzen.entities.MessagesEntity;
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
public interface MessagesRepository extends JpaRepository<MessagesEntity, Long> {

    @Query("SELECT m FROM MessagesEntity m")
    List<MessagesEntity> findAllMessages();
    
    // Find all messages between a specific sender and receiver
    @Query("SELECT m FROM MessagesEntity m WHERE " +
           "(m.sender_id = :userId AND m.receiver_id = :freelancerId) OR " +
           "(m.sender_id = :freelancerId AND m.receiver_id = :userId) " +
           "ORDER BY m.created_at ASC")
    List<MessagesEntity> findMessagesBetweenUsers(
            @Param("userId") Long userId,
            @Param("freelancerId") Long freelancerId
    );
}
