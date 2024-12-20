/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.it.taskzen.controllers;

import com.it.taskzen.entities.ClientMasterEntity;
import com.it.taskzen.entities.ClientProjectEntity;
import com.it.taskzen.entities.FreelancerMasterEntity;
import com.it.taskzen.entities.PostEntity;
import com.it.taskzen.exceptions.ResourceNotFoundException;
import com.it.taskzen.services.PostService;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Aditya Pathak R
 */
@RestController
@CrossOrigin(origins = "http://localhost:8080", allowCredentials = "true")
@RequestMapping(value = "/api/freelancer")
public class PostController {

    @Autowired
    private PostService postService;

    @PostMapping(value = "/applyProjects", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Map<String, Object>> applyProjects(
            @RequestParam("freelancer_range") String freelancer_range,
            @RequestParam("freelancer_description") String freelancer_description,
            @RequestParam("duration") String duration,
            @RequestParam("client_id") ClientMasterEntity client_id,
            @RequestParam("client_project_id") ClientProjectEntity client_project_id,
            @RequestHeader("Authorization") String token) {

        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7).trim();
        }

        // Call service to handle the logic
        boolean isApplied = postService.applyProjects(token, freelancer_range, freelancer_description, duration, client_id, client_project_id);

        // Prepare response
        Map<String, Object> response = new HashMap<>();
        if (isApplied) {
            response.put("message", "Freelancer applied for project.");
            response.put("data", "1");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            response.put("message", "not");
            response.put("data", "0");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping(value = "/updateProject/{postId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Map<String, Object>> updatePost(
            @PathVariable Long postId,
            @RequestParam("freelancer_range") String freelancer_range,
            @RequestParam("freelancer_description") String freelancer_description,
            @RequestParam("duration") String duration) {

        PostEntity updatedPost = new PostEntity();
        updatedPost.setFreelancer_range(freelancer_range);
        updatedPost.setFreelancer_description(freelancer_description);
        updatedPost.setDuration(duration);

        PostEntity post = postService.updatePost(postId, updatedPost);

        Map<String, Object> response = new HashMap<>();
        response.put("message", "Post updated successfully.");
        response.put("data", post);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<Map<String, String>> deletePost(@PathVariable Long postId) {
        postService.deletePost(postId);

        Map<String, String> response = new HashMap<>();
        response.put("message", "Post deleted successfully.");
        response.put("postId", String.valueOf(postId));

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping(value = "/getAllPosts")
    public ResponseEntity<Map<String, Object>> getAllPosts() {
        List<PostEntity> posts = postService.getAllPosts();
        Map<String, Object> response = new HashMap<>();

        if (posts == null || posts.isEmpty()) {
            throw new ResourceNotFoundException("No freelancers found");
        }

        response.put("message", "Posts retrieved successfully.");
        response.put("data", posts);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping(value = "/getPost/{postId}")
    public ResponseEntity<Map<String, Object>> findPostById(@PathVariable Long postId) {
        PostEntity post = postService.findPostById(postId);

        Map<String, Object> response = new HashMap<>();
        response.put("message", "Post retrieved successfully.");
        response.put("data", post);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/getFreelancerAppliedPostByToken")
    public ResponseEntity<Map<String, Object>> getFreelancerAppliedPostByToken(@RequestHeader("Authorization") String token) throws IOException {
        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7).trim();
        }

        List<PostEntity> postDetails = postService.getFreelancerAppliedPostByToken(token);

        Map<String, Object> response = new HashMap<>();
        if (!postDetails.isEmpty()) {
            response.put("message", "Freelancer applied posts fetched successfully.");
            response.put("data", postDetails);
        } else {
            response.put("message", "No applied posts found.");
            response.put("data", null);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/getClientsFreelancersByProjectsByToken")
    public ResponseEntity<Map<String, Object>> getClientsFreelancersByProjectsByToken(@RequestHeader("Authorization") String token) throws IOException {
        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7).trim();
        }

        List<PostEntity> details = postService.getClientsFreelancersByProjectsByToken(token);

        Map<String, Object> response = new HashMap<>();
        if (!details.isEmpty()) {
            response.put("message", "Freelancer and Projects details fetched successfully.");
            response.put("data", details);
        } else {
            response.put("message", "No applied Freelancer found for this Client projects.");
            response.put("data", null);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
