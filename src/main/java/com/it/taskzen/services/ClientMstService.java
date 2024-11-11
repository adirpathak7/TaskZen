/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.it.taskzen.services;

import com.it.taskzen.entities.ClientMasterEntity;
import com.it.taskzen.repositories.ClientMstRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Aditya Pathak R
 */
@Service
public class ClientMstService {

    @Autowired
    private ClientMstRepository clientMstRepository;
    
    public List getAllClientsDetails(ClientMasterEntity clientMasterEntity) {
        return clientMstRepository.findAll();
    }

//    public ClientMasterEntity clientsAllDetails(ClientMasterEntity clientMasterEntity) {
//        if (clientMasterEntity.getFirst_name() == null || clientMasterEntity.getFirst_name().isEmpty()) {
//            throw new IllegalArgumentException("First name can't be empty!");
//        }
//        if (clientMasterEntity.getLast_name() == null || clientMasterEntity.getLast_name().isEmpty()) {
//            throw new IllegalArgumentException("Last name can't be empty!");
//        }
//        if (clientMasterEntity.getEmail() == null || clientMasterEntity.getEmail().isEmpty()) {
//            throw new IllegalArgumentException("Email can't be empty!");
//        }
//        if (clientMasterEntity.getPassword() == null || clientMasterEntity.getPassword().isEmpty()) {
//            throw new IllegalArgumentException("Password can't be empty!");
//        }
//        if (clientMasterEntity.getRole().toString() == null || clientMasterEntity.getRole().toString().isEmpty()) {
//            throw new IllegalArgumentException("Role can't be empty!");
//        }
//    }
}
