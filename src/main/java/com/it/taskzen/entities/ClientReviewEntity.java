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
import javax.persistence.PrePersist;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 *
 * @author Aditya Pathak R
 */
@Entity
@Table(name = "client_review_id")
public class ClientReviewEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "client_review_seq")
    @SequenceGenerator(name = "client_review_seq", sequenceName = "client_review_sequence", allocationSize = 1)
    private Long client_review_id;

    @ManyToOne
    @JoinColumn(name = "client_id", referencedColumnName = "client_id")
    private ClientMasterEntity client_id;

    @ManyToOne
    @JoinColumn(name = "freelancer_id", referencedColumnName = "freelancer_id")
    private FreelancerMasterEntity freelancer_id;

    private String comment;
    private int rating;
    private LocalDateTime created_at;

    public ClientReviewEntity() {
    }

    public ClientReviewEntity(Long client_review_id, ClientMasterEntity client_id, FreelancerMasterEntity freelancer_id, String comment, int rating, LocalDateTime created_at) {
        this.client_review_id = client_review_id;
        this.client_id = client_id;
        this.freelancer_id = freelancer_id;
        this.comment = comment;
        this.rating = rating;
        this.created_at = created_at;
    }

    public Long getClient_review_id() {
        return client_review_id;
    }

    public void setClient_review_id(Long client_review_id) {
        this.client_review_id = client_review_id;
    }

    public ClientMasterEntity getClient_id() {
        return client_id;
    }

    public void setClient_id(ClientMasterEntity client_id) {
        this.client_id = client_id;
    }

    public FreelancerMasterEntity getFreelancer_id() {
        return freelancer_id;
    }

    public void setFreelancer_id(FreelancerMasterEntity freelancer_id) {
        this.freelancer_id = freelancer_id;
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

    @PrePersist
    protected void onCreate() {
        created_at = LocalDateTime.now();
    }
}
