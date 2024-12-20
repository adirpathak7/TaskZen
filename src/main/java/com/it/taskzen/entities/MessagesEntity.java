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
@Table(name = "message_tbl")
public class MessagesEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "message_seq")
    @SequenceGenerator(name = "message_seq", sequenceName = "message_sequence", allocationSize = 1)
    private Long message_id;

    private Long sender_id;
    private Long receiver_id;

    private String sender_role;

    private String receiver_role;

    private String message_content;

    @Enumerated(EnumType.STRING)
    private Status status;

    private LocalDateTime created_at;

    public MessagesEntity() {
    }

    public MessagesEntity(Long message_id, Long sender_id, Long receiver_id, String sender_role, String receiver_role, String message_content, Status status, LocalDateTime created_at) {
        this.message_id = message_id;
        this.sender_id = sender_id;
        this.receiver_id = receiver_id;
        this.sender_role = sender_role;
        this.receiver_role = receiver_role;
        this.message_content = message_content;
        this.status = status;
        this.created_at = created_at;
    }

    public MessagesEntity(String message_content) {
        this.message_content = message_content;
    }

    public Long getMessage_id() {
        return message_id;
    }

    public void setMessage_id(Long message_id) {
        this.message_id = message_id;
    }

    public Long getSender_id() {
        return sender_id;
    }

    public void setSender_id(Long sender_id) {
        this.sender_id = sender_id;
    }

    public Long getReceiver_id() {
        return receiver_id;
    }

    public void setReceiver_id(Long receiver_id) {
        this.receiver_id = receiver_id;
    }

    public String getSender_role() {
        return sender_role;
    }

    public void setSender_role(String sender_role) {
        this.sender_role = sender_role;
    }

    public String getReceiver_role() {
        return receiver_role;
    }

    public void setReceiver_role(String receiver_role) {
        this.receiver_role = receiver_role;
    }

    public String getMessage_content() {
        return message_content;
    }

    public void setMessage_content(String message_content) {
        this.message_content = message_content;
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

    @PrePersist
    protected void onCreate() {
        created_at = LocalDateTime.now();
        if (status == null) {
            status = Status.delivered;
        }
    }

    public void setCreated_at(LocalDateTime created_at) {
        this.created_at = created_at;
    }

    public enum Status {
        delivered("delivered"), read("read");

        private final String value;

        Status(String value) {
            this.value = value;
        }

        public String getStatus() {
            return value;
        }
    }
}
