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

    @GetMapping(value = "/getUsers")
    public ResponseEntity<Map<String, String>> getUsers(UserEntity userEntity) {
        List usersList = userService.getUsers(userEntity);
        try {
            Map<String, String> usersResponse = new HashMap<>();
            if (usersList == null || usersList.isEmpty()) {
                usersResponse.put("message", "User not found!");
                usersResponse.put("data", "0");
                return new ResponseEntity(usersResponse, HttpStatus.NOT_FOUND);
            } else {
                usersResponse.put("message", "Exist User's are.");
                usersResponse.put("data", "1");
                return new ResponseEntity(usersList, HttpStatus.OK);
            }
        } catch (Exception ex) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("message", "Something went wrong! " + ex.getMessage());
            errorResponse.put("data", "0");
            return new ResponseEntity(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(value = "/signUpUser", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Map<String, String>> signUpUser(
            @RequestParam("first_name") String first_name,
            @RequestParam("last_name") String last_name,
            @RequestParam("email") String email,
            @RequestParam("password") String password,
            @RequestParam("role") Role role) {

        try {
            UserEntity userEntity = new UserEntity(first_name, last_name, email, password, role);
            userService.signUpUser(userEntity);

            Map<String, String> signUpResponse = new HashMap<>();
            signUpResponse.put("message", "User registered successfully.");
            signUpResponse.put("data", "1");
            return new ResponseEntity<>(signUpResponse, HttpStatus.OK);
        } catch (ResourceNotFoundException ex) {
            Map<String, String> resNotFoundResponse = new HashMap<>();
            resNotFoundResponse.put("error", "User not found! " + ex.getMessage());
            resNotFoundResponse.put("data", "0");
            return new ResponseEntity<>(resNotFoundResponse, HttpStatus.NOT_FOUND);
        } catch (IllegalArgumentException ex) {
            Map<String, String> illArgExceResponse = new HashMap<>();
            illArgExceResponse.put("error", ex.getMessage());
            illArgExceResponse.put("data", "0");
            return new ResponseEntity<>(illArgExceResponse, HttpStatus.BAD_REQUEST);
        } catch (InternalError ex) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "Invalid token or token maybe expire! " + ex.getMessage());
            errorResponse.put("data", "0");
            return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception ex) {
            System.out.println("Something went wrong! " + ex.getMessage());
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "Somthing wen wrong!");
            errorResponse.put("data", "0");
            return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(value = "/loginUser", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Map<String, String>> loginUser(
            @RequestParam("email") String email,
            @RequestParam("password") String password) {
        try {
            UserEntity userEntity = new UserEntity(email, password);
            Map<String, String> signInResponse = userService.loginUser(userEntity);
            return new ResponseEntity<>(signInResponse, HttpStatus.OK);
        } catch (IllegalArgumentException ex) {
            Map<String, String> illArgExceResponse = new HashMap<>();
            illArgExceResponse.put("error", ex.getMessage());
            illArgExceResponse.put("data", "0");
            return new ResponseEntity<>(illArgExceResponse, HttpStatus.BAD_REQUEST);
        } catch (ResourceNotFoundException ex) {
            Map<String, String> resNotFoundResponse = new HashMap<>();
            resNotFoundResponse.put("error", "Email does not exist! " + ex.getMessage());
            resNotFoundResponse.put("data", "0");
            return new ResponseEntity<>(resNotFoundResponse, HttpStatus.NOT_FOUND);
        } catch (Exception ex) {
            System.out.println("Something went wrong! " + ex.getMessage());
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "Something went wrong!");
            errorResponse.put("data", "0");
            return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
