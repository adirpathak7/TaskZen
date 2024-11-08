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
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 *
 * @author Aditya Pathak R
 */
@Entity
@Table(name = "freelancer_project_tbl")
public class FreelancerProjectEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int freelancer_project_id;

    @ManyToOne
    @JoinColumn(name = "freelancer_id", referencedColumnName = "freelancer_id")
    private FreelancerMasterEntity freelancerMasterEntity;

    @ManyToOne
    @JoinColumn(name = "client_id", referencedColumnName = "client_id")
    private ClientMasterEntity clientMasterEntity;

    private String freelancer_project_name;
    private String details;
    private String project_link;
    private String freelancer_project_github_link;
    private String freelancer_project_linkedin_link;
    private LocalDateTime created_at;

    public FreelancerProjectEntity() {
    }

    public FreelancerProjectEntity(int freelancer_project_id, FreelancerMasterEntity freelancerMasterEntity, ClientMasterEntity clientMasterEntity, String freelancer_project_name, String details, String project_link, String freelancer_project_github_link, String freelancer_project_linkedin_link, LocalDateTime created_at) {
        this.freelancer_project_id = freelancer_project_id;
        this.freelancerMasterEntity = freelancerMasterEntity;
        this.clientMasterEntity = clientMasterEntity;
        this.freelancer_project_name = freelancer_project_name;
        this.details = details;
        this.project_link = project_link;
        this.freelancer_project_github_link = freelancer_project_github_link;
        this.freelancer_project_linkedin_link = freelancer_project_linkedin_link;
        this.created_at = created_at;
    }

    public int getFreelancer_project_id() {
        return freelancer_project_id;
    }

    public void setFreelancer_project_id(int freelancer_project_id) {
        this.freelancer_project_id = freelancer_project_id;
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
}
