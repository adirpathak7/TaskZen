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
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int message_id;

    private int conversation_id;
    private int sender_id;
    private int receiver_id;

    @Enumerated(EnumType.STRING)
    private Sender_Role sender_role;

    @Enumerated(EnumType.STRING)
    private Receiver_Role receiver_role;

    private String message_content;
    private LocalDateTime messageTime;

    @Enumerated(EnumType.STRING)
    private Status status;

    private LocalDateTime created_at;

    public MessagesEntity() {
    }

    public MessagesEntity(int message_id, int conversation_id, int sender_id, int receiver_id, Sender_Role sender_role, Receiver_Role receiver_role, String message_content, LocalDateTime messageTime, Status status, LocalDateTime created_at) {
        this.message_id = message_id;
        this.conversation_id = conversation_id;
        this.sender_id = sender_id;
        this.receiver_id = receiver_id;
        this.sender_role = sender_role;
        this.receiver_role = receiver_role;
        this.message_content = message_content;
        this.messageTime = messageTime;
        this.status = status;
        this.created_at = created_at;
    }

    public int getMessage_id() {
        return message_id;
    }

    public void setMessage_id(int message_id) {
        this.message_id = message_id;
    }

    public int getConversation_id() {
        return conversation_id;
    }

    public void setConversation_id(int conversation_id) {
        this.conversation_id = conversation_id;
    }

    public int getSender_id() {
        return sender_id;
    }

    public void setSender_id(int sender_id) {
        this.sender_id = sender_id;
    }

    public int getReceiver_id() {
        return receiver_id;
    }

    public void setReceiver_id(int receiver_id) {
        this.receiver_id = receiver_id;
    }

    public Sender_Role getSender_role() {
        return sender_role;
    }

    public void setSender_role(Sender_Role sender_role) {
        this.sender_role = sender_role;
    }

    public Receiver_Role getReceiver_role() {
        return receiver_role;
    }

    public void setReceiver_role(Receiver_Role receiver_role) {
        this.receiver_role = receiver_role;
    }

    public String getMessage_content() {
        return message_content;
    }

    public void setMessage_content(String message_content) {
        this.message_content = message_content;
    }

    public LocalDateTime getMessageTime() {
        return messageTime;
    }

    public void setMessageTime(LocalDateTime messageTime) {
        this.messageTime = messageTime;
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

    public enum Sender_Role {
        admin("admin"), freelancer("freelancer"), client("client");

        private final String value;

        Sender_Role(String value) {
            this.value = value;
        }

        public String getSender_Role() {
            return value;
        }
    }

    public enum Receiver_Role {
        admin("admin"), freelancer("freelancer"), client("client");

        private final String value;

        Receiver_Role(String value) {
            this.value = value;
        }

        public String getReceiver_Role() {
            return value;
        }
    }

    public enum Status {
        sent("sent"), delivered("delivered"), read("read");

        private final String value;

        Status(String value) {
            this.value = value;
        }

        public String getStatus() {
            return value;
        }
    }
}
