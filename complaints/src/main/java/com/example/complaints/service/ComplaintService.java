package com.example.complaints.service;

import com.example.complaints.model.*;
import com.example.complaints.repository.ComplaintRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

@Service
public class ComplaintService {

    private final ComplaintRepository complaintRepository;
    
    private static final Map<Status, List<Status>> ALLOWED_TRANSITIONS = Map.of(
        Status.OPEN, List.of(Status.IN_PROGRESS),
        Status.IN_PROGRESS, List.of(Status.RESOLVED),
        Status.RESOLVED, List.of(Status.CLOSED),
        Status.CLOSED, List.of()
    );

    public ComplaintService(ComplaintRepository complaintRepository) {
        this.complaintRepository = complaintRepository;
    }

    public List<Complaint> findAll() {
        return complaintRepository.findAll();
    }

    public Complaint findById(Long id) {
        return complaintRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Complaint not found with ID: " + id));
    }

    @Transactional
    public Complaint createComplaint(Complaint complaint) {
        UpdateLog log = new UpdateLog();
        log.setComplaint(complaint);
        log.setPerformedBy(complaint.getReporter());
        log.setNote("Complaint filed.");
        log.setStatusAfter(Status.OPEN);
        complaint.getUpdateLogs().add(log);
        return complaintRepository.save(complaint);
    }

    @Transactional
    public void assignComplaint(Long complaintId, String staffName) {
        Complaint complaint = findById(complaintId);
        if (complaint.getAssignment() != null) {
            throw new IllegalStateException("Complaint is already assigned.");
        }

        Assignment assignment = new Assignment();
        assignment.setComplaint(complaint);
        assignment.setStaff(staffName);
        complaint.setAssignment(assignment);

        UpdateLog log = new UpdateLog();
        log.setComplaint(complaint);
        log.setPerformedBy("Admin");
        log.setNote("Assigned to " + staffName);

        if (complaint.getStatus() == Status.OPEN) {
            complaint.setStatus(Status.IN_PROGRESS);
            log.setStatusAfter(Status.IN_PROGRESS);
        } else {
             log.setStatusAfter(complaint.getStatus());
        }
        
        complaint.getUpdateLogs().add(log);
        complaintRepository.save(complaint);
    }

    @Transactional
    public void addUpdate(Long complaintId, String note, Status newStatus) {
        Complaint complaint = findById(complaintId);
        Status currentStatus = complaint.getStatus();

        if (newStatus != currentStatus && !ALLOWED_TRANSITIONS.getOrDefault(currentStatus, List.of()).contains(newStatus)) {
            throw new IllegalStateException("Invalid status transition from " + currentStatus + " to " + newStatus);
        }

        complaint.setStatus(newStatus);

        UpdateLog log = new UpdateLog();
        log.setComplaint(complaint);
        log.setPerformedBy("Admin");
        log.setNote(note.isEmpty() ? "Status changed to " + newStatus : note);
        log.setStatusAfter(newStatus);

        complaint.getUpdateLogs().add(log);
        complaintRepository.save(complaint);
    }
}

