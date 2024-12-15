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
@Table(name = "payment_tbl")
public class PaymentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "payment_seq")
    @SequenceGenerator(name = "payment_seq", sequenceName = "payment_sequence", allocationSize = 1)
    private Long payment_id;
    
    @ManyToOne
    @JoinColumn(name = "freelancer_id", referencedColumnName = "freelancer_id")
    private FreelancerMasterEntity freelancer_id;

    @ManyToOne
    @JoinColumn(name = "client_id", referencedColumnName = "client_id")
    private ClientMasterEntity client_id;

    @ManyToOne
    @JoinColumn(name = "freelancer_project_id", referencedColumnName = "freelancer_project_id")
    private FreelancerProjectEntity freelancer_project_id;

    @ManyToOne
    @JoinColumn(name = "client_project_id", referencedColumnName = "client_project_id")
    private ClientProjectEntity client_project_id;

    private String total_amount;
    private String half_pay_amount;
    private String payment_method;

    @Enumerated(EnumType.STRING)
    private Status status;
    private LocalDateTime created_at;

    public PaymentEntity() {
    }

    public PaymentEntity(Long payment_id, FreelancerMasterEntity freelancer_id, ClientMasterEntity client_id, FreelancerProjectEntity freelancer_project_id, ClientProjectEntity client_project_id, String total_amount, String half_pay_amount, String payment_method, Status status, LocalDateTime created_at) {
        this.payment_id = payment_id;
        this.freelancer_id = freelancer_id;
        this.client_id = client_id;
        this.freelancer_project_id = freelancer_project_id;
        this.client_project_id = client_project_id;
        this.total_amount = total_amount;
        this.half_pay_amount = half_pay_amount;
        this.payment_method = payment_method;
        this.status = status;
        this.created_at = created_at;
    }

    public Long getPayment_id() {
        return payment_id;
    }

    public void setPayment_id(Long payment_id) {
        this.payment_id = payment_id;
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

    public FreelancerProjectEntity getFreelancer_project_id() {
        return freelancer_project_id;
    }

    public void setFreelancer_project_id(FreelancerProjectEntity freelancer_project_id) {
        this.freelancer_project_id = freelancer_project_id;
    }

    public ClientProjectEntity getClient_project_id() {
        return client_project_id;
    }

    public void setClient_project_id(ClientProjectEntity client_project_id) {
        this.client_project_id = client_project_id;
    }

    public String getTotal_amount() {
        return total_amount;
    }

    public void setTotal_amount(String total_amount) {
        this.total_amount = total_amount;
    }

    public String getHalf_pay_amount() {
        return half_pay_amount;
    }

    public void setHalf_pay_amount(String half_pay_amount) {
        this.half_pay_amount = half_pay_amount;
    }

    public String getPayment_method() {
        return payment_method;
    }

    public void setPayment_method(String payment_method) {
        this.payment_method = payment_method;
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
        pending("pending"), completed("completed"), rejected("rejected");

        private final String value;

        Status(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }
}
