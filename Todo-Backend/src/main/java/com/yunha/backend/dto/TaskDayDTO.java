package com.yunha.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@AllArgsConstructor
@Getter@Setter
public class TaskDayDTO {
    private Long taskCode;
    private String taskContent;
    private LocalDate taskStartDate;
    private LocalDate taskEndDate;
    private boolean taskState;
    private String taskCategoryName;
}
