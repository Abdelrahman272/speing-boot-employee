package com.exampleApi.employee_management.repositories;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.exampleApi.employee_management.entities.LeaveRequest;

public interface LeaveRequestRepo extends JpaRepository<LeaveRequest, UUID> {
     List<LeaveRequest> findAllByEmployeeId(UUID employeeUd);
}
