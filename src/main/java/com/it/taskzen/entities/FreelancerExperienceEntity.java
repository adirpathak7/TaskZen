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
@Table(name = "freelancer_experience_tbl")
public class FreelancerExperienceEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "freelancer_experience_seq")
    @SequenceGenerator(name = "freelancer_experience_seq", sequenceName = "freelancer_experience_sequence", allocationSize = 1)
    private Long freelancer_experience;

    @ManyToOne
    @JoinColumn(name = "freelancer_id", referencedColumnName = "freelancer_id")
    private FreelancerMasterEntity freelancer_id;

    private String client_name;
    private String designation;
    private String starting_date;
    private LocalDateTime ending_date;
    private LocalDateTime created_at;

    public FreelancerExperienceEntity() {
    }

    public FreelancerExperienceEntity(Long freelancer_experience, FreelancerMasterEntity freelancer_id, String client_name, String designation, String starting_date, LocalDateTime ending_date, LocalDateTime created_at) {
        this.freelancer_experience = freelancer_experience;
        this.freelancer_id = freelancer_id;
        this.client_name = client_name;
        this.designation = designation;
        this.starting_date = starting_date;
        this.ending_date = ending_date;
        this.created_at = created_at;
    }

    public Long getFreelancer_experience() {
        return freelancer_experience;
    }

    public void setFreelancer_experience(Long freelancer_experience) {
        this.freelancer_experience = freelancer_experience;
    }

    public FreelancerMasterEntity getFreelancer_id() {
        return freelancer_id;
    }

    public void setFreelancer_id(FreelancerMasterEntity freelancer_id) {
        this.freelancer_id = freelancer_id;
    }

    public String getClient_name() {
        return client_name;
    }

    public void setClient_name(String client_name) {
        this.client_name = client_name;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getStarting_date() {
        return starting_date;
    }

    public void setStarting_date(String starting_date) {
        this.starting_date = starting_date;
    }

    public LocalDateTime getEnding_date() {
        return ending_date;
    }

    public void setEnding_date(LocalDateTime ending_date) {
        this.ending_date = ending_date;
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
