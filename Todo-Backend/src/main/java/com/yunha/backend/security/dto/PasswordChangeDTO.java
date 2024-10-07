package com.yunha.backend.security.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PasswordChangeDTO {
    private String id;
    private String email;
    private String password;
}
