package com.cms.complaint_management_system.repository;

import com.cms.complaint_management_system.entity.Complaint;
import com.cms.complaint_management_system.entity.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ComplaintRepository extends JpaRepository<Complaint, Long> {
    List<Complaint> findByStatus(Status status);
    List<Complaint> findByReporterName(String reporterName);
}