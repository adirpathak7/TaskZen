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
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;

/**
 *
 * @author Aditya Pathak R
 */
@Entity
@Table(name = "client_mst_tbl")
public class ClientMasterEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int client_id;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    private UserEntity userEntity;

    private String contact;
    private String profile_picture;
    private String country;
    private String establish;
    private String industry;
    private LocalDateTime created_at;

    public ClientMasterEntity() {
    }

    public ClientMasterEntity(int client_id, UserEntity userEntity, String contact, String profile_picture, String country, String establish, String industry, LocalDateTime created_at) {
        this.client_id = client_id;
        this.userEntity = userEntity;
        this.contact = contact;
        this.profile_picture = profile_picture;
        this.country = country;
        this.establish = establish;
        this.industry = industry;
        this.created_at = created_at;
    }

    public ClientMasterEntity(String contact, String country, String establish, String industry) {
        this.contact = contact;
        this.country = country;
        this.establish = establish;
        this.industry = industry;
    }

    public int getClient_id() {
        return client_id;
    }

    public void setClient_id(int client_id) {
        this.client_id = client_id;
    }

    public UserEntity getUserEntity() {
        return userEntity;
    }

    public void setUserEntity(UserEntity userEntity) {
        this.userEntity = userEntity;
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

    public String getEstablish() {
        return establish;
    }

    public void setEstablish(String establish) {
        this.establish = establish;
    }

    public String getIndustry() {
        return industry;
    }

    public void setIndustry(String industry) {
        this.industry = industry;
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
