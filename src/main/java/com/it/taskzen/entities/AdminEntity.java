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
import javax.persistence.PrePersist;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 *
 * @author Aditya Pathak R
 */
@Entity
@Table(name = "admin_tbl")
public class AdminEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "admin_seq")
    @SequenceGenerator(name = "admin_seq", sequenceName = "admin_sequence", allocationSize = 1)
    private Long admin_id;

    private String email;
    private String password;
    private LocalDateTime created_at;

    @Enumerated(EnumType.STRING)
    private Role role;

    public AdminEntity() {
    }

    public AdminEntity(Long admin_id, String email, String password, LocalDateTime created_at, Role role) {
        this.admin_id = admin_id;
        this.email = email;
        this.password = password;
        this.created_at = created_at;
        this.role = role;
    }

    public AdminEntity(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public Long getAdmin_id() {
        return admin_id;
    }

    public void setAdmin_id(Long admin_id) {
        this.admin_id = admin_id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public LocalDateTime getCreated_at() {
        return created_at;
    }

    public void setCreated_at(LocalDateTime created_at) {
        this.created_at = created_at;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    @PrePersist
    protected void onCreate() {
        created_at = LocalDateTime.now();
        if (role == null) {
            role = Role.admin;
        }
    }

    public enum Role {
        admin("admin");

        private final String value;

        Role(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }
}
