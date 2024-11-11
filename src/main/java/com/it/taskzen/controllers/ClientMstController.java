/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.it.taskzen.controllers;

import com.it.taskzen.entities.ClientMasterEntity;
import com.it.taskzen.exceptions.ResourceNotFoundException;
import com.it.taskzen.services.ClientMstService;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
    public ResponseEntity<Map<String, String>> getAllClientsDetails(ClientMasterEntity clientMasterEntity) {
        List clientsList = clientMstService.getAllClientsDetails(clientMasterEntity);
        try {
            Map<String, String> clientsResponse = new HashMap<>();
            if (clientsList == null || clientsList.isEmpty()) {
                clientsResponse.put("message", "Client not found!");
                clientsResponse.put("data", "0");
                return new ResponseEntity(clientsResponse, HttpStatus.NOT_FOUND);
            } else {
                clientsResponse.put("message", "Exist Client's are.");
                clientsResponse.put("data", "1");
                return new ResponseEntity(clientsList, HttpStatus.OK);
            }
        } catch (Exception ex) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("message", "Something went wrong! " + ex.getMessage());
            errorResponse.put("data", "0");
            return new ResponseEntity(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

//    @PostMapping(value = "/getAllClientsDetails", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
//    public ResponseEntity<Map<String, String>> getAllClientsDetails(
//            @RequestParam("first_name") String first_name,
//            @RequestParam("last_name") String last_name,
//            @RequestParam("email") String email,
//            @RequestParam("password") String password,
//            @RequestParam("role") ClientEntity.Role role) {
//
//        try {
//            ClientMasterEntity clientEntity = new ClientMasterEntity(first_name, last_name, email, password, role);
//            clientMstService.signUpClient(clientEntity);
//
//            Map<String, String> signUpResponse = new HashMap<>();
//            signUpResponse.put("message", "Client registered successfully.");
//            signUpResponse.put("data", "1");
//            return new ResponseEntity<>(signUpResponse, HttpStatus.OK);
//        } catch (ResourceNotFoundException ex) {
//            Map<String, String> resNotFoundResponse = new HashMap<>();
//            resNotFoundResponse.put("error", "Client not found! " + ex.getMessage());
//            resNotFoundResponse.put("data", "0");
//            return new ResponseEntity<>(resNotFoundResponse, HttpStatus.NOT_FOUND);
//        } catch (IllegalArgumentException ex) {
//            Map<String, String> illArgExceResponse = new HashMap<>();
//            illArgExceResponse.put("error", ex.getMessage());
//            illArgExceResponse.put("data", "0");
//            return new ResponseEntity<>(illArgExceResponse, HttpStatus.BAD_REQUEST);
//        } catch (InternalError ex) {
//            Map<String, String> errorResponse = new HashMap<>();
//            errorResponse.put("error", "Invalid token or token maybe expire! " + ex.getMessage());
//            errorResponse.put("data", "0");
//            return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
//        } catch (Exception ex) {
//            System.out.println("Something went wrong! " + ex.getMessage());
//            Map<String, String> errorResponse = new HashMap<>();
//            errorResponse.put("error", "Somthing wen wrong!");
//            errorResponse.put("data", "0");
//            return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }
}
