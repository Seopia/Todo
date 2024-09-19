package com.yunha.backend.entity;

import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@ToString
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

    @Column(name = "user_nickname")
    private String userNickname;

    @Column(name = "user_profile_img")
    private String userProfileImg;

    public User(Long userCode) {
        this.userCode = userCode;
    }
}
