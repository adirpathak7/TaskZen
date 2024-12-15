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
import javax.persistence.PrePersist;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 *
 * @author Aditya Pathak R
 */
@Entity
@Table(name = "contact_tbl")
public class ContactUsEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "contact_seq")
    @SequenceGenerator(name = "contact_seq", sequenceName = "contact_sequence", allocationSize = 1)
    private Long contact_id;
    
    private String email;
    private String contact;
    private String message;
    private LocalDateTime created_at;

    public ContactUsEntity() {
    }

    public ContactUsEntity(Long contact_id, String email, String contact, String message, LocalDateTime created_at) {
        this.contact_id = contact_id;
        this.email = email;
        this.contact = contact;
        this.message = message;
        this.created_at = created_at;
    }

    public Long getContact_id() {
        return contact_id;
    }

    public void setContact_id(Long contact_id) {
        this.contact_id = contact_id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
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
