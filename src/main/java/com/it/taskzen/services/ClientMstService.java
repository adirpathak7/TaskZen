/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.it.taskzen.services;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.it.taskzen.entities.ClientMasterEntity;
import com.it.taskzen.entities.UserEntity;
import com.it.taskzen.exceptions.ResourceNotFoundException;
import com.it.taskzen.jwt.JWTService;
import com.it.taskzen.repositories.ClientMstRepository;
import com.it.taskzen.repositories.UserRepository;
import java.io.IOException;
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
public class ClientMstService {

    @Autowired
    private ClientMstRepository clientMstRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JWTService jWTService;

    @Autowired
    private Cloudinary cloudinary;

    public List getAllClientsDetails() {
        return clientMstRepository.findAll();
    }

    public ClientMasterEntity clientsAllDetails(String token, ClientMasterEntity clientMasterEntity, MultipartFile profilePicture) throws IOException {
        Long userId = jWTService.extractUserId(token);
        System.out.println("the user_id is in clientsAllDetails service " + userId);

        UserEntity userEntity = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with ID: " + userId));

        clientMasterEntity.setUser_id(userEntity);

        Map uploadResult = cloudinary.uploader().upload(profilePicture.getBytes(), ObjectUtils.emptyMap());
        String photoUrl = uploadResult.get("url").toString();
        if (photoUrl == null || photoUrl.isEmpty()) {
            throw new IllegalArgumentException("Profile picture can't be empty!");
        }
        clientMasterEntity.setProfile_picture(photoUrl);

        return clientMstRepository.save(clientMasterEntity);
    }

    public Optional<ClientMasterEntity> searchClientById(Long client_id) {
        return clientMstRepository.findById(client_id);
    }

    public ClientMasterEntity updateClientDetail(Long client_id, String contact, String country,
            String establish, String industry, MultipartFile profile_Picture) throws IOException {
        Optional<ClientMasterEntity> existingClientOpt = clientMstRepository.findById(client_id);

        if (existingClientOpt.isPresent()) {
            ClientMasterEntity existingClient = existingClientOpt.get();

            existingClient.setContact(contact);
            existingClient.setCountry(country);
            existingClient.setEstablish(establish);
            existingClient.setIndustry(industry);

            if (profile_Picture != null && !profile_Picture.isEmpty()) {
                Map uploadResult = cloudinary.uploader().upload(profile_Picture.getBytes(), ObjectUtils.emptyMap());
                existingClient.setProfile_picture(uploadResult.get("url").toString());
            }

            return clientMstRepository.save(existingClient);
        } else {
            throw new ResourceNotFoundException("User not found on this id: " + client_id);
        }
    }

    public List<ClientMasterEntity> getClientStatusPending() {
        return clientMstRepository.findClientByPendingStatus();
    }

    public void approveClientStatus(Long client_id) throws IOException {
        int updatedRows = clientMstRepository.approveClientStatus(client_id);

        if (updatedRows == 0) {
            throw new ResourceNotFoundException("Client not found with id: " + client_id);
        }
    }
}
