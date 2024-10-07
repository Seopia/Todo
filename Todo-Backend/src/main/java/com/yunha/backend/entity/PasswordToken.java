package com.yunha.backend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "password_token")
@Getter@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PasswordToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "password_token_code")
    private Long passwordTokenCode;
    @JoinColumn(name = "password_token_user_code")
    @ManyToOne
    private User user;
    @Column(name = "password_token_expire_at")
    private LocalDateTime passwordTokenExpireAt;
    @Column(name = "password_token_token")
    private String passwordTokenToken;

    public PasswordToken(User user, LocalDateTime passwordTokenExpireAt, String passwordTokenToken) {
        this.user = user;
        this.passwordTokenExpireAt = passwordTokenExpireAt;
        this.passwordTokenToken = passwordTokenToken;
    }
}
