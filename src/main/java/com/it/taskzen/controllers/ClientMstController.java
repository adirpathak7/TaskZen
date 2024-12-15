/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.it.taskzen.controllers;

import com.it.taskzen.entities.ClientMasterEntity;
import com.it.taskzen.exceptions.ResourceNotFoundException;
import com.it.taskzen.services.ClientMstService;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author Aditya Pathak R
 */
@RestController
@CrossOrigin(origins = "http://localhost:8080", allowCredentials = "true")
@RequestMapping(value = "/api")
public class ClientMstController {

    @Autowired
    private ClientMstService clientMstService;

    @GetMapping(value = "/clientMst")
    public String clientMst() {
        return "Hello Client";
    }

    @GetMapping(value = "/getAllClientsDetails")
    public ResponseEntity<List<ClientMasterEntity>> getAllClientsDetails() {
        List<ClientMasterEntity> clients = clientMstService.getAllClientsDetails();
        if (clients == null || clients.isEmpty()) {
            throw new ResourceNotFoundException("No clients found");
        }
        return new ResponseEntity<>(clients, HttpStatus.OK);
    }

    @PostMapping(value = "/clientsAllDetails", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Map<String, String>> clientsAllDetails(
            @RequestHeader("Authorization") String token,
            @RequestParam("contact") String contact,
            @RequestParam("profile_picture") MultipartFile profilePicture,
            @RequestParam("country") String country,
            @RequestParam("establish") String establish,
            @RequestParam("industry") String industry) throws IOException {

        ClientMasterEntity clientMasterEntity = new ClientMasterEntity(contact, country, establish, industry);
        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7).trim();
        }
        clientMstService.clientsAllDetails(token, clientMasterEntity, profilePicture);
        Map<String, String> response = new HashMap<>();
        response.put("message", "Client details filled successfully.");
        response.put("data", "1");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping(value = "/searchClientById/{client_id}")
    public ResponseEntity searchUser(@PathVariable Long client_id) {
        Optional<ClientMasterEntity> existClient = clientMstService.searchClientById(client_id);
        if (existClient.isPresent()) {
            return new ResponseEntity(existClient.get(), HttpStatus.OK);
        } else {
            throw new ResourceNotFoundException("No freelancers found");
        }
    }

    @PutMapping(value = "/updateClientDetail/{client_id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Map<String, String>> updateClientDetails(
            @PathVariable("client_id") Long client_id,
            @RequestParam("contact") String contact,
            @RequestParam("country") String country,
            @RequestParam("establish") String establish,
            @RequestParam("industry") String industry,
            @RequestParam(value = "profile_picture", required = false) MultipartFile profile_Picture) {
        try {
            clientMstService.updateClientDetail(client_id, contact, country, establish, industry, profile_Picture);
            return new ResponseEntity("User updated successfully.", HttpStatus.OK);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "/getClientStatusPending")
    public ResponseEntity<List<ClientMasterEntity>> getClientStatusPending() {
        List<ClientMasterEntity> clients = clientMstService.getClientStatusPending();
        if (clients == null || clients.isEmpty()) {
            throw new ResourceNotFoundException("No clients found with pending status!");
        }
        return new ResponseEntity<>(clients, HttpStatus.OK);
    }

    @PutMapping(value = "/approveClientStatus/{client_id}")
    public ResponseEntity<Map<String, String>> updateClientDetails(
            @PathVariable("client_id") Long client_id) {
        try {
            clientMstService.approveClientStatus(client_id);
            Map<String, String> response = new HashMap<>();
            response.put("message", "Client status approved by admin.");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(Map.of("error", e.getMessage()), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(Map.of("error", "An error occurred: " + e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }
}
