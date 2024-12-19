/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.it.taskzen.services;

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

    public PostEntity applyProjects(String token, PostEntity postEntity) {
        Long freelancer_id = jWTService.extractFreelancerId(token);
        System.out.println("User ID from JWT: " + freelancer_id);

        FreelancerMasterEntity exist_freelancer_id = freelancerMstRepository.findByUserId(freelancer_id);

        if (exist_freelancer_id == null) {
            throw new IllegalArgumentException("Please create your profile first!");
        }
        postEntity.setFreelancer_id(exist_freelancer_id);

        return postRepository.save(postEntity);
    }


    public PostEntity updatePost(Long postId, PostEntity updatedPost) {
        Optional<PostEntity> existingPost = postRepository.findById(postId);
        if (existingPost.isPresent()) {
            PostEntity post = existingPost.get();
            post.setFreelancer_range(updatedPost.getFreelancer_range());
//            post.setBudget(updatedPost.getBudget());
            post.setDuration(updatedPost.getDuration());
            post.setStatus(updatedPost.getStatus());
            post.setFreelancer_id(updatedPost.getFreelancer_id());
            post.setClient_id(updatedPost.getClient_id());
            post.setClient_project_id(updatedPost.getClient_project_id());
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
}
