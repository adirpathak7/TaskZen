/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.it.taskzen.entities;

import java.time.LocalDateTime;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author Aditya Pathak R
 */
@Entity
@Table(name = "freelancer_review_tbl")
public class FreelancerReviewEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int freelancer_review_id;

    @ManyToOne
    @JoinColumn(name = "freelancer_id", referencedColumnName = "freelancer_id")
    private FreelancerMasterEntity freelancerMasterEntity;

    @ManyToOne
    @JoinColumn(name = "client_id", referencedColumnName = "client_id")
    private ClientMasterEntity clientMasterEntity;

    private String comment;
    private int rating;
    private LocalDateTime created_at;

    public FreelancerReviewEntity() {
    }

    public FreelancerReviewEntity(int freelancer_review_id, FreelancerMasterEntity freelancerMasterEntity, ClientMasterEntity clientMasterEntity, String comment, int rating, LocalDateTime created_at) {
        this.freelancer_review_id = freelancer_review_id;
        this.freelancerMasterEntity = freelancerMasterEntity;
        this.clientMasterEntity = clientMasterEntity;
        this.comment = comment;
        this.rating = rating;
        this.created_at = created_at;
    }

    public int getFreelancer_review_id() {
        return freelancer_review_id;
    }

    public void setFreelancer_review_id(int freelancer_review_id) {
        this.freelancer_review_id = freelancer_review_id;
    }

    public FreelancerMasterEntity getFreelancerMasterEntity() {
        return freelancerMasterEntity;
    }

    public void setFreelancerMasterEntity(FreelancerMasterEntity freelancerMasterEntity) {
        this.freelancerMasterEntity = freelancerMasterEntity;
    }

    public ClientMasterEntity getClientMasterEntity() {
        return clientMasterEntity;
    }

    public void setClientMasterEntity(ClientMasterEntity clientMasterEntity) {
        this.clientMasterEntity = clientMasterEntity;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public LocalDateTime getCreated_at() {
        return created_at;
    }

    public void setCreated_at(LocalDateTime created_at) {
        this.created_at = created_at;
    }
}
