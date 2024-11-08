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
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author Aditya Pathak R
 */
@Entity
@Table(name = "payment_tbl")
public class PaymentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int payment_id;

    @ManyToOne
    @JoinColumn(name = "freelancer_id", referencedColumnName = "freelancer_id")
    private FreelancerMasterEntity freelancerMasterEntity;

    @ManyToOne
    @JoinColumn(name = "client_id", referencedColumnName = "client_id")
    private ClientMasterEntity clientMasterEntity;

    @ManyToOne
    @JoinColumn(name = "freelancer_project_id", referencedColumnName = "freelancer_project_id")
    private FreelancerProjectEntity freelancerProjectEntity;

    @ManyToOne
    @JoinColumn(name = "client_project_id", referencedColumnName = "client_project_id")
    private ClientProjectEntity clientProjectEntity;

    private String total_amount;
    private String half_pay_amount;
    private String payment_method;

    @Enumerated(EnumType.STRING)
    private Status status;
    private LocalDateTime created_at;

    public PaymentEntity() {
    }

    public PaymentEntity(int payment_id, FreelancerMasterEntity freelancerMasterEntity, ClientMasterEntity clientMasterEntity, FreelancerProjectEntity freelancerProjectEntity, ClientProjectEntity clientProjectEntity, String total_amount, String half_pay_amount, String payment_method, Status status, LocalDateTime created_at) {
        this.payment_id = payment_id;
        this.freelancerMasterEntity = freelancerMasterEntity;
        this.clientMasterEntity = clientMasterEntity;
        this.freelancerProjectEntity = freelancerProjectEntity;
        this.clientProjectEntity = clientProjectEntity;
        this.total_amount = total_amount;
        this.half_pay_amount = half_pay_amount;
        this.payment_method = payment_method;
        this.status = status;
        this.created_at = created_at;
    }

    public int getPayment_id() {
        return payment_id;
    }

    public void setPayment_id(int payment_id) {
        this.payment_id = payment_id;
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

    public FreelancerProjectEntity getFreelancerProjectEntity() {
        return freelancerProjectEntity;
    }

    public void setFreelancerProjectEntity(FreelancerProjectEntity freelancerProjectEntity) {
        this.freelancerProjectEntity = freelancerProjectEntity;
    }

    public ClientProjectEntity getClientProjectEntity() {
        return clientProjectEntity;
    }

    public void setClientProjectEntity(ClientProjectEntity clientProjectEntity) {
        this.clientProjectEntity = clientProjectEntity;
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
