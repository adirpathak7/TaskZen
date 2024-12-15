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
@Table(name = "freelancer_project_tbl")
public class FreelancerProjectEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "freelancer_project_seq")
    @SequenceGenerator(name = "freelancer_project_seq", sequenceName = "freelancer_project_sequence", allocationSize = 1)
    private Long freelancer_project_id;
    
    @ManyToOne
    @JoinColumn(name = "freelancer_id", referencedColumnName = "freelancer_id")
    private FreelancerMasterEntity freelancer_id;

    @ManyToOne
    @JoinColumn(name = "client_id", referencedColumnName = "client_id")
    private ClientMasterEntity client_id;

    private String freelancer_project_name;
    private String details;
    private String project_link;
    private String freelancer_project_github_link;
    private String freelancer_project_linkedin_link;
    private LocalDateTime created_at;

    public FreelancerProjectEntity() {
    }

    public FreelancerProjectEntity(Long freelancer_project_id, FreelancerMasterEntity freelancer_id, ClientMasterEntity client_id, String freelancer_project_name, String details, String project_link, String freelancer_project_github_link, String freelancer_project_linkedin_link, LocalDateTime created_at) {
        this.freelancer_project_id = freelancer_project_id;
        this.freelancer_id = freelancer_id;
        this.client_id = client_id;
        this.freelancer_project_name = freelancer_project_name;
        this.details = details;
        this.project_link = project_link;
        this.freelancer_project_github_link = freelancer_project_github_link;
        this.freelancer_project_linkedin_link = freelancer_project_linkedin_link;
        this.created_at = created_at;
    }

    public Long getFreelancer_project_id() {
        return freelancer_project_id;
    }

    public void setFreelancer_project_id(Long freelancer_project_id) {
        this.freelancer_project_id = freelancer_project_id;
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

    public String getFreelancer_project_name() {
        return freelancer_project_name;
    }

    public void setFreelancer_project_name(String freelancer_project_name) {
        this.freelancer_project_name = freelancer_project_name;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getProject_link() {
        return project_link;
    }

    public void setProject_link(String project_link) {
        this.project_link = project_link;
    }

    public String getFreelancer_project_github_link() {
        return freelancer_project_github_link;
    }

    public void setFreelancer_project_github_link(String freelancer_project_github_link) {
        this.freelancer_project_github_link = freelancer_project_github_link;
    }

    public String getFreelancer_project_linkedin_link() {
        return freelancer_project_linkedin_link;
    }

    public void setFreelancer_project_linkedin_link(String freelancer_project_linkedin_link) {
        this.freelancer_project_linkedin_link = freelancer_project_linkedin_link;
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
