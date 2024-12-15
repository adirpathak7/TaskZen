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
@Table(name = "invoice_tbl")
public class InvoiceEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "invoice_seq")
    @SequenceGenerator(name = "invoice_seq", sequenceName = "invoice_sequence", allocationSize = 1)
    private Long invoice_id;
    
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

    @ManyToOne
    @JoinColumn(name = "payment_id", referencedColumnName = "payment_id")
    private PaymentEntity payment_id;

    @ManyToOne
    @JoinColumn(name = "start_date", referencedColumnName = "start_date")
    @JoinColumn(name = "end_date", referencedColumnName = "end_date")
    private ContractEntity contractEntity;

    private LocalDateTime created_at;

    public InvoiceEntity() {
    }

    public InvoiceEntity(Long invoice_id, FreelancerMasterEntity freelancer_id, ClientMasterEntity client_id, FreelancerProjectEntity freelancer_project_id, ClientProjectEntity client_project_id, PaymentEntity payment_id, ContractEntity contractEntity, LocalDateTime created_at) {
        this.invoice_id = invoice_id;
        this.freelancer_id = freelancer_id;
        this.client_id = client_id;
        this.freelancer_project_id = freelancer_project_id;
        this.client_project_id = client_project_id;
        this.payment_id = payment_id;
        this.contractEntity = contractEntity;
        this.created_at = created_at;
    }

    public Long getInvoice_id() {
        return invoice_id;
    }

    public void setInvoice_id(Long invoice_id) {
        this.invoice_id = invoice_id;
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

    public PaymentEntity getPayment_id() {
        return payment_id;
    }

    public void setPayment_id(PaymentEntity payment_id) {
        this.payment_id = payment_id;
    }


    public ContractEntity getContractEntity() {
        return contractEntity;
    }

    public void setContractEntity(ContractEntity contractEntity) {
        this.contractEntity = contractEntity;
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
