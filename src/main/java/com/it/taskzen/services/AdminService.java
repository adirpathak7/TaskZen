/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.it.taskzen.services;

import com.it.taskzen.entities.AdminEntity;
import com.it.taskzen.jwt.JWTService;
import com.it.taskzen.repositories.AdminRepository;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

/**
 *
 * @author Aditya Pathak R
 */
@Service
public class AdminService {

    @Autowired
    private JWTService jwtService;

    @Autowired
    private AdminRepository adminRepository;

    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(7);

    public List getAdmin(AdminEntity adminEntity) {
        return adminRepository.findAll();
    }

    public AdminEntity addAdmin(AdminEntity adminEntity) {

        if (adminEntity.getEmail() == null || adminEntity.getEmail().isEmpty()) {
            throw new IllegalArgumentException("Email can't be empty!");
        }
        if (adminEntity.getPassword() == null || adminEntity.getPassword().isEmpty()) {
            throw new IllegalArgumentException("Password can't be empty!");
        }

        adminEntity.setPassword(encoder.encode(adminEntity.getPassword()));
        return adminRepository.save(adminEntity);

    }

    public Map<String, String> loginAdmin(AdminEntity adminEntity) {
        if (adminEntity.getEmail() == null || adminEntity.getEmail().isEmpty()) {
            throw new IllegalArgumentException("Email can't be empty!");
        }
        if (adminEntity.getPassword() == null || adminEntity.getPassword().isEmpty()) {
            throw new IllegalArgumentException("Password can't be empty!");
        }

        AdminEntity existAdmin = adminRepository.findByEmail(adminEntity.getEmail());

        if (existAdmin == null) {
            throw new IllegalArgumentException("Email does not exist!");
        }

        boolean checkPassword = encoder.matches(adminEntity.getPassword(), existAdmin.getPassword());
        if (!checkPassword) {
            throw new IllegalArgumentException("Invalid password!");
        }

        String role = existAdmin.getRole().toString();
        Long admin_id = existAdmin.getAdmin_id();
//        Long user_id = null;
//        Long client_id = null;
//        Long freelancer_id = null;

//        String token = jwtService.generateToken(adminEntity.getEmail(), role, admin_id);

        Map<String, String> loginResponse = new HashMap<>();
        loginResponse.put("role", role);
//        loginResponse.put("token", token);
        loginResponse.put("data", "1");
        return loginResponse;
    }
}
