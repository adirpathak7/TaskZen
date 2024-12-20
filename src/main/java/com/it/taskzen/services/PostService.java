/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.it.taskzen.services;

import com.it.taskzen.entities.ClientMasterEntity;
import com.it.taskzen.entities.ClientProjectEntity;
import com.it.taskzen.entities.FreelancerMasterEntity;
import com.it.taskzen.entities.PostEntity;
import com.it.taskzen.jwt.JWTService;
import com.it.taskzen.repositories.FreelancerMstRepository;
import com.it.taskzen.repositories.PostRepository;
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

    public boolean applyProjects(String token, String freelancer_range, String freelancer_description, String duration,
            ClientMasterEntity client_id, ClientProjectEntity client_project_id) {
        // Extract freelancer_id from JWT
        Long freelancer_id = jWTService.extractFreelancerId(token);

        // Check if freelancer exists
        FreelancerMasterEntity exist_freelancer_id = freelancerMstRepository.findByUserId(freelancer_id);
        if (exist_freelancer_id == null) {
            throw new IllegalArgumentException("Please create your profile first!");
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
        System.out.println("the client_id is coming in getPostsAndFreelancersByClientId: " + client_id);
        return postRepository.findProjectsAndFreelancersByClientId(client_id);
    }
}
