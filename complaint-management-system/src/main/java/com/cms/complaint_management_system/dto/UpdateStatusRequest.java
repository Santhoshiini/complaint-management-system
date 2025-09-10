package com.cms.complaint_management_system.dto;

import com.cms.complaint_management_system.entity.Status;

public class UpdateStatusRequest {
    private String user;
    private String note;
    private Status newStatus;

    // Default constructor
    public UpdateStatusRequest() {}

    // Getters and setters
    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Status getNewStatus() {
        return newStatus;
    }

    public void setNewStatus(Status newStatus) {
        this.newStatus = newStatus;
    }
}