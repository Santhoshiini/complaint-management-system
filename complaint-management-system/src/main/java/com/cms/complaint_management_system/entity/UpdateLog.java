package com.cms.complaint_management_system.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "update_logs")
public class UpdateLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "complaint_id")
    private Complaint complaint;
    
    private String user;
    private String note;
    
    @Enumerated(EnumType.STRING)
    private Status statusAfter;
    
    private LocalDateTime updatedAt = LocalDateTime.now();
    
    // Constructors
    public UpdateLog() {}
    
    public UpdateLog(Complaint complaint, String user, String note, Status statusAfter) {
        this.complaint = complaint;
        this.user = user;
        this.note = note;
        this.statusAfter = statusAfter;
    }
    
    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Complaint getComplaint() { return complaint; }
    public void setComplaint(Complaint complaint) { this.complaint = complaint; }
    public String getUser() { return user; }
    public void setUser(String user) { this.user = user; }
    public String getNote() { return note; }
    public void setNote(String note) { this.note = note; }
    public Status getStatusAfter() { return statusAfter; }
    public void setStatusAfter(Status statusAfter) { this.statusAfter = statusAfter; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
}