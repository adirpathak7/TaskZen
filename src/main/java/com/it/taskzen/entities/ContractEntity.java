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
import javax.persistence.Table;

/**
 *
 * @author Aditya Pathak R
 */
@Entity
@Table(name = "contract_tbl")
public class ContractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int contract_id;

    @ManyToOne
    @JoinColumn(name = "freelancer_id", referencedColumnName = "freelancer_id")
    private FreelancerMasterEntity freelancerMasterEntity;

    @ManyToOne
    @JoinColumn(name = "client_id", referencedColumnName = "client_id")
    private ClientMasterEntity clientMasterEntity;

    private String start_date;
    private String end_date;
    private String amount_agreed;

    @Enumerated(EnumType.STRING)
    private Status status;
    private LocalDateTime created_at;

    public ContractEntity() {
    }

    public ContractEntity(int contract_id, FreelancerMasterEntity freelancerMasterEntity, ClientMasterEntity clientMasterEntity, String start_date, String end_date, String amount_agreed, Status status, LocalDateTime created_at) {
        this.contract_id = contract_id;
        this.freelancerMasterEntity = freelancerMasterEntity;
        this.clientMasterEntity = clientMasterEntity;
        this.start_date = start_date;
        this.end_date = end_date;
        this.amount_agreed = amount_agreed;
        this.status = status;
        this.created_at = created_at;
    }

    public int getContract_id() {
        return contract_id;
    }

    public void setContract_id(int contract_id) {
        this.contract_id = contract_id;
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

    public String getStart_date() {
        return start_date;
    }

    public void setStart_date(String start_date) {
        this.start_date = start_date;
    }

    public String getEnd_date() {
        return end_date;
    }

    public void setEnd_date(String end_date) {
        this.end_date = end_date;
    }

    public String getAmount_agreed() {
        return amount_agreed;
    }

    public void setAmount_agreed(String amount_agreed) {
        this.amount_agreed = amount_agreed;
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
        activated("activated"), completed("completed"), rejected("rejected");

        private final String value;

        Status(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }
}
