package com.yunha.backend.controller;


import com.yunha.backend.dto.ResponseDTO;
import com.yunha.backend.dto.TaskDTO;
import com.yunha.backend.dto.TaskDayDTO;
import com.yunha.backend.entity.Category;
import com.yunha.backend.entity.Task;
import com.yunha.backend.repository.TaskRepository;
import com.yunha.backend.security.dto.CustomUserDetails;
import com.yunha.backend.service.TaskService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;


@RestController
@RequestMapping("todo")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    // task 할 일 리스트 보기
    @GetMapping("/tasks")
    public ResponseEntity<?> getMyTasks(@RequestParam LocalDate calendarDate, @AuthenticationPrincipal CustomUserDetails user){
        try{
            List<TaskDTO> myTaskList = taskService.getMyTaskList(calendarDate, user.getUserCode());

            return ResponseEntity.ok().body(myTaskList);

        }catch (Exception e){

            return ResponseEntity.badRequest().body("에러");
        }

    }
    @GetMapping("/tasks/day")
    public ResponseEntity<?> getTaskOfDay(@RequestParam LocalDate day, @AuthenticationPrincipal CustomUserDetails user){
        try{
            List<TaskDayDTO> myTaskList = taskService.getTaskOfDay(day, user.getUserCode());
            return ResponseEntity.ok().body(new ResponseDTO("success",myTaskList));
        } catch (Exception e){
            return ResponseEntity.badRequest().body("fail");
        }
    }



    @PostMapping("/tasks")
    public ResponseEntity<String> createMyTask(@ModelAttribute TaskDTO newTaskDTO, @AuthenticationPrincipal CustomUserDetails user){

        try{
            String result = taskService.createMyTask(newTaskDTO, user.getUserCode());
            return ResponseEntity.ok().body(result);

        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.badRequest().body("할 일 등록 실패");

        }

    }
    @DeleteMapping("/tasks/{taskCode}")
    public ResponseEntity<String> removeMyTask(@PathVariable Long taskCode){
        try{
            taskService.removeMyTask(taskCode);
            return ResponseEntity.ok().body("삭제 성공");
        } catch (Exception e){
            return ResponseEntity.ok().body(e.getMessage());
        }
    }

}
