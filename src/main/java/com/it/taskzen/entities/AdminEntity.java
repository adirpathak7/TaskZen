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
import javax.persistence.Table;

/**
 *
 * @author Aditya Pathak R
 */
@Entity
@Table(name = "admin_tbl")
public class AdminEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int admin_id;

    private String email;
    private String password;
    private LocalDateTime created_at;

    public AdminEntity() {
    }

    public AdminEntity(int admin_id, String email, String password, LocalDateTime created_at) {
        this.admin_id = admin_id;
        this.email = email;
        this.password = password;
        this.created_at = created_at;
    }

    public AdminEntity(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public int getAdmin_id() {
        return admin_id;
    }

    public void setAdmin_id(int admin_id) {
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
}
