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
            response.put("data", "0");
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
            response.put("data", "0");
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping(value = "/approveFreelancerProjectStatus")
    public ResponseEntity<Map<String, String>> approveFreelancerProjectStatus(
            @RequestHeader("Authorization") String token,
            @RequestParam("client_project_id") ClientProjectEntity clientProject,
            @RequestParam("freelancer_id") FreelancerMasterEntity freelancer_id) {
        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7).trim();
        }

        try {
            postService.approveFreelancerProjectStatus(clientProject, token, freelancer_id);
            Map<String, String> response = new HashMap<>();
            response.put("message", "Freelancer project status approved by client.");
            response.put("data", "1");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(Map.of("error", e.getMessage()), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(Map.of("error", "An error occurred: " + e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping(value = "/rejectFreelancerProjectStatus")
    public ResponseEntity<Map<String, String>> rejectFreelancerProjectStatus(
            @RequestHeader("Authorization") String token,
            @RequestParam("client_project_id") ClientProjectEntity clientProject,
            @RequestParam("freelancer_id") FreelancerMasterEntity freelancer_id) {
        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7).trim();
        }

        try {
            postService.rejectFreelancerProjectStatus(clientProject, token, freelancer_id);
            Map<String, String> response = new HashMap<>();
            response.put("message", "Freelancer project status rejected by client.");
            response.put("data", "1");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(Map.of("error", e.getMessage()), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(Map.of("error", "An error occurred: " + e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping(value = "/completeFreelancerProjectStatus")
    public ResponseEntity<Map<String, String>> completeFreelancerProjectStatus(
            @RequestHeader("Authorization") String token,
            @RequestParam("client_project_id") ClientProjectEntity clientProject) {
        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7).trim();
        }

        try {
            postService.completeFreelancerProjectStatus(clientProject, token);
            Map<String, String> response = new HashMap<>();
            response.put("message", "Freelancer project status completed by freelancer.");
            response.put("data", "1");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(Map.of("error", e.getMessage()), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(Map.of("error", "An error occurred: " + e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/getClientsFreelancersByProjectsByToken/accepted")
    public ResponseEntity<Map<String, Object>> getClientsAcceptedFreelancersByProjectsByToken(@RequestHeader("Authorization") String token) throws IOException {
        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7).trim();
        }

        List<PostEntity> details = postService.getClientsAcceptedFreelancersByProjectsByToken(token);

        Map<String, Object> response = new HashMap<>();
        if (!details.isEmpty()) {
            response.put("message", "Accepted Freelancer and Projects details fetched successfully.");
            response.put("data", details);
        } else {
            response.put("message", "No accepted Freelancer found for this Client projects.");
            response.put("data", "0");
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/getClientsFreelancersByProjectsByToken/completed")
    public ResponseEntity<Map<String, Object>> getClientsCompletedFreelancersByProjectsByToken(@RequestHeader("Authorization") String token) throws IOException {
        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7).trim();
        }

        List<PostEntity> details = postService.getClientsCompletedFreelancersByProjectsByToken(token);

        Map<String, Object> response = new HashMap<>();
        if (!details.isEmpty()) {
            response.put("message", "Completed Freelancer and Projects details fetched successfully.");
            response.put("data", details);
        } else {
            response.put("message", "No completed Freelancer found for this Client projects.");
            response.put("data", "0");
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/getClientsFreelancersByProjectsByToken/done")
    public ResponseEntity<Map<String, Object>> getClientsDoneFreelancersByProjectsByToken(@RequestHeader("Authorization") String token) throws IOException {
        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7).trim();
        }

        List<PostEntity> details = postService.getClientsDoneFreelancersByProjectsByToken(token);

        Map<String, Object> response = new HashMap<>();
        if (!details.isEmpty()) {
            response.put("message", "Done Freelancer and Projects details fetched successfully.");
            response.put("data", details);
        } else {
            response.put("message", "No done Freelancer found for this Client projects.");
            response.put("data", "0");
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/getFreelancerAppliedPostByToken/pending")
    public ResponseEntity<Map<String, Object>> getPendingFreelancerAppliedPostByToken(@RequestHeader("Authorization") String token) throws IOException {
        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7).trim();
        }

        List<PostEntity> postDetails = postService.getPendingFreelancerAppliedPostByToken(token);

        Map<String, Object> response = new HashMap<>();
        if (!postDetails.isEmpty()) {
            response.put("message", "Pending Freelancer applied posts fetched successfully.");
            response.put("data", postDetails);
        } else {
            response.put("message", "No pending posts found.");
            response.put("data", "0");
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/getFreelancerAppliedPostByToken/accepted")
    public ResponseEntity<Map<String, Object>> getAcceptedFreelancerAppliedPostByToken(@RequestHeader("Authorization") String token) throws IOException {
        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7).trim();
        }

        List<PostEntity> postDetails = postService.getAcceptedFreelancerAppliedPostByToken(token);

        Map<String, Object> response = new HashMap<>();
        if (!postDetails.isEmpty()) {
            response.put("message", "Accepted Freelancer applied posts fetched successfully.");
            response.put("data", postDetails);
        } else {
            response.put("message", "No accepted posts found.");
            response.put("data", "0");
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/getFreelancerAppliedPostByToken/completed")
    public ResponseEntity<Map<String, Object>> getCompletedFreelancerAppliedPostByToken(@RequestHeader("Authorization") String token) throws IOException {
        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7).trim();
        }

        List<PostEntity> postDetails = postService.getCompletedFreelancerAppliedPostByToken(token);

        Map<String, Object> response = new HashMap<>();
        if (!postDetails.isEmpty()) {
            response.put("message", "Completed Freelancer applied posts fetched successfully.");
            response.put("data", postDetails);
        } else {
            response.put("message", "No completed posts found.");
            response.put("data", "0");
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/getFreelancerAppliedPostByToken/rejected")
    public ResponseEntity<Map<String, Object>> getRejectedFreelancerAppliedPostByToken(@RequestHeader("Authorization") String token) throws IOException {
        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7).trim();
        }

        List<PostEntity> postDetails = postService.getRejectedFreelancerAppliedPostByToken(token);

        Map<String, Object> response = new HashMap<>();
        if (!postDetails.isEmpty()) {
            response.put("message", "Rejected Freelancer applied posts fetched successfully.");
            response.put("data", postDetails);
        } else {
            response.put("message", "No rejected posts found.");
            response.put("data", "0");
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/getFreelancerProjectDetails/accepted&completed")
    public ResponseEntity<Map<String, Object>> getFreelancerProjectDetailsAcceptedCompleted() throws IOException {
        List<PostEntity> postDetails = postService.getFreelancerProjectDetailsAcceptedCompleted();

        Map<String, Object> response = new HashMap<>();
        if (!postDetails.isEmpty()) {
            response.put("message", "Freelancer applied posts fetched successfully.");
            response.put("data", postDetails);
        } else {
            response.put("message", "No freelancer posts found.");
            response.put("data", "0");
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/appliedProjects/count")
    public ResponseEntity<Long> countAppliedProjectsByClientId(@RequestHeader("Authorization") String token) {
        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7).trim();
        }
        long count = postService.countProjectsByClientId(token);
        return ResponseEntity.ok(count);
    }

    @GetMapping("/appliedProjects/sumRanges")
    public ResponseEntity<Double> sumFreelancerRanges(@RequestHeader("Authorization") String token) {
        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7).trim();
        }
        Double sum = postService.sumFreelancerRangesByClientId(token);
        return ResponseEntity.ok(sum);
    }

    @GetMapping("/count/completed")
    public ResponseEntity<Long> getCountCompletedByFreelancer(@RequestHeader("Authorization") String token) {
         if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7).trim();
        }
        long count = postService.countCompletedByFreelancer(token);
        return ResponseEntity.ok(count);
    }

    @GetMapping("/count/pending")
    public ResponseEntity<Long> getCountByStatusPending() {
        long count = postService.countByStatusPending();
        return ResponseEntity.ok(count);
    }

    @GetMapping("/count/completed-status")
    public ResponseEntity<Long> getCountByStatusCompleted() {
        long count = postService.countByStatusCompleted();
        return ResponseEntity.ok(count);
    }

    @GetMapping("/count/freelancer-range")
    public ResponseEntity<Map<Long, Long>> getCountByFreelancerRange() {
        Map<Long, Long> countByRange = postService.countByFreelancerRange();
        return ResponseEntity.ok(countByRange);
    }
}
