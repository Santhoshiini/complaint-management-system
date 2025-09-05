package com.example.complaints.controller;

import com.example.complaints.model.Complaint;
import com.example.complaints.model.Status;
import com.example.complaints.service.ComplaintService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class ComplaintController {

    private final ComplaintService complaintService;

    public ComplaintController(ComplaintService complaintService) {
        this.complaintService = complaintService;
    }
    
    @GetMapping("/")
    public String viewAdminQueue(Model model) {
        model.addAttribute("complaints", complaintService.findAll());
        model.addAttribute("staffList", List.of("Staff Santhoshini", "Staff Monisha", "Staff Deva"));
        return "admin-queue";
    }

    @GetMapping("/complaints/new")
    public String showNewComplaintForm(Model model) {
        model.addAttribute("complaint", new Complaint());
        model.addAttribute("categories", List.of("Roads", "Water Supply", "Electricity", "Waste Management"));
        model.addAttribute("cities", List.of("Salem", "Chennai", "Coimbatore", "Madurai"));
        return "new-complaint";
    }

    @PostMapping("/complaints/new")
    public String createNewComplaint(@Valid @ModelAttribute Complaint complaint, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("categories", List.of("Roads", "Water Supply", "Electricity", "Waste Management"));
            model.addAttribute("cities", List.of("Salem", "Chennai", "Coimbatore", "Madurai"));
            return "new-complaint";
        }
        complaintService.createComplaint(complaint);
        return "redirect:/";
    }

    @GetMapping("/complaints/{id}")
    public String viewComplaintDetail(@PathVariable Long id, Model model) {
        model.addAttribute("complaint", complaintService.findById(id));
        model.addAttribute("statuses", Status.values());
        return "complaint-detail";
    }

    @PostMapping("/complaints/assign")
    public String assignComplaint(@RequestParam Long complaintId, @RequestParam String staffName) {
        complaintService.assignComplaint(complaintId, staffName);
        return "redirect:/";
    }
    
    @PostMapping("/complaints/update")
    public String addUpdate(@RequestParam Long complaintId, @RequestParam Status newStatus, @RequestParam String note) {
        complaintService.addUpdate(complaintId, note, newStatus);
        return "redirect:/complaints/" + complaintId;
    }
}

