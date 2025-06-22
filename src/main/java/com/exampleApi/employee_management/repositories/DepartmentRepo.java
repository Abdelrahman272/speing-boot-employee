package com.exampleApi.employee_management.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.exampleApi.employee_management.entities.Department;

public interface DepartmentRepo extends JpaRepository<Department, UUID> {    
}
