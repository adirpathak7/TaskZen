/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.it.taskzen.services;

import com.it.taskzen.entities.MessagesEntity;
import com.it.taskzen.jwt.JWTService;
import com.it.taskzen.repositories.MessagesRepository;
import java.io.IOException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Aditya Pathak R
 */
@Service
public class MessagesService {

    @Autowired
    private MessagesRepository messagesRepository;

    @Autowired
    private JWTService jwtService;

    public MessagesEntity sendMessage(String token, MessagesEntity messagesEntity) throws IOException {
        Long userId = jwtService.extractUserId(token);
        String userRole = jwtService.getRoleFromToken(token);

        messagesEntity.setSender_id(userId);
        messagesEntity.setSender_role(userRole);

        return messagesRepository.save(messagesEntity);
    }

    public List<MessagesEntity> getReceivedMessages() {
        return messagesRepository.findAllMessages();
    }
    
    public List<MessagesEntity> getMessagesBetweenUsers(Long senderId, Long receiverId) {
        return messagesRepository.findMessagesBetweenUsers(senderId, receiverId);
    }
}
