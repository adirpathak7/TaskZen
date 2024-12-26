/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.it.taskzen.services;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.it.taskzen.entities.ClientMasterEntity;
import com.it.taskzen.entities.ClientProjectEntity;
import com.it.taskzen.exceptions.ResourceNotFoundException;
import com.it.taskzen.jwt.JWTService;
import com.it.taskzen.repositories.ClientMstRepository;
import com.it.taskzen.repositories.ClientProjectRepository;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author Aditya Pathak R
 */
@Service
public class ClientProjectService {

    @Autowired
    private ClientProjectRepository clientProjectRepository;

    @Autowired
    private ClientMstRepository clientMstRepository;

    @Autowired
    private JWTService jWTService;

    @Autowired
    private Cloudinary cloudinary;

    public List getAllClientsProjects() {
        return clientProjectRepository.findAll();
    }

    public ClientProjectEntity clientProjects(String token, ClientProjectEntity clientProjectEntity, MultipartFile project_picture) throws IOException {
        Long client_id = jWTService.extractClientId(token);
//        System.out.println("User ID from JWT: " + client_id);

        ClientMasterEntity exist_client_id = clientMstRepository.findByUserId(client_id);

        if (exist_client_id == null) {
            throw new IllegalArgumentException("Please create your profile first!");
        }
        clientProjectEntity.setClient_id(exist_client_id);

        Map uploadResult = cloudinary.uploader().upload(project_picture.getBytes(), ObjectUtils.emptyMap());
        String photoUrl = uploadResult.get("url").toString();
        if (photoUrl == null || photoUrl.isEmpty()) {
            throw new IllegalArgumentException("Project picture can't be empty!");
        }

        clientProjectEntity.setProject_picture(photoUrl);
        return clientProjectRepository.save(clientProjectEntity);
    }

    public List<ClientProjectEntity> findByProjectByClientId(String token) {
        Long client_id = jWTService.extractClientId(token);
//        List<ClientProjectEntity> checkClient = clientProjectRepository.findByProjectByClientId(client_id);
//        System.out.println("the client_id is: " + client_id);
//        System.out.println("the checkClient is: " + checkClient);

        return clientProjectRepository.findByProjectByClientId(client_id);
    }

    public ClientProjectEntity updateClientProjectDetail(Long client_project_id, String client_project_name, String description, String duration, String minimum_range, String maximum_range, MultipartFile profile_Picture) throws IOException {
        Optional<ClientProjectEntity> existingClientOpt = clientProjectRepository.findById(client_project_id);

        if (existingClientOpt.isPresent()) {
            ClientProjectEntity existingClient = existingClientOpt.get();

            existingClient.setClient_project_name(client_project_name);
            existingClient.setDescription(description);
            existingClient.setDuration(duration);
            existingClient.setMinimum_range(minimum_range);
            existingClient.setMaximum_range(maximum_range);

            if (profile_Picture != null && !profile_Picture.isEmpty()) {
                Map uploadResult = cloudinary.uploader().upload(profile_Picture.getBytes(), ObjectUtils.emptyMap());
                existingClient.setProject_picture(uploadResult.get("url").toString());
            }

            return clientProjectRepository.save(existingClient);
        } else {
            throw new ResourceNotFoundException("Project not found on this id: " + client_project_id);
        }
    }

    public boolean updateStatusToInProgress(Long clientProjectId) {
        int updatedRows = clientProjectRepository.updateStatusToInProgress(clientProjectId);
        return updatedRows > 0;
    }

    public boolean updateStatusToHalfCompleted(Long clientProjectId) {
        int updatedRows = clientProjectRepository.updateStatusToHalfCompleted(clientProjectId);
        return updatedRows > 0;
    }

    public boolean updateStatusToCompleted(Long clientProjectId) {
        int updatedRows = clientProjectRepository.updateStatusToCompleted(clientProjectId);
        return updatedRows > 0;
    }

    public boolean updateStatusToRemove(Long clientProjectId) {
        int updatedRows = clientProjectRepository.updateStatusToRemove(clientProjectId);
        return updatedRows > 0;
    }

    public boolean updateStatusToDone(Long client_project_id) {
        int updatedRows = clientProjectRepository.updateStatusToDone(client_project_id);
        return updatedRows > 0;
    }

    public Map<String, Object> getProjectCountAndSumRanges(String token) {
        Long client_id = jWTService.extractClientId(token);

        Long projectCount = clientProjectRepository.countProjectsByClientId(client_id);
        Double totalRangeSum = clientProjectRepository.sumMinMaxRangeByClientId(client_id);

        Map<String, Object> response = new HashMap<>();
        response.put("projectCount", projectCount);
        response.put("totalRangeSum", totalRangeSum);

        return response;
    }

}
