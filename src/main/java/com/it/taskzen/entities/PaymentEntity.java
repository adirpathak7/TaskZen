/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.it.taskzen.entities;

import java.time.LocalDateTime;
import javax.persistence.CascadeType;
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
@Table(name = "payment_tbl")
public class PaymentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "payment_seq")
    @SequenceGenerator(name = "payment_seq", sequenceName = "payment_sequence", allocationSize = 1)
    private Long payment_id;

    private Long amount;

    private String status;
    private String razorpayId;
    private LocalDateTime created_at;

    @ManyToOne(cascade = CascadeType.PERSIST)
    private FreelancerMasterEntity freelancer;

    @ManyToOne(cascade = CascadeType.PERSIST)
    private ClientProjectEntity project;

    public PaymentEntity(Long payment_id, Long amount, String status, String razorpayId, LocalDateTime created_at, FreelancerMasterEntity freelancer, ClientProjectEntity project) {
        this.payment_id = payment_id;
        this.amount = amount;
        this.status = status;
        this.razorpayId = razorpayId;
        this.created_at = created_at;
        this.freelancer = freelancer;
        this.project = project;
    }

    public PaymentEntity() {
    }

    public FreelancerMasterEntity getFreelancer() {
        return freelancer;
    }

    public void setFreelancer(FreelancerMasterEntity freelancer) {
        this.freelancer = freelancer;
    }

    public ClientProjectEntity getProject() {
        return project;
    }

    public void setProject(ClientProjectEntity project) {
        this.project = project;
    }

    public Long getPayment_id() {
        return payment_id;
    }

    public void setPayment_id(Long payment_id) {
        this.payment_id = payment_id;
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

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRazorpayId() {
        return razorpayId;
    }

    public void setRazorpayId(String razorpayId) {
        this.razorpayId = razorpayId;
    }

}
