/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.it.taskzen.services;

import com.it.taskzen.entities.ClientMasterEntity;
import com.it.taskzen.entities.ClientProjectEntity;
import com.it.taskzen.entities.FreelancerMasterEntity;
import com.it.taskzen.entities.PostEntity;
import com.it.taskzen.entities.UserEntity;
import com.it.taskzen.exceptions.ResourceNotFoundException;
import com.it.taskzen.jwt.JWTService;
import com.it.taskzen.repositories.FreelancerMstRepository;
import com.it.taskzen.repositories.PostRepository;
import java.nio.file.AccessDeniedException;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Aditya Pathak R
 */
@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private FreelancerMstRepository freelancerMstRepository;

    @Autowired
    private JWTService jWTService;

    @Autowired
    private EmailService emailService;

    public boolean applyProjects(String token, String freelancer_range, String freelancer_description, String duration,
            ClientMasterEntity client_id, ClientProjectEntity client_project_id) {
        // Extract freelancer_id from JWT
        Long freelancer_id = jWTService.extractFreelancerId(token);
//        System.out.println("Extracted freelancer_id: " + freelancer_id);

        // Check if freelancer exists
        FreelancerMasterEntity exist_freelancer_id = freelancerMstRepository.findByFreelancerId(freelancer_id);
//        System.out.println("Freelancer entity: " + exist_freelancer_id);

        if (exist_freelancer_id == null) {
            throw new IllegalArgumentException("Freelancer profile not found for ID: " + freelancer_id);
        }

        // Check if the freelancer has already applied for this project
        boolean alreadyApplied = postRepository.existsByFreelancerAndClientProject(exist_freelancer_id, client_project_id);
        if (alreadyApplied) {
            return false; // Indicate that the freelancer has already applied
        }

        // Save the new application if not already applied
        PostEntity postEntity = new PostEntity(freelancer_range, freelancer_description, duration, client_id, client_project_id);
        postEntity.setFreelancer(exist_freelancer_id);
        postRepository.save(postEntity);

        return true; // Indicate successful application
    }

    public PostEntity updatePost(Long postId, PostEntity updatedPost) {
        Optional<PostEntity> existingPost = postRepository.findById(postId);
        if (existingPost.isPresent()) {
            PostEntity post = existingPost.get();
            post.setFreelancer_range(updatedPost.getFreelancer_range());
//            post.setBudget(updatedPost.getBudget());
            post.setDuration(updatedPost.getDuration());
            post.setStatus(updatedPost.getStatus());
            post.setFreelancer(updatedPost.getFreelancer());
            post.setClient_id(updatedPost.getClient_id());
            post.setClientProject(updatedPost.getClientProject());
            return postRepository.save(post);
        }
        throw new RuntimeException("Post with ID " + postId + " not found.");
    }

    public void deletePost(Long postId) {
        if (postRepository.existsById(postId)) {
            postRepository.deleteById(postId);
        } else {
            throw new RuntimeException("Post with ID " + postId + " not found.");
        }
    }

    public List<PostEntity> getAllPosts() {
        return postRepository.findAll();
    }

    public PostEntity findPostById(Long postId) {
        return postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post with ID " + postId + " not found."));
    }

    public List<PostEntity> getFreelancerAppliedPostByToken(String token) {
        Long id = jWTService.extractUserId(token);
        FreelancerMasterEntity freelancer = freelancerMstRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Freelancer not found with id: " + id));
        return postRepository.findByFreelancer(freelancer.getFreelancer_id());
    }

    public List<PostEntity> getClientsFreelancersByProjectsByToken(String token) {
        Long client_id = jWTService.extractClientId(token);
//        System.out.println("the client_id is coming in getPostsAndFreelancersByClientId: " + client_id);
        return postRepository.findProjectsAndFreelancersByClientId(client_id);
    }

    public void approveFreelancerProjectStatus(ClientProjectEntity clientProject, String token, FreelancerMasterEntity freelancer_id) throws AccessDeniedException {
        Long clientId = jWTService.extractClientId(token);

//        System.out.println("the clientId in approveFreelancerProjectStatus: " + clientId);
        Optional<PostEntity> postEntityOptional = postRepository.findByClientProjectId(clientProject);
//        System.out.println("the postEntityOptional: " + postEntityOptional);
        if (postEntityOptional.isEmpty()) {
            throw new ResourceNotFoundException("No project found for the given clientProjectId.");
        }

        PostEntity postEntity = postEntityOptional.get();
//        System.out.println("Client ID from token: " + clientId);
//        System.out.println("Client ID in project: " + postEntity.getClientProject().getClient_id().getClient_id());

        if (!postEntity.getClientProject().getClient_id().getClient_id().equals(clientId)) {
            throw new AccessDeniedException("You are not authorized to approve this freelancer.");
        }

        UserEntity freelancer = postEntity.getFreelancer().getUser();
        ClientMasterEntity client = postEntity.getClientProject().getClient_id();
        ClientProjectEntity clientProjectDetails = postEntity.getClientProject();

        String freelancerEmail = freelancer.getEmail();
        String freelancerFirstName = freelancer.getFirst_name();
        String freelancerLastName = freelancer.getLast_name();
        String clientName = client.getClient_name();
        String contactNumber = client.getContact();
        String clientProjectName = clientProjectDetails.getClient_project_name();
        String duration = postEntity.getDuration();
        String range = postEntity.getFreelancer_range();

        String subject = "Project Request Accepted – Let's Get Started!";
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
                + "<h1>Dear " + freelancerFirstName + " " + freelancerLastName + ",</h1>"
                + "<p>Your project request has been approved!</p>"
                + "<p>Project Details:</p>"
                + "<ul>"
                + "<li>Client: " + clientName + "</li>"
                + "<li>Contact Number: " + contactNumber + "</li>"
                + "<li>Project Name: " + clientProjectName + "</li>"
                + "<li>Duration: " + duration + "</li>"
                + "<li>Budget Range: " + range + "</li>"
                + "</ul>"
                + "<p>Best of luck with the project!</p>"
                + "</body>"
                + "</html>";

        emailService.sendEmail(freelancerEmail, subject, body);

        int updatedRows = postRepository.approveFreelancerProjectStatus(clientId, clientProject, freelancer_id);

        if (updatedRows == 0) {
            throw new ResourceNotFoundException("Unable to update freelancer project status.");
        }
    }

    public void rejectFreelancerProjectStatus(ClientProjectEntity clientProject, String token) {
        Long client_id = jWTService.extractClientId(token);
        int updatedRows = postRepository.rejectFreelancerProjectStatus(client_id, clientProject);

        if (updatedRows == 0) {
            throw new ResourceNotFoundException("Freelancer project not found with id: " + client_id);
        }
    }
}
