/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.it.taskzen.controllers;

import com.it.taskzen.entities.ClientProjectEntity;
import com.it.taskzen.exceptions.ResourceNotFoundException;
import com.it.taskzen.jwt.JWTService;
import com.it.taskzen.services.ClientProjectService;
import com.it.taskzen.services.UserService;
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
@RequestMapping(value = "/api/client")
public class ClientProjectController {

    @Autowired
    private ClientProjectService clientProjectService;

    @Autowired
    private JWTService jwtService;

    @Autowired
    private UserService userService;

    @GetMapping(value = "/clientProject")
    public String clientMst() {
        return "Hello Client Project";
    }

    @GetMapping(value = "/getAllClientsProjects")
    public ResponseEntity<List<ClientProjectEntity>> getAllClientsDetails() {
        List<ClientProjectEntity> clientProjects = clientProjectService.getAllClientsProjects();
        if (clientProjects == null || clientProjects.isEmpty()) {
            throw new ResourceNotFoundException("No clients found");
        }
        return new ResponseEntity<>(clientProjects, HttpStatus.OK);
    }

    @PostMapping(value = "/createClientProject", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Map<String, String>> clientProjects(
            @RequestParam("client_project_name") String client_project_name,
            @RequestParam("description") String description,
            @RequestParam("project_picture") MultipartFile project_picture,
            @RequestParam("duration") String duration,
            @RequestParam("minimum_range") String minimum_range,
            @RequestParam("maximum_range") String maximum_range,
            @RequestHeader("Authorization") String token) throws IOException {

        ClientProjectEntity clientProjectEntity = new ClientProjectEntity(client_project_name, description, duration, minimum_range, maximum_range);

        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7).trim();
        }
        clientProjectService.clientProjects(token, clientProjectEntity, project_picture);

        Map<String, String> response = new HashMap<>();
        response.put("message", "Client Project created successfully.");
        response.put("data", "1");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/getProjectsByClientId")
    public ResponseEntity<Map<String, Object>> getProjectByClientId(@RequestHeader("Authorization") String token) throws IOException {
        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7).trim();
        }

        List<ClientProjectEntity> project = clientProjectService.findByProjectByClientId(token);

        Map<String, Object> response = new HashMap<>();
        response.put("message", "Client projects fetched successfully.");
        response.put("data", project);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping(value = "/updateProjectDetail/{client_project_id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Map<String, String>> updateClientProjectDetails(
            @PathVariable("client_project_id") Long client_project_id,
            @RequestParam("client_project_name") String client_project_name,
            @RequestParam("description") String description,
            @RequestParam("duration") String duration,
            @RequestParam("minimum_range") String minimum_range,
            @RequestParam("maximum_range") String maximum_range,
            @RequestParam(value = "project_picture", required = false) MultipartFile project_picture) {
        try {
            clientProjectService.updateClientProjectDetail(client_project_id, client_project_name, description, duration, minimum_range, maximum_range, project_picture);
            return new ResponseEntity("Proejct details updated successfully.", HttpStatus.OK);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

//    @DeleteMapping("/{client_project_id}")
//    public ResponseEntity<Map<String, String>> deletePost(@PathVariable Long postId) {
//        clientProjectService.deletePost(postId);
//
//        Map<String, String> response = new HashMap<>();
//        response.put("message", "Post deleted successfully.");
//        response.put("postId", String.valueOf(postId));
//
//        return new ResponseEntity<>(response, HttpStatus.OK);
//    }
    @PutMapping(value = "/updateStatusInprogress/{client_project_id}")
    public ResponseEntity<Map<String, String>> updateStatusInprogress(
            @PathVariable("client_project_id") Long client_project_id) {
        try {
            clientProjectService.updateStatusToInProgress(client_project_id);
            return new ResponseEntity("Proejct status updated successfully.", HttpStatus.OK);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/updateStatusToHalfCompleted/{client_project_id}")
    public ResponseEntity<String> updateStatusToHalfCompleted(@RequestParam Long client_project_id) {
        boolean success = clientProjectService.updateStatusToHalfCompleted(client_project_id);
        if (success) {
            return ResponseEntity.ok("Project status updated to 'halfCompleted'.");
        } else {
            return ResponseEntity.status(400).body("Failed to update project status.");
        }
    }

    @PutMapping("/updateStatusToCompleted/{client_project_id}")
    public ResponseEntity<String> updateStatusToCompleted(@RequestParam Long client_project_id) {
        boolean success = clientProjectService.updateStatusToCompleted(client_project_id);
        if (success) {
            return ResponseEntity.ok("Project status updated to 'completed'.");
        } else {
            return ResponseEntity.status(400).body("Failed to update project status.");
        }
    }
}
