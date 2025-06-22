package com.exampleApi.employee_management.abstracts;

import java.util.List;
import java.util.UUID;

import com.exampleApi.employee_management.dtos.DepartmentCreate;
import com.exampleApi.employee_management.entities.Department;

public interface DepartmentService {

    Department findOne(UUID  departmentId);

    List<Department> findAll();

    Department createOne(DepartmentCreate  departmentCreate);

    void deleteOne(UUID departmentId);
}
