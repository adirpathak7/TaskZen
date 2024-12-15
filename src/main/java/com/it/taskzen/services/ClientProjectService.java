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
import java.util.List;
import java.util.Map;
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
        Long user_id = jWTService.extractUserId(token);
        System.out.println("User ID from JWT: " + user_id);

        ClientMasterEntity clientMasterEntity = clientMstRepository.findByUser_Id(user_id)
                .orElseThrow(() -> new IllegalStateException("Please create your profile first."));

        clientProjectEntity.setClient_id(clientMasterEntity);

        Map uploadResult = cloudinary.uploader().upload(project_picture.getBytes(), ObjectUtils.emptyMap());
        String photoUrl = uploadResult.get("url").toString();
        if (photoUrl == null || photoUrl.isEmpty()) {
            throw new IllegalArgumentException("Project picture can't be empty!");
        }

        clientProjectEntity.setProject_picture(photoUrl);
        return clientProjectRepository.save(clientProjectEntity);
    }

}
