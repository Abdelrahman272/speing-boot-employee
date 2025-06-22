package com.exampleApi.employee_management.services;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.exampleApi.employee_management.abstracts.DepartmentService;
import com.exampleApi.employee_management.dtos.DepartmentCreate;
import com.exampleApi.employee_management.entities.Department;
import com.exampleApi.employee_management.repositories.DepartmentRepo;
import com.exampleApi.employee_management.shared.CustomResponseException;

@Service
public class DepartmentServiceImpl implements DepartmentService {

    @Autowired
    private DepartmentRepo departmentRepo;

    public Department findOne(UUID departmentId) {
        Department department = departmentRepo.findById(departmentId)
                .orElseThrow(() -> CustomResponseException
                        .ResourceNotFound(" Department with ID: " + departmentId + " not found"));

        return department;
    }

    public List<Department> findAll() {
        return departmentRepo.findAll();
    }

    public Department createOne(DepartmentCreate departmentCreate) {
        Department department = new Department();

        department.setName(departmentCreate.name());

        departmentRepo.save(department);
        return department;
    }

    public void deleteOne(UUID departmentId) {
        departmentRepo.deleteById(departmentId);
    }

}
