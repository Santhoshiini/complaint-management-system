package com.example.complaints.config;

import com.example.complaints.model.Complaint;
import com.example.complaints.repository.ComplaintRepository;
import com.example.complaints.service.ComplaintService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataSeeder implements CommandLineRunner {

    private final ComplaintRepository complaintRepository;
    private final ComplaintService complaintService;

    public DataSeeder(ComplaintRepository complaintRepository, ComplaintService complaintService) {
        this.complaintRepository = complaintRepository;
        this.complaintService = complaintService;
    }

    @Override
    public void run(String... args) {
        if (complaintRepository.count() == 0) {
            // Complaint 1: Open
            Complaint c1 = new Complaint();
            c1.setTitle("Large pothole on Cherry Street");
            c1.setDescription("A very dangerous pothole has formed in Salem. Needs urgent repair.");
            c1.setReporter("Citizen Joe");
            c1.setCategory("Roads");
            c1.setCity("Salem");
            complaintService.createComplaint(c1);

            // Complaint 2: In Progress & Assigned
            Complaint c2 = new Complaint();
            c2.setTitle("No water for 2 days");
            c2.setDescription("There has been no water supply in the Gandhipuram area of Coimbatore.");
            c2.setReporter("Jane Doe");
            c2.setCategory("Water Supply");
            c2.setCity("Coimbatore");
            Complaint savedC2 = complaintService.createComplaint(c2);
            complaintService.assignComplaint(savedC2.getId(), "Staff Deva");
        }
    }
}

