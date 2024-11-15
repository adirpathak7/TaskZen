package com.it.taskzen.controllers;

import com.it.taskzen.exceptions.ResourceNotFoundException;
import com.it.taskzen.entities.UserEntity;
import com.it.taskzen.entities.UserEntity.Role;
import com.it.taskzen.services.EmailService;
import com.it.taskzen.services.UserService;
import java.sql.SQLException;
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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * A sample greetings controller to return greeting text
 */
@RestController
@CrossOrigin(origins = "http://localhost:8080", allowCredentials = "true")
@RequestMapping(value = "/api")
public class UserController {

    /**
     *
     * @param name the name to greet
     * @return greeting text
     */
    @Autowired
    private UserService userService;

    @GetMapping(value = "/Java")
    public String greetJava() {
        return "Hello Java!";
    }

    @GetMapping(value = "/Spring")
    public String greetSpring() {
        return "Hello Spring!";
    }

    @GetMapping(value = "/RodJohnson")
    public String greetRodJohnson() {
        return "Hello RodJohnson!";
    }

    @RequestMapping(value = "/hello", method = RequestMethod.GET)
    public String hello() {
        return "Hello World!";
    }

    @GetMapping(value = "/getAllUsers")
    public ResponseEntity<Map<String, String>> getUsers(UserEntity userEntity) {
        List<UserEntity> usersList = userService.getUsers(userEntity);
        Map<String, String> response = new HashMap<>();
        if (usersList == null || usersList.isEmpty()) {
            throw new ResourceNotFoundException("No users found");
        }
        response.put("message", "Users retrieved successfully");
        response.put("data", "1");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping(value = "/signUpUser", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Map<String, String>> signUpUser(
            @RequestParam("first_name") String firstName,
            @RequestParam("last_name") String lastName,
            @RequestParam("email") String email,
            @RequestParam("password") String password,
            @RequestParam("role") Role role) {

        UserEntity userEntity = new UserEntity(firstName, lastName, email, password, role);
        userService.signUpUser(userEntity);

        Map<String, String> signUpResponse = new HashMap<>();
        signUpResponse.put("message", "User registered successfully.");
        signUpResponse.put("data", "1");

        return new ResponseEntity<>(signUpResponse, HttpStatus.OK);
    }

    @PostMapping(value = "/loginUser", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Map<String, String>> loginUser(
            @RequestParam("email") String email,
            @RequestParam("password") String password) {

        UserEntity userEntity = new UserEntity(email, password);
        Map<String, String> loginResponse = userService.loginUser(userEntity);
        return new ResponseEntity<>(loginResponse, HttpStatus.OK);
    }

}
