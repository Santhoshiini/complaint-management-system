package com.cms.complaint_management_system.repository;

import com.cms.complaint_management_system.entity.*;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface AssignmentRepository extends JpaRepository<Assignment, Long> {
    Optional<Assignment> findByComplaintIdAndActive(Long complaintId, Boolean active);
}