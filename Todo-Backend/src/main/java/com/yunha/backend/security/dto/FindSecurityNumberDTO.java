package com.yunha.backend.security.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class FindSecurityNumberDTO {
    private String token;
    private String userId;
    private String userEmail;
}
