package com.yunha.backend.dto;

import lombok.*;

import org.springframework.http.HttpStatus;


@NoArgsConstructor
@Getter
@Setter
@ToString
public class ResponseDTO {

    private String message; // 응답메시지
    private Object data; // 응답데이터

    public ResponseDTO(String message, Object data) {
        this.message = message;
        this.data = data;
    }

}
