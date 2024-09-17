package com.yunha.backend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "task")
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // auto increment
    @Column(name = "task_code")
    private Long taskCode;

    @Column(name = "task_content")
    private String taskContent;

    @Column(name = "task_start_date")
    private LocalDate taskStartDate;

    @Column(name = "task_end_date")
    private LocalDate taskEndDate;

    @Column(name = "task_state")
    private boolean taskState;

    @JoinColumn(name = "task_user_code")
    @ManyToOne
    private User taskUser;

    @JoinColumn(name = "task_category_code")
    @ManyToOne
    private Category taskCategory;

}
