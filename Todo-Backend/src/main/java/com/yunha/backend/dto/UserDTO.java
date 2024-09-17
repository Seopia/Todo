package com.yunha.backend.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class UserDTO {

    private Long userCode;
    private String userId;
    private String userPw;

}
