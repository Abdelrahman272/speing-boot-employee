package com.exampleApi.employee_management.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.exampleApi.employee_management.entities.PasswordResetToken;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface PasswordRestRepo extends JpaRepository<PasswordResetToken, UUID> {
  Optional<PasswordResetToken> findOneByToken(String token);
}
