package com.exampleApi.employee_management.dtos;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record LoginRequest(
        @NotNull(message = "Username is required") 
        @Size(min = 2, max = 100, message = "Username must be between 2 and 100 characters") 
        String username,

        @NotNull(message = "Password is required") 
        @Size(min = 6, max = 100, message = "Password must be between 6 and 100 characters") 
        String password
) {}
