package com.cms.complaint_management_system.repository;

import com.cms.complaint_management_system.entity.*;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UpdateLogRepository extends JpaRepository<UpdateLog, Long> {
}