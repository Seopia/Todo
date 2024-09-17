package com.yunha.backend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @Column(name = "user_code")
    private Long userCode;

    @Column(name = "user_id")
    private String userId;

    @Column(name = "user_pw")
    private String userPw;

    @Column(name = "user_role")
    private String userRole;

    public User(Long userCode) {
        this.userCode = userCode;
    }
}
