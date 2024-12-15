/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.it.taskzen.entities;

import java.time.LocalDateTime;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
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
@Table(name = "bid_tbl")
public class BidEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "bid_seq")
    @SequenceGenerator(name = "bid_seq", sequenceName = "bid_sequence", allocationSize = 1)
    private Long bid_id;

    @ManyToOne
    @JoinColumn(name = "freelancer_id", referencedColumnName = "freelancer_id")
    private FreelancerMasterEntity freelancer_id;

    @ManyToOne
    @JoinColumn(name = "client_id", referencedColumnName = "client_id")
    private ClientMasterEntity client_id;

    @ManyToOne
    @JoinColumn(name = "post_id", referencedColumnName = "post_id")
    private PostEntity post_id;

    private String bid_amount;
    private String proposal;

    @Enumerated(EnumType.STRING)
    private Status status;
    private LocalDateTime created_at;

    public BidEntity() {
    }

    public BidEntity(Long bid_id, FreelancerMasterEntity freelancer_id, ClientMasterEntity client_id, PostEntity post_id, String bid_amount, String proposal, Status status, LocalDateTime created_at) {
        this.bid_id = bid_id;
        this.freelancer_id = freelancer_id;
        this.client_id = client_id;
        this.post_id = post_id;
        this.bid_amount = bid_amount;
        this.proposal = proposal;
        this.status = status;
        this.created_at = created_at;
    }

    public FreelancerMasterEntity getFreelancer_id() {
        return freelancer_id;
    }

    public void setFreelancer_id(FreelancerMasterEntity freelancer_id) {
        this.freelancer_id = freelancer_id;
    }

    public ClientMasterEntity getClient_id() {
        return client_id;
    }

    public void setClient_id(ClientMasterEntity client_id) {
        this.client_id = client_id;
    }

    public PostEntity getPost_id() {
        return post_id;
    }

    public void setPost_id(PostEntity post_id) {
        this.post_id = post_id;
    }

    public Long getBid_id() {
        return bid_id;
    }

    public void setBid_id(Long bid_id) {
        this.bid_id = bid_id;
    }

    public String getBid_amount() {
        return bid_amount;
    }

    public void setBid_amount(String bid_amount) {
        this.bid_amount = bid_amount;
    }

    public String getProposal() {
        return proposal;
    }

    public void setProposal(String proposal) {
        this.proposal = proposal;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
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

    public enum Status {
        submitted("submitted"), accepted("accepted"), rejected("rejected");

        private final String value;

        Status(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }
}
