package com.cms.complaint_management_system.service;

import com.cms.complaint_management_system.entity.Complaint;
import com.cms.complaint_management_system.entity.Assignment;
import com.cms.complaint_management_system.entity.UpdateLog;
import com.cms.complaint_management_system.entity.Status;
import com.cms.complaint_management_system.repository.ComplaintRepository;
import com.cms.complaint_management_system.repository.AssignmentRepository;
import com.cms.complaint_management_system.repository.UpdateLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ComplaintService {

    @Autowired
    private ComplaintRepository complaintRepository;
    
    @Autowired
    private AssignmentRepository assignmentRepository;
    
    @Autowired
    private UpdateLogRepository updateLogRepository;

    // 1. CRUD Operations
    public Complaint createComplaint(Complaint complaint) {
        Complaint savedComplaint = complaintRepository.save(complaint);
        addUpdateLog(savedComplaint, "System", "Complaint created", Status.OPEN);
        return savedComplaint;
    }

    public List<Complaint> getAllComplaints() {
        return complaintRepository.findAll();
    }

    public Optional<Complaint> getComplaintById(Long id) {
        return complaintRepository.findById(id);
    }

    public List<Complaint> getComplaintsByStatus(Status status) {
        return complaintRepository.findByStatus(status);
    }

    public List<Complaint> getComplaintsByReporter(String reporterName) {
        return complaintRepository.findByReporterName(reporterName);
    }

    // 2. Assignment Logic
    @Transactional
    public void assignComplaint(Long complaintId, String staffName) {
        Complaint complaint = complaintRepository.findById(complaintId)
                .orElseThrow(() -> new RuntimeException("Complaint not found with id: " + complaintId));

        // Deactivate any existing active assignment
        Optional<Assignment> existingAssignment = assignmentRepository.findByComplaintIdAndActive(complaintId, true);
        if (existingAssignment.isPresent()) {
            Assignment assignment = existingAssignment.get();
            assignment.setActive(false);
            assignmentRepository.save(assignment);
        }

        // Create and save new active assignment
        Assignment newAssignment = new Assignment(complaint, staffName);
        assignmentRepository.save(newAssignment);

        // Add a log entry
        addUpdateLog(complaint, "Admin", "Assigned to: " + staffName, complaint.getStatus());
    }

    // 3. Status Transition Logic with Validation
    @Transactional
    public void updateStatus(Long complaintId, String user, String note, Status newStatus) {
        Complaint complaint = complaintRepository.findById(complaintId)
                .orElseThrow(() -> new RuntimeException("Complaint not found with id: " + complaintId));
        
        Status oldStatus = complaint.getStatus();

        // Validate the transition
        if (!isValidTransition(oldStatus, newStatus)) {
            throw new RuntimeException("Invalid status transition from " + oldStatus + " to " + newStatus);
        }

        // Update the complaint status
        complaint.setStatus(newStatus);
        complaintRepository.save(complaint);

        // Log this status change
        addUpdateLog(complaint, user, note, newStatus);
    }

    // Validation for allowed status transitions
    private boolean isValidTransition(Status oldStatus, Status newStatus) {
        switch (oldStatus) {
            case OPEN:
                return newStatus == Status.IN_PROGRESS;
            case IN_PROGRESS:
                return newStatus == Status.RESOLVED;
            case RESOLVED:
                return newStatus == Status.CLOSED;
            case CLOSED:
                return false; // CLOSED is a terminal state
            default:
                return false;
        }
    }

    // Helper method to add update logs
    private void addUpdateLog(Complaint complaint, String user, String note, Status statusAfter) {
        UpdateLog log = new UpdateLog(complaint, user, note, statusAfter);
        updateLogRepository.save(log);
    }
}