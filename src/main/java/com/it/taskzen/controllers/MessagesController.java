/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.it.taskzen.controllers;

import com.it.taskzen.entities.MessagesEntity;
import com.it.taskzen.jwt.JWTService;
import com.it.taskzen.services.MessagesService;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Aditya Pathak R
 */
@RestController
@CrossOrigin(origins = "http://localhost:8080", allowCredentials = "true")
@RequestMapping(value = "/api/users/messages")
public class MessagesController {

    @Autowired
    private MessagesService messagesService;

    @Autowired
    private JWTService jWTService;

    @GetMapping(value = "/hello")
    public String helloMessage() {
        return "Hello from message!";
    }

    @PostMapping(value = "/send", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Map<String, String>> sendMessage(
            @RequestHeader("Authorization") String authorizationHeader,
            @RequestParam("receiver_id") String receiverId,
            @RequestParam("message_content") String messageContent) throws IOException {

        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            return new ResponseEntity<>(Map.of("error", "Invalid or missing Authorization header"), HttpStatus.BAD_REQUEST);
        }

        String token = authorizationHeader.substring(7).trim();

        try {
            MessagesEntity messagesEntity = new MessagesEntity(messageContent);
            messagesEntity.setReceiver_id(Long.parseLong(receiverId));

            messagesService.sendMessage(token, messagesEntity);

            Map<String, String> response = new HashMap<>();
            response.put("message", "Message sent successfully.");
            response.put("data", "1");
            return new ResponseEntity<>(response, HttpStatus.OK);

        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(Map.of("data", "0", "error", e.getMessage()), HttpStatus.BAD_REQUEST);

        } catch (Exception e) {
            return new ResponseEntity<>(Map.of("data", "0", "error", "Something went wrong: " + e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/receive")
    public ResponseEntity<?> receiveMessages() {
        try {
            List<MessagesEntity> receivedMessages = messagesService.getReceivedMessages();

            Map<String, Object> response = new HashMap<>();
            response.put("message", "Messages retrieved successfully.");
            response.put("data", receivedMessages);
            return new ResponseEntity<>(response, HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(Map.of("error", "Something went wrong: " + e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @GetMapping("/history")
    public ResponseEntity<?> getMessagesBetweenUsers(
            @RequestParam("sender_id") Long senderId,
            @RequestParam("receiver_id") Long receiverId) {

        try {
            // Fetch chat history between the users
            List<MessagesEntity> messages = messagesService.getMessagesBetweenUsers(senderId, receiverId);

            Map<String, Object> response = new HashMap<>();
            response.put("message", "Messages retrieved successfully.");
            response.put("data", messages);

            return new ResponseEntity<>(response, HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(Map.of("error", "Failed to retrieve messages: " + e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
