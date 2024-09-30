package com.yunha.backend.dto;

import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Setter@Getter
@ToString
public class ChatDTO {
    private Long userCode;
    private String userNickname;
    private String message;
    private LocalDateTime chatTime;
}
