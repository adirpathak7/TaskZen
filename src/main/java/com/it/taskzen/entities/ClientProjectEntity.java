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
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 *
 * @author Aditya Pathak R
 */
@Entity
@Table(name = "client_project_tbl")
public class ClientProjectEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "client_project_seq")
    @SequenceGenerator(name = "client_project_seq", sequenceName = "client_project_sequence", allocationSize = 1)
    private Long client_project_id;

    private String client_project_name;
    private String description;
    private String project_picture;

    private String duration;
    private String minimum_range;
    private String maximum_range;

    @Enumerated(EnumType.STRING)
    private Status status;

    @ManyToOne
    @JoinColumn(name = "client_id", referencedColumnName = "client_id")
    private ClientMasterEntity client_id;

    private LocalDateTime created_at;

    public ClientProjectEntity() {
    }

    public ClientProjectEntity(Long client_project_id, String client_project_name, String description, String project_picture, String duration, String minimum_range, String maximum_range, Status status, ClientMasterEntity client_id, LocalDateTime created_at) {
        this.client_project_id = client_project_id;
        this.client_project_name = client_project_name;
        this.description = description;
        this.project_picture = project_picture;
        this.duration = duration;
        this.minimum_range = minimum_range;
        this.maximum_range = maximum_range;
        this.status = status;
        this.client_id = client_id;
        this.created_at = created_at;
    }

    public ClientProjectEntity(String client_project_name, String description, String duration, String minimum_range, String maximum_range) {
        this.client_project_name = client_project_name;
        this.description = description;
        this.duration = duration;
        this.minimum_range = minimum_range;
        this.maximum_range = maximum_range;
    }

    public Long getClient_project_id() {
        return client_project_id;
    }

    public void setClient_project_id(Long client_project_id) {
        this.client_project_id = client_project_id;
    }

    public String getClient_project_name() {
        return client_project_name;
    }

    public void setClient_project_name(String client_project_name) {
        this.client_project_name = client_project_name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getProject_picture() {
        return project_picture;
    }

    public void setProject_picture(String project_picture) {
        this.project_picture = project_picture;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getMinimum_range() {
        return minimum_range;
    }

    public void setMinimum_range(String minimum_range) {
        this.minimum_range = minimum_range;
    }

    public String getMaximum_range() {
        return maximum_range;
    }

    public void setMaximum_range(String maximum_range) {
        this.maximum_range = maximum_range;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public ClientMasterEntity getClient_id() {
        return client_id;
    }

    public void setClient_id(ClientMasterEntity client_id) {
        this.client_id = client_id;
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
        pending("pending"), inProgress("inProgress"), halfCompleted("halfCompleted"), completed("completed"), remove("remove"), done("done");

        private final String value;

        Status(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }
}
