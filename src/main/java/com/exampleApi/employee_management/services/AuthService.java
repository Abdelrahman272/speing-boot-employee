package com.exampleApi.employee_management.services;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.exampleApi.employee_management.config.JwtHelper;
import com.exampleApi.employee_management.dtos.LoginRequest;
import com.exampleApi.employee_management.dtos.ResetPasswordRequest;
import com.exampleApi.employee_management.dtos.SignUpRequest;
import com.exampleApi.employee_management.entities.Employee;
import com.exampleApi.employee_management.entities.PasswordResetToken;
import com.exampleApi.employee_management.entities.UserAccount;
import com.exampleApi.employee_management.repositories.EmployeeRepo;
import com.exampleApi.employee_management.repositories.PasswordRestRepo;
import com.exampleApi.employee_management.repositories.UserAccountRepo;
import com.exampleApi.employee_management.shared.CustomResponseException;

import jakarta.transaction.Transactional;

@Service
public class AuthService {
  @Autowired
  private UserAccountRepo userAccountRepo;
  @Autowired
  private EmployeeRepo employeeRepo;
  @Autowired
  private PasswordRestRepo passwordRestRepo;
  @Autowired
  private EmailService emailService;

  @Autowired
  private PasswordEncoder passwordEncoder;
  @Autowired
  private AuthenticationManager authenticationManager;
  @Autowired
  private JwtHelper jwtHelper;

  public void signup(SignUpRequest signupRequest, String token) {
    Employee employee = employeeRepo.findOneByAccountCreationToken(token)
        .orElseThrow(() -> CustomResponseException.ResourceNotFound("Invalid token"));

    if (employee.isVerified()) {
      throw CustomResponseException.BadRequest("Account already created");
    }

    UserAccount account = new UserAccount();
    account.setUsername(signupRequest.username());
    account.setPassword(passwordEncoder.encode(signupRequest.password()));
    account.setEmployee(employee);
    userAccountRepo.save(account);

    employee.setVerified(true);
    employee.setAccountCreationToken(null);
    employeeRepo.save(employee);
  }


  public String login(LoginRequest loginRequest) {
    authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(
            loginRequest.username(),
            loginRequest.password()
        )
    );

    UserAccount user = userAccountRepo.findOneByUsername(loginRequest.username())
        .orElseThrow(() -> CustomResponseException.BadCredentials());

    Map<String, Object> customClaims = new HashMap<>();
    customClaims.put("userId", user.getId());
    return jwtHelper.generateToken(customClaims, user);
  }

  @Transactional
  public void initiatePasswordRest(String username) {
    try {
      UserAccount user = userAccountRepo.findOneByUsername(username)
          .orElseThrow(() -> CustomResponseException.ResourceNotFound("Account not found"));

      String token = UUID.randomUUID().toString();
      LocalDateTime expiry = LocalDateTime.now().plusMinutes(15);

      PasswordResetToken resetToken = new PasswordResetToken();
      resetToken.setToken(token);
      resetToken.setExpiryDate(expiry);
      resetToken.setUser(user);

      passwordRestRepo.save(resetToken);

      emailService.sendPasswordRestEmail(user.getEmployee().getEmail(), token);
    } catch (Exception e) {
      throw CustomResponseException.BadRequest("Sending reset password email failed");
    }
  }


  public void resetPassword(ResetPasswordRequest resetPasswordRequest) {
    PasswordResetToken resetToken = passwordRestRepo.findOneByToken(resetPasswordRequest.token())
        .orElseThrow(() -> CustomResponseException.BadRequest("Invalid token"));

    boolean isTokenExpired = resetToken.getExpiryDate().isBefore(LocalDateTime.now());
    if (isTokenExpired) {
      passwordRestRepo.delete(resetToken);
      throw CustomResponseException.BadRequest("Token has expired, request a new one");
    }

    UserAccount user = resetToken.getUser();
    user.setPassword(passwordEncoder.encode(resetPasswordRequest.newPassword()));
    userAccountRepo.save(user);
    passwordRestRepo.delete(resetToken);
  }
}