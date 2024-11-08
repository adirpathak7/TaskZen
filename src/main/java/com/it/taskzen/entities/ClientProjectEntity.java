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
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 *
 * @author Aditya Pathak R
 */
@Entity
@Table(name = "client_project_tbl")
public class ClientProjectEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int client_project_id;

    @ManyToOne
    @JoinColumn(name = "client_id", referencedColumnName = "client_id")
    private ClientMasterEntity clientMasterEntity;

    @ManyToOne
    @JoinColumn(name = "freelancer_id", referencedColumnName = "freelancer_id")
    private FreelancerMasterEntity freelancerMasterEntity;

    private String client_project_name;
    private String details;
    private String project_picture;

    @ManyToOne
    @JoinColumn(name = "skill_id", referencedColumnName = "skill_id")
    private SkillsEntity skillsEntity;

    private String duration;
    private LocalDateTime created_at;

    public ClientProjectEntity() {
    }

    public ClientProjectEntity(int client_project_id, ClientMasterEntity clientMasterEntity, FreelancerMasterEntity freelancerMasterEntity, String client_project_name, String details, String project_picture, SkillsEntity skillsEntity, String duration, LocalDateTime created_at) {
        this.client_project_id = client_project_id;
        this.clientMasterEntity = clientMasterEntity;
        this.freelancerMasterEntity = freelancerMasterEntity;
        this.client_project_name = client_project_name;
        this.details = details;
        this.project_picture = project_picture;
        this.skillsEntity = skillsEntity;
        this.duration = duration;
        this.created_at = created_at;
    }

    public int getClient_project_id() {
        return client_project_id;
    }

    public void setClient_project_id(int client_project_id) {
        this.client_project_id = client_project_id;
    }

    public ClientMasterEntity getClientMasterEntity() {
        return clientMasterEntity;
    }

    public void setClientMasterEntity(ClientMasterEntity clientMasterEntity) {
        this.clientMasterEntity = clientMasterEntity;
    }

    public FreelancerMasterEntity getFreelancerMasterEntity() {
        return freelancerMasterEntity;
    }

    public void setFreelancerMasterEntity(FreelancerMasterEntity freelancerMasterEntity) {
        this.freelancerMasterEntity = freelancerMasterEntity;
    }

    public String getClient_project_name() {
        return client_project_name;
    }

    public void setClient_project_name(String client_project_name) {
        this.client_project_name = client_project_name;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getProject_picture() {
        return project_picture;
    }

    public void setProject_picture(String project_picture) {
        this.project_picture = project_picture;
    }

    public SkillsEntity getSkillsEntity() {
        return skillsEntity;
    }

    public void setSkillsEntity(SkillsEntity skillsEntity) {
        this.skillsEntity = skillsEntity;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public LocalDateTime getCreated_at() {
        return created_at;
    }

    public void setCreated_at(LocalDateTime created_at) {
        this.created_at = created_at;
    }
}
