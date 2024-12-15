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
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 *
 * @author Aditya Pathak R
 */
@Entity
@Table(name = "client_mst_tbl")
public class ClientMasterEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "client_mst_seq")
    @SequenceGenerator(name = "client_mst_seq", sequenceName = "client_mst_sequence", allocationSize = 1)
    private Long client_id;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    private UserEntity user_id;

    private String contact;
    private String profile_picture;
    private String country;
    private String establish;
    private String industry;

    @Enumerated(EnumType.STRING)
    private Status status;

    private LocalDateTime created_at;

    public ClientMasterEntity() {
    }

    public ClientMasterEntity(Long client_id, UserEntity user_id, String contact, String profile_picture, String country, String establish, String industry, Status status, LocalDateTime created_at) {
        this.client_id = client_id;
        this.user_id = user_id;
        this.contact = contact;
        this.profile_picture = profile_picture;
        this.country = country;
        this.establish = establish;
        this.industry = industry;
        this.status = status;
        this.created_at = created_at;
    }

    public ClientMasterEntity(String contact, String country, String establish, String industry) {
        this.contact = contact;
        this.country = country;
        this.establish = establish;
        this.industry = industry;
    }

    public Long getClient_id() {
        return client_id;
    }

    public void setClient_id(Long client_id) {
        this.client_id = client_id;
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

    public UserEntity getUser_id() {
        return user_id;
    }

    public void setUser_id(UserEntity user_id) {
        this.user_id = user_id;
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
