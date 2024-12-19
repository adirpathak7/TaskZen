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
@Table(name = "freelancer_applied_project_tbl")
public class PostEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "freelancer_applied_project_seq")
    @SequenceGenerator(name = "freelancer_applied_project_seq", sequenceName = "freelancer_applied_project_sequence", allocationSize = 1)
    private Long post_id;

    private String freelancer_range;
    private String freelancer_description;
    private String duration;

    @Enumerated(EnumType.STRING)
    private Status status;

    @ManyToOne
    @JoinColumn(name = "freelancer_id", referencedColumnName = "freelancer_id")
    private FreelancerMasterEntity freelancer_id;

    @ManyToOne
    @JoinColumn(name = "client_id", referencedColumnName = "client_id")
    private ClientMasterEntity client_id;

    @ManyToOne
    @JoinColumn(name = "client_project_id", referencedColumnName = "client_project_id")
    private ClientProjectEntity client_project_id;
    private LocalDateTime created_at;

    public PostEntity() {
    }

    public PostEntity(Long post_id, String freelancer_range, String freelancer_description, String duration, Status status, FreelancerMasterEntity freelancer_id, ClientMasterEntity client_id, ClientProjectEntity client_project_id, LocalDateTime created_at) {
        this.post_id = post_id;
        this.freelancer_range = freelancer_range;
        this.freelancer_description = freelancer_description;
        this.duration = duration;
        this.status = status;
        this.freelancer_id = freelancer_id;
        this.client_id = client_id;
        this.client_project_id = client_project_id;
        this.created_at = created_at;
    }

    public PostEntity(String freelancer_range, String freelancer_description, String duration, ClientMasterEntity client_id, ClientProjectEntity client_project_id) {
        this.freelancer_range = freelancer_range;
        this.freelancer_description = freelancer_description;
        this.duration = duration;
        this.client_id = client_id;
        this.client_project_id = client_project_id;
    }

    public Long getPost_id() {
        return post_id;
    }

    public void setPost_id(Long post_id) {
        this.post_id = post_id;
    }

    public String getFreelancer_range() {
        return freelancer_range;
    }

    public void setFreelancer_range(String freelancer_range) {
        this.freelancer_range = freelancer_range;
    }

    public String getFreelancer_description() {
        return freelancer_description;
    }

    public void setFreelancer_description(String freelancer_description) {
        this.freelancer_description = freelancer_description;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
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

    public ClientProjectEntity getClient_project_id() {
        return client_project_id;
    }

    public void setClient_project_id(ClientProjectEntity client_project_id) {
        this.client_project_id = client_project_id;
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
        if (status == null) {
            status = Status.pending;
        }
    }

    public enum Status {
        pending("pending"), inProgress("inProgress"), accepted("accepted"), completed("completed"), rejected("rejected");

        private final String value;

        Status(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }
}
