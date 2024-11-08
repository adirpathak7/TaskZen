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
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 *
 * @author Aditya Pathak R
 */
@Entity
@Table(name = "freelancer_education_tbl")
public class FreelancerEducationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int freelancer_education_id;

    @ManyToOne
    @JoinColumn(name = "freelancer_id", referencedColumnName = "freelancer_id")
    private FreelancerMasterEntity cleFreelancerMasterEntity;

    private String university;
    private String course;
    private String start_date;
    private String end_date;
    private LocalDateTime created_at;

    public FreelancerEducationEntity() {
    }

    public FreelancerEducationEntity(int freelancer_education_id, FreelancerMasterEntity cleFreelancerMasterEntity, String university, String course, String start_date, String end_date, LocalDateTime created_at) {
        this.freelancer_education_id = freelancer_education_id;
        this.cleFreelancerMasterEntity = cleFreelancerMasterEntity;
        this.university = university;
        this.course = course;
        this.start_date = start_date;
        this.end_date = end_date;
        this.created_at = created_at;
    }

    public int getFreelancer_education_id() {
        return freelancer_education_id;
    }

    public void setFreelancer_education_id(int freelancer_education_id) {
        this.freelancer_education_id = freelancer_education_id;
    }

    public FreelancerMasterEntity getCleFreelancerMasterEntity() {
        return cleFreelancerMasterEntity;
    }

    public void setCleFreelancerMasterEntity(FreelancerMasterEntity cleFreelancerMasterEntity) {
        this.cleFreelancerMasterEntity = cleFreelancerMasterEntity;
    }

    public String getUniversity() {
        return university;
    }

    public void setUniversity(String university) {
        this.university = university;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public String getStart_date() {
        return start_date;
    }

    public void setStart_date(String start_date) {
        this.start_date = start_date;
    }

    public String getEnd_date() {
        return end_date;
    }

    public void setEnd_date(String end_date) {
        this.end_date = end_date;
    }

    public LocalDateTime getCreated_at() {
        return created_at;
    }

    public void setCreated_at(LocalDateTime created_at) {
        this.created_at = created_at;
    }
}
