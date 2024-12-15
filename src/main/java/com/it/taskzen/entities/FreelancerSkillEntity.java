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
@Table(name = "freelancer_skill_tbl")
public class FreelancerSkillEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "freelancer_skill_seq")
    @SequenceGenerator(name = "freelancer_skill_seq", sequenceName = "freelancer_skill_sequence", allocationSize = 1)
    private Long freelancer_skill_id;

    @ManyToOne
    @JoinColumn(name = "freelancer_id", referencedColumnName = "freelancer_id")
    private FreelancerMasterEntity freelancer_id;

    @ManyToOne
    @JoinColumn(name = "skill_id", referencedColumnName = "skill_id")
    private SkillsEntity skill_id;

    private LocalDateTime created_at;

    public FreelancerSkillEntity() {
    }

    public Long getFreelancer_skill_id() {
        return freelancer_skill_id;
    }

    public void setFreelancer_skill_id(Long freelancer_skill_id) {
        this.freelancer_skill_id = freelancer_skill_id;
    }

    public FreelancerMasterEntity getFreelancer_id() {
        return freelancer_id;
    }

    public void setFreelancer_id(FreelancerMasterEntity freelancer_id) {
        this.freelancer_id = freelancer_id;
    }

    public SkillsEntity getSkill_id() {
        return skill_id;
    }

    public void setSkill_id(SkillsEntity skill_id) {
        this.skill_id = skill_id;
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
