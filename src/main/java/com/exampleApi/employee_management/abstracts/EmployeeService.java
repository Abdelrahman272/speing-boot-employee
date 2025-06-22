package com.exampleApi.employee_management.abstracts;

import java.util.List;
import java.util.UUID;

import com.exampleApi.employee_management.dtos.EmployeeCreate;
import com.exampleApi.employee_management.dtos.EmployeeUpdate;
import com.exampleApi.employee_management.entities.Employee;

public interface EmployeeService {
    Employee findOne(UUID employeeId);

    List<Employee> findAll();

    void deleteOne(UUID employeeId);

    Employee updateOne(UUID employeeId, EmployeeUpdate employee);

    Employee createOne(EmployeeCreate employee);
}
