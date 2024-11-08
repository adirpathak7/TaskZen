/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.it.taskzen.services;

import com.it.taskzen.entities.UserEntity;
import com.it.taskzen.jwt.JWTService;
import com.it.taskzen.repositories.UserRepository;
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
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EmailService emailService;

    @Autowired
    private JWTService jwtService;

    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(7);

    public List getUsers(UserEntity userEntity) {
        return userRepository.findAll();
    }

    public UserEntity signUpUser(UserEntity userEntity) {
        if (userEntity.getFirst_name() == null || userEntity.getFirst_name().isEmpty()) {
            throw new IllegalArgumentException("First name can't be empty!");
        }
        if (userEntity.getLast_name() == null || userEntity.getLast_name().isEmpty()) {
            throw new IllegalArgumentException("Last name can't be empty!");
        }
        if (userEntity.getEmail() == null || userEntity.getEmail().isEmpty()) {
            throw new IllegalArgumentException("Email can't be empty!");
        }
        if (userEntity.getPassword() == null || userEntity.getPassword().isEmpty()) {
            throw new IllegalArgumentException("Password can't be empty!");
        }
        if (userEntity.getRole().toString() == null || userEntity.getRole().toString().isEmpty()) {
            throw new IllegalArgumentException("Role can't be empty!");
        }

        String subject = "Welcome to TaskZen 😊.";
        String body = "<html>"
                + "<head>"
                + "<style>"
                + "body { font-family: Arial, sans-serif; line-height: 1.6; }"
                + "h1 { color: #333; }"
                + "p { font-size: 16px; color: #555; }"
                + "b { color: #007BFF; }"
                + "</style>"
                + "</head>"
                + "<body>"
                + "<h1>Dear " + userEntity.getFirst_name() + " " + userEntity.getLast_name() + ",</h1>"
                + "<p>Thank you for registering with us.</p>"
                + "<p>We're excited to have you onboard!</p>"
                + "<p><b>Welcome to TaskZen!</b></p>"
                + "</body>"
                + "</html>";

        userEntity.setPassword(encoder.encode(userEntity.getPassword()));
        String email = userEntity.getEmail();
        emailService.sendEmail(email, subject, body);
        return userRepository.save(userEntity);

    }

    public Map<String, String> loginUser(UserEntity userEntity) {
        if (userEntity.getEmail() == null || userEntity.getEmail().isEmpty()) {
            throw new IllegalArgumentException("Email can't be empty!");
        }
        if (userEntity.getPassword() == null || userEntity.getPassword().isEmpty()) {
            throw new IllegalArgumentException("Password can't be empty!");
        }

        UserEntity existUser = userRepository.findByEmail(userEntity.getEmail());

        if (existUser == null) {
            throw new IllegalArgumentException("Email does not exist!");
        }

        boolean checkPassword = encoder.matches(userEntity.getPassword(), existUser.getPassword());
        if (!checkPassword) {
            throw new IllegalArgumentException("Invalid password!");
        }

        String role = existUser.getRole().toString();
        String token = jwtService.generateToken(userEntity.getEmail());

        Map<String, String> loginResponse = new HashMap<>();
        loginResponse.put("role", role);
        loginResponse.put("token", token);
        loginResponse.put("data", "1");
        return loginResponse;
    }

    public UserEntity findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

}
