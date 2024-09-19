package com.yunha.backend.repository;

import com.yunha.backend.dto.TaskDTO;
import com.yunha.backend.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {


    @Query("SELECT new com.yunha.backend.dto.TaskDTO(t.taskCode,t.taskContent,t.taskStartDate,t.taskEndDate,t.taskState) FROM Task t " +
            "WHERE ((YEAR(t.taskStartDate) = :year AND MONTH(t.taskStartDate) = :month) " +
            "OR (YEAR(t.taskEndDate) = :year AND MONTH(t.taskEndDate) = :month)) AND t.taskUser.userCode = :userCode")
    List<TaskDTO> findAllByTaskUserCode(int year, int month, Long userCode);

    @Query(value = "SELECT * FROM task WHERE ?1 >= task_start_date AND ?1 <= task_end_date AND task_user_code = ?2", nativeQuery = true)
    List<Task> getTaskOfDay(LocalDate day, Long userCode);
    boolean existsByTaskCode(Long taskCode);
}
