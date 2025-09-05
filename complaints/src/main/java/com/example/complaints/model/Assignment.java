package com.example.complaints.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Assignment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "complaint_id", referencedColumnName = "id")
    private Complaint complaint;
    
    private String staff;
    private LocalDateTime assignedAt;

    @PrePersist
    protected void onAssign() {
        assignedAt = LocalDateTime.now();
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Complaint getComplaint() { return complaint; }
    public void setComplaint(Complaint complaint) { this.complaint = complaint; }
    public String getStaff() { return staff; }
    public void setStaff(String staff) { this.staff = staff; }
    public LocalDateTime getAssignedAt() { return assignedAt; }
    public void setAssignedAt(LocalDateTime assignedAt) { this.assignedAt = assignedAt; }
}

