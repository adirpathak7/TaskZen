/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.it.taskzen.services;

import com.it.taskzen.entities.PostEntity;
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

    // Insert or Save Post
    public PostEntity savePost(PostEntity postEntity) {
        return postRepository.save(postEntity);
    }

    // Update Post
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

    // Delete Post
    public void deletePost(Long postId) {
        if (postRepository.existsById(postId)) {
            postRepository.deleteById(postId);
        } else {
            throw new RuntimeException("Post with ID " + postId + " not found.");
        }
    }

    // Retrieve All Posts
    public List<PostEntity> getAllPosts() {
        return postRepository.findAll();
    }

    // Find Post by ID
    public PostEntity findPostById(Long postId) {
        return postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post with ID " + postId + " not found."));
    }
}
