package com.yunha.backend.dto;

import lombok.*;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class TaskDTO {

    private Long taskCode;
    private String taskContent;
    private LocalDate taskStartDate;
    private LocalDate taskEndDate;
    private boolean taskState;

    private String taskUserName;
    private String taskCategoryName;
}
