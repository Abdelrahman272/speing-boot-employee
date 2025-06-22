package com.exampleApi.employee_management.services;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import com.exampleApi.employee_management.abstracts.LeaveRequestService;
import com.exampleApi.employee_management.dtos.LeaveRequestCreate;
import com.exampleApi.employee_management.entities.Employee;
import com.exampleApi.employee_management.entities.LeaveRequest;
import com.exampleApi.employee_management.repositories.EmployeeRepo;
import com.exampleApi.employee_management.repositories.LeaveRequestRepo;
import com.exampleApi.employee_management.shared.CustomResponseException;

@Service
public class LeaveRequestImpl implements LeaveRequestService {

    @Autowired
    private EmployeeRepo employeeRepo;

    @Autowired
    private LeaveRequestRepo leaveRequestRepo;

    @PreAuthorize("@securityUtils.isOwner(#employeeId)")
    public LeaveRequest createOne(
            LeaveRequestCreate leaveRequestCreate,
            UUID employeeId) {

        Employee employee = employeeRepo.findById(employeeId)
                .orElseThrow(() -> CustomResponseException.ResourceNotFound(
                        "Employee with id " + employeeId + " not found"));

        LeaveRequest leaveRequest = new LeaveRequest();

        leaveRequest.setStatus("PENDING");
        leaveRequest.setReason(leaveRequestCreate.reason());
        leaveRequest.setStartDate(leaveRequestCreate.startDate());
        leaveRequest.setEndDate(leaveRequestCreate.endDate());
        leaveRequest.setEmployee(employee);

        leaveRequestRepo.save(leaveRequest);

        return leaveRequest;
    }

    @PreAuthorize("@securityUtils.isOwner(#employeeId)")
    public List<LeaveRequest> findAllByEmployeeId(UUID employeeId) {
        return leaveRequestRepo.findAllByEmployeeId(employeeId);
    }
}
