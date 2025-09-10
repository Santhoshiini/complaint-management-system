package com.cms.complaint_management_system.controller;

import com.cms.complaint_management_system.entity.Complaint;
import com.cms.complaint_management_system.entity.Status;
import com.cms.complaint_management_system.service.ComplaintService;
import com.cms.complaint_management_system.dto.AssignRequest;
import com.cms.complaint_management_system.dto.UpdateStatusRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/complaints")
@CrossOrigin(origins = {"http://localhost:3000", "http://127.0.0.1:3000", "null"}) // Crucial for frontend connection!
public class ComplaintController {

    @Autowired
    private ComplaintService complaintService;

    // Create a new complaint
    @PostMapping
    public Complaint createComplaint(@RequestBody Complaint complaint) {
        return complaintService.createComplaint(complaint);
    }

    // Get all complaints or filter by status
    @GetMapping
    public List<Complaint> getAllComplaints(@RequestParam(required = false) Status status) {
        if (status != null) {
            return complaintService.getComplaintsByStatus(status);
        }
        return complaintService.getAllComplaints();
    }

    // Get complaint by ID
    @GetMapping("/{id}")
    public Optional<Complaint> getComplaintById(@PathVariable Long id) {
        return complaintService.getComplaintById(id);
    }

    // Get complaints by reporter name
    @GetMapping("/reporter/{reporterName}")
    public List<Complaint> getComplaintsByReporter(@PathVariable String reporterName) {
        return complaintService.getComplaintsByReporter(reporterName);
    }
    
    // Add this method to test basic connectivity
    @GetMapping("/test")
    public String testEndpoint() {
        return "Backend is working! Time: " + new java.util.Date();
    }
    

    // Assign complaint to staff
    @PostMapping("/{complaintId}/assign")
    public void assignComplaint(@PathVariable Long complaintId, @RequestBody AssignRequest request) {
        complaintService.assignComplaint(complaintId, request.getStaffName());
    }

    // Update complaint status
    @PostMapping("/{complaintId}/update-status")
    public void updateStatus(@PathVariable Long complaintId, @RequestBody UpdateStatusRequest request) {
        complaintService.updateStatus(complaintId, request.getUser(), request.getNote(), request.getNewStatus());
    }
}