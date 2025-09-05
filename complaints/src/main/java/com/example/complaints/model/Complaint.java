package com.example.complaints.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Complaint {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "Reporter name cannot be empty")
    private String reporter;
    
    @NotEmpty(message = "Category cannot be empty")
    private String category;
    
    @NotEmpty(message = "City cannot be empty")
    private String city;

    @NotEmpty(message = "Title cannot be empty")
    @Size(min = 5, max = 100, message = "Title must be between 5 and 100 characters")
    private String title;

    @NotEmpty(message = "Description cannot be empty")
    @Column(length = 1000)
    private String description;
    
    @Enumerated(EnumType.STRING)
    private Status status;
    
    private LocalDateTime createdAt;
    
    @OneToMany(mappedBy = "complaint", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @OrderBy("createdAt DESC")
    private List<UpdateLog> updateLogs = new ArrayList<>();

    @OneToOne(mappedBy = "complaint", cascade = CascadeType.ALL)
    private Assignment assignment;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        if (status == null) {
            status = Status.OPEN;
        }
    }
    
    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getReporter() { return reporter; }
    public void setReporter(String reporter) { this.reporter = reporter; }
    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }
    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public Status getStatus() { return status; }
    public void setStatus(Status status) { this.status = status; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    public List<UpdateLog> getUpdateLogs() { return updateLogs; }
    public void setUpdateLogs(List<UpdateLog> updateLogs) { this.updateLogs = updateLogs; }
    public Assignment getAssignment() { return assignment; }
    public void setAssignment(Assignment assignment) { this.assignment = assignment; }
}

