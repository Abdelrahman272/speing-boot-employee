package com.exampleApi.employee_management.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.exampleApi.employee_management.abstracts.DepartmentService;
import com.exampleApi.employee_management.dtos.DepartmentCreate;
import com.exampleApi.employee_management.entities.Department;
import com.exampleApi.employee_management.shared.GlobalResponse;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/departments")
public class DepartmentController {

    @Autowired
    private DepartmentService departmentService;

    @GetMapping
    public ResponseEntity<GlobalResponse<List<Department>>> findAll() {

        List<Department> departments = departmentService.findAll();

        return new ResponseEntity<>(new GlobalResponse<>(departments), HttpStatus.OK);
    }

    @GetMapping("/{departmentId}")
    public ResponseEntity<GlobalResponse<Department>> findOne(@PathVariable UUID departmentId) {

        Department department = departmentService.findOne(departmentId);
        return new ResponseEntity<>(new GlobalResponse<>(department), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<GlobalResponse<Department>> createOne(@RequestBody DepartmentCreate departmentCreate) {

        Department department = departmentService.createOne(departmentCreate);

        return new ResponseEntity<>(new GlobalResponse<>(department), HttpStatus.CREATED);
    }

    @DeleteMapping("/{departmentId}")
    public ResponseEntity<Object> deleteOne(@PathVariable UUID departmentId) {

        departmentService.deleteOne(departmentId);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
