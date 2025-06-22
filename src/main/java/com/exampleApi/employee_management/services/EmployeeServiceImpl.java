package com.exampleApi.employee_management.services;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.exampleApi.employee_management.abstracts.EmployeeService;
import com.exampleApi.employee_management.dtos.EmployeeCreate;
import com.exampleApi.employee_management.dtos.EmployeeUpdate;
import com.exampleApi.employee_management.entities.Department;
import com.exampleApi.employee_management.entities.Employee;
import com.exampleApi.employee_management.repositories.DepartmentRepo;
import com.exampleApi.employee_management.repositories.EmployeeRepo;
import com.exampleApi.employee_management.shared.CustomResponseException;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeRepo employeeRepo;

    @Autowired
    private DepartmentRepo departmentRepo;

    @Autowired
    private EmailService emailService;


    @PreAuthorize("@securityUtils.isOwner(#employeeId)")
    public Employee findOne(UUID employeeId) {
        Employee employee = employeeRepo.findById(employeeId)
                .orElseThrow(() -> CustomResponseException
                        .ResourceNotFound("Employee with ID: " + employeeId + " not found"));

        return employee;
    }

    public List<Employee> findAll() {
        return employeeRepo.findAll();
    }

    public void deleteOne(UUID employeeId) {
        Optional<Employee> employee = employeeRepo.findById(employeeId);

        employee.ifPresent(value -> employeeRepo.deleteById(value.getId()));
    }

    @PreAuthorize("@securityUtils.isOwner(#employeeId)")
    public Employee updateOne(UUID employeeId, EmployeeUpdate employee) {
        Employee existEmployee = employeeRepo.findById(employeeId)
                .orElseThrow(
                        () -> CustomResponseException.ResourceNotFound("Employee not found with ID: " + employeeId));

        existEmployee.setFirstName(employee.firstName());
        existEmployee.setLastName(employee.lastName());
        existEmployee.setPhoneNumber(employee.phoneNumber());
        existEmployee.setPosition(employee.position());

        return existEmployee;
    }


    @Transactional
    public Employee createOne(EmployeeCreate employeeCreate) {
        Employee employee = new Employee();

        Department department = departmentRepo.findById(employeeCreate.departmentId())
                .orElseThrow(() -> CustomResponseException
                        .ResourceNotFound("Department with ID: " + employeeCreate.departmentId() + " not found"));
                        
        String token = UUID.randomUUID().toString();
        employee.setVerified(false);
        employee.setAccountCreationToken(token);

        employee.setFirstName(employeeCreate.firstName());
        employee.setLastName(employeeCreate.lastName());
        employee.setPosition(employeeCreate.position());
        employee.setPhoneNumber(employeeCreate.phoneNumber());
        employee.setHireDate(employeeCreate.hireDate());
        employee.setEmail(employeeCreate.email());
        employee.setDepartment(department);

        employeeRepo.save(employee);

        emailService.sendAccountCreationEmail(employeeCreate.email(), token);

        return employee;
    }
}
