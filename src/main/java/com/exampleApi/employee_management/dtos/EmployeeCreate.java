package com.exampleApi.employee_management.dtos;

import java.util.UUID;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record EmployeeCreate(
    @NotNull(message = "First Name is required")
    @Size(min = 2, max = 50, message = "First Name must be between 2 and 50 characters")
    String firstName,

    @NotNull(message = "Last Name is required")
    @Size(min = 2, max = 50, message = "Last Name must be between 2 and 50 characters")
    String lastName,

    @NotNull(message = "Email is required")
    @Email(message = "Email should be valid")
    String email,

    @NotNull(message = "Phone Number is required")
    @Pattern(regexp = "^\\+?[0-9]{10,15}$", message = "Phone Number must be between 10 and 15 digits")
    String phoneNumber,

    @NotNull(message = "Hire Date is required")
    String hireDate,

    @NotNull(message = "Position is required")
    @Size(min = 2, max = 50, message = "Position must be between 2 and 50 characters")
    String position,
    
    @NotNull(message = "Department ID is required")
    UUID departmentId

) {}
