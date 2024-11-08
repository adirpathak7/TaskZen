/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.it.taskzen.entities;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
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
@Table(name = "skill_tbl")
public class SkillsEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int skill_id;

    @ManyToMany(mappedBy = "skills")
    private Set<FreelancerMasterEntity> freelancers = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "freelancer_id", referencedColumnName = "freelancer_id")
    private FreelancerMasterEntity freelancerMasterEntity;

    @ManyToOne
    @JoinColumn(name = "client_id", referencedColumnName = "client_id")
    private ClientMasterEntity clientMasterEntity;

    private String skill_name;
    private LocalDateTime created_at;

    public SkillsEntity() {
    }

    public SkillsEntity(int skill_id, FreelancerMasterEntity freelancerMasterEntity, ClientMasterEntity clientMasterEntity, String skill_name, LocalDateTime created_at) {
        this.skill_id = skill_id;
        this.freelancerMasterEntity = freelancerMasterEntity;
        this.clientMasterEntity = clientMasterEntity;
        this.skill_name = skill_name;
        this.created_at = created_at;
    }

    public int getSkill_id() {
        return skill_id;
    }

    public void setSkill_id(int skill_id) {
        this.skill_id = skill_id;
    }

    public Set<FreelancerMasterEntity> getFreelancers() {
        return freelancers;
    }

    public void setFreelancers(Set<FreelancerMasterEntity> freelancers) {
        this.freelancers = freelancers;
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

    public String getSkill_name() {
        return skill_name;
    }

    public void setSkill_name(String skill_name) {
        this.skill_name = skill_name;
    }

    public LocalDateTime getCreated_at() {
        return created_at;
    }

    public void setCreated_at(LocalDateTime created_at) {
        this.created_at = created_at;
    }

}
