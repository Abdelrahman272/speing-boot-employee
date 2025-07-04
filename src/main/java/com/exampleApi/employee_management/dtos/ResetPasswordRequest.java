package com.exampleApi.employee_management.dtos;

public record ResetPasswordRequest(
    String token,
    String newPassword
) {
}
