package com.yunha.backend.repository;

import com.yunha.backend.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {


    @Query(value = "SELECT * \n" +
            "FROM task\n" +
            "WHERE ((YEAR(task_start_date) = ?1 AND MONTH(task_start_date) = ?2)\n" +
            "   OR (YEAR(task_end_date) = ?1 AND MONTH(task_end_date) = ?2)) AND task_user_code = ?3", nativeQuery = true)
    List<Task> findAllByTaskUserCode(int year, int month, Long userCode);

    @Query(value = "SELECT * FROM task WHERE ?1 >= task_start_date AND ?1 <= task_end_date AND task_user_code = ?2", nativeQuery = true)
    List<Task> getTaskOfDay(LocalDate day, Long userCode);
    boolean existsByTaskCode(Long taskCode);
}
