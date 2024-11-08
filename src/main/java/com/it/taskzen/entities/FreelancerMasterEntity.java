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
import javax.persistence.Table;

/**
 *
 * @author Aditya Pathak R
 */
@Entity
@Table(name = "freelancer_mst_tbl")
public class FreelancerMasterEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int freelancer_id;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    private UserEntity userEntity;

    @ManyToMany
    @JoinTable(
            name = "freelancer_skills",
            joinColumns = @JoinColumn(name = "freelancer_id"),
            inverseJoinColumns = @JoinColumn(name = "skill_id")
    )
    private Set<SkillsEntity> skills = new HashSet<>();

    private String contact;
    private String profile_picture;
    private String country;
    private String dob;
    private String gender;
    private String github_link;
    private String linkedin_link;
    private String portfolio_link;

    @Enumerated(EnumType.STRING)
    private Freelancer_Status freelancer_status;
    private LocalDateTime created_at;

    public FreelancerMasterEntity() {
    }

    public FreelancerMasterEntity(int freelancer_id, UserEntity userEntity, String contact, String profile_picture, String country, String dob, String gender, String github_link, String linkedin_link, String portfolio_link, Freelancer_Status freelancer_status, LocalDateTime created_at) {
        this.freelancer_id = freelancer_id;
        this.userEntity = userEntity;
        this.contact = contact;
        this.profile_picture = profile_picture;
        this.country = country;
        this.dob = dob;
        this.gender = gender;
        this.github_link = github_link;
        this.linkedin_link = linkedin_link;
        this.portfolio_link = portfolio_link;
        this.freelancer_status = freelancer_status;
        this.created_at = created_at;
    }

    public int getFreelancer_id() {
        return freelancer_id;
    }

    public void setFreelancer_id(int freelancer_id) {
        this.freelancer_id = freelancer_id;
    }

    public UserEntity getUserEntity() {
        return userEntity;
    }

    public void setUserEntity(UserEntity userEntity) {
        this.userEntity = userEntity;
    }

    public Set<SkillsEntity> getSkills() {
        return skills;
    }

    public void setSkills(Set<SkillsEntity> skills) {
        this.skills = skills;
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

    public Freelancer_Status getFreelancer_status() {
        return freelancer_status;
    }

    public void setFreelancer_status(Freelancer_Status freelancer_status) {
        this.freelancer_status = freelancer_status;
    }

    public LocalDateTime getCreated_at() {
        return created_at;
    }

    public void setCreated_at(LocalDateTime created_at) {
        this.created_at = created_at;
    }

    public enum Freelancer_Status {
        active("active"), inactive("inactive");

        private final String value;

        Freelancer_Status(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }
}
