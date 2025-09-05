package com.example.complaints.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class UpdateLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "complaint_id", nullable = false)
    private Complaint complaint;
    
    private String performedBy; // Changed 'user' to 'performedBy' for clarity
    private String note;
    
    @Enumerated(EnumType.STRING)
    private Status statusAfter;
    
    private LocalDateTime createdAt;
    
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
    
    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Complaint getComplaint() { return complaint; }
    public void setComplaint(Complaint complaint) { this.complaint = complaint; }
    public String getPerformedBy() { return performedBy; }
    public void setPerformedBy(String performedBy) { this.performedBy = performedBy; }
    public String getNote() { return note; }
    public void setNote(String note) { this.note = note; }
    public Status getStatusAfter() { return statusAfter; }
    public void setStatusAfter(Status statusAfter) { this.statusAfter = statusAfter; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}

