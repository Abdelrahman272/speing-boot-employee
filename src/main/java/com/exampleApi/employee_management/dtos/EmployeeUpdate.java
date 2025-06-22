package com.exampleApi.employee_management.dtos;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record EmployeeUpdate(
    @NotNull(message = "First Name is required")
    @Size(min = 2, max = 50, message = "First Name must be between 2 and 50 characters")
    String firstName,

    @NotNull(message = "Last Name is required")
    @Size(min = 2, max = 50, message = "Last Name must be between 2 and 50 characters")
    String lastName,

    @NotNull(message = "Phone Number is required")
    @Pattern(regexp = "^\\+?[0-9]{10,15}$", message = "Phone Number must be between 10 and 15 digits")
    String phoneNumber,


    @NotNull(message = "Position is required")
    String position
) {}
