package com.yunha.backend.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter@Setter@ToString
public class FindAccountDTO {
    private String userId;
    private String userEmail;
}
