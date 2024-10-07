package com.yunha.backend.security.repository;

import com.yunha.backend.entity.PasswordToken;
import com.yunha.backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PasswordTokenRepository extends JpaRepository<PasswordToken, Long> {
    void deleteByUser(User user);

    boolean existsByPasswordTokenTokenAndUser(String token, User user);
}
