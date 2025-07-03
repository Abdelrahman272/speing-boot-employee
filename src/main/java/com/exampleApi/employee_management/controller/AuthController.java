package com.exampleApi.employee_management.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.exampleApi.employee_management.dtos.LoginRequest;
import com.exampleApi.employee_management.dtos.SignUpRequest;
import com.exampleApi.employee_management.services.AuthService;
import com.exampleApi.employee_management.shared.GlobalResponse;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<GlobalResponse<String>> login(@RequestBody LoginRequest loginRequest) {

        authService.login(loginRequest);

        String token = authService.login(loginRequest);

        return new ResponseEntity<>(
                new GlobalResponse<>(token), HttpStatus.CREATED);
    }

    @PostMapping("/singup")
    public ResponseEntity<GlobalResponse<String>> signup(
            @RequestBody SignUpRequest signupRequest,
            @RequestParam String token) {

        authService.signup(signupRequest, token);
        return new ResponseEntity<>(new GlobalResponse<>("Signed Up"), HttpStatus.CREATED);
    }

}
