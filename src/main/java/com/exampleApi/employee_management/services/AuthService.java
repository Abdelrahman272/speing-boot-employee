package com.exampleApi.employee_management.services;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.exampleApi.employee_management.config.JwtHelper;
import com.exampleApi.employee_management.dtos.LoginRequest;
import com.exampleApi.employee_management.dtos.SignUpRequest;
import com.exampleApi.employee_management.entities.Employee;
import com.exampleApi.employee_management.entities.UserAccount;
import com.exampleApi.employee_management.repositories.EmployeeRepo;
import com.exampleApi.employee_management.repositories.UserAccountRepo;
import com.exampleApi.employee_management.shared.CustomResponseException;

@Service
public class AuthService {

    @Autowired
    private UserAccountRepo userAccountRepo;

    @Autowired
    private EmployeeRepo employeeRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtHelper jwtHelper;

    public void signup(SignUpRequest signUpRequest) {

        Employee employee = employeeRepo.findById(signUpRequest.employeeId())
                .orElseThrow(() -> CustomResponseException
                        .ResourceNotFound("Employee not found with ID: " + signUpRequest.employeeId()));

        UserAccount account = new UserAccount();
        account.setUsername(signUpRequest.username());
        account.setPassword(passwordEncoder.encode(signUpRequest.password()));
        account.setEmployee(employee);

        userAccountRepo.save(account);
    }

    public String login(LoginRequest loginRequest) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.username(),
                        loginRequest.password()));

        UserAccount user = userAccountRepo.findOneByUsername(loginRequest.username())
                .orElseThrow(() -> CustomResponseException.BadCredentials());

        Map<String, Object> customClaims = new HashMap<>();
        customClaims.put("userId", user.getId());
        return jwtHelper.generateToken(customClaims, user);
    }
}
