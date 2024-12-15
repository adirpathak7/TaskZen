/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.it.taskzen.entities;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 *
 * @author Aditya Pathak R
 */
@Entity
@Table(name = "freelancer_mst_tbl")
public class FreelancerMasterEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "freelancer_mst_seq")
    @SequenceGenerator(name = "freelancer_mst_seq", sequenceName = "freelancer_mst_sequence", allocationSize = 1)
    private Long freelancer_id;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    private UserEntity user_id;

    private String contact;
    private String profile_picture;
    private String country;
    private String dob;
    private String gender;
    private String github_link;
    private String linkedin_link;
    private String portfolio_link;
    private LocalDateTime created_at;

    @Enumerated(EnumType.STRING)
    private Status status;

    public FreelancerMasterEntity() {
    }

    public FreelancerMasterEntity(Long freelancer_id, UserEntity user_id, String contact, String profile_picture, String country, String dob, String gender, String github_link, String linkedin_link, String portfolio_link, LocalDateTime created_at, Status status) {
        this.freelancer_id = freelancer_id;
        this.user_id = user_id;
        this.contact = contact;
        this.profile_picture = profile_picture;
        this.country = country;
        this.dob = dob;
        this.gender = gender;
        this.github_link = github_link;
        this.linkedin_link = linkedin_link;
        this.portfolio_link = portfolio_link;
        this.created_at = created_at;
        this.status = status;
    }

    public FreelancerMasterEntity(String contact, String country, String dob, String gender, String github_link, String linkedin_link, String portfolio_link) {
        this.contact = contact;
        this.country = country;
        this.dob = dob;
        this.gender = gender;
        this.github_link = github_link;
        this.linkedin_link = linkedin_link;
        this.portfolio_link = portfolio_link;
    }

    public Long getFreelancer_id() {
        return freelancer_id;
    }

    public void setFreelancer_id(Long freelancer_id) {
        this.freelancer_id = freelancer_id;
    }

    public UserEntity getUser_id() {
        return user_id;
    }

    public void setUser_id(UserEntity user_id) {
        this.user_id = user_id;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getProfile_picture() {
        return profile_picture;
    }

    public void setProfile_picture(String profile_picture) {
        this.profile_picture = profile_picture;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getGithub_link() {
        return github_link;
    }

    public void setGithub_link(String github_link) {
        this.github_link = github_link;
    }

    public String getLinkedin_link() {
        return linkedin_link;
    }

    public void setLinkedin_link(String linkedin_link) {
        this.linkedin_link = linkedin_link;
    }

    public String getPortfolio_link() {
        return portfolio_link;
    }

    public void setPortfolio_link(String portfolio_link) {
        this.portfolio_link = portfolio_link;
    }

    public LocalDateTime getCreated_at() {
        return created_at;
    }

    public void setCreated_at(LocalDateTime created_at) {
        this.created_at = created_at;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @PrePersist
    protected void onCreate() {
        created_at = LocalDateTime.now();
        if (status == null) {
            status = Status.pending;
        }
    }

    public enum Status {
        pending("pending"), approved("approved");

        private final String value;

        Status(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

}
