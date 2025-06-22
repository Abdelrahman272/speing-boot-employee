package com.exampleApi.employee_management.abstracts;

import java.util.List;
import java.util.UUID;

import com.exampleApi.employee_management.dtos.LeaveRequestCreate;
import com.exampleApi.employee_management.entities.LeaveRequest;

public interface LeaveRequestService {

    LeaveRequest createOne(LeaveRequestCreate leaveRequestCreate, UUID employeeId);
    List<LeaveRequest> findAllByEmployeeId(UUID employeeId);
}
