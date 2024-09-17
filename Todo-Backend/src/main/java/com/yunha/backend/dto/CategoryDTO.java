package com.yunha.backend.dto;


import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class CategoryDTO {

    private Long categoryCode;
    private String categoryName;

    private String categoryUserId;
    private boolean categoryIsPrivate;

}
