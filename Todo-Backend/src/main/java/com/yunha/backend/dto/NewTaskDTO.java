package com.yunha.backend.dto;

import lombok.*;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class NewTaskDTO {
    private String taskContent;
    private LocalDate taskStartDate;
    private LocalDate taskEndDate;
    private boolean taskState;

    private String taskUserName;
    private String taskCategoryName;

    public NewTaskDTO(String taskContent, LocalDate taskStartDate, LocalDate taskEndDate, String taskUserName, String taskCategoryName) {
        this.taskContent = taskContent;
        this.taskStartDate = taskStartDate;
        this.taskEndDate = taskEndDate;
        this.taskState = false;
        this.taskUserName = taskUserName;
        this.taskCategoryName = taskCategoryName;
    }
}
