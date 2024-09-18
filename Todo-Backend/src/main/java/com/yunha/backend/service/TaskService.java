package com.yunha.backend.service;

import com.yunha.backend.dto.TaskDTO;
import com.yunha.backend.dto.TaskDayDTO;
import com.yunha.backend.entity.Category;
import com.yunha.backend.entity.Task;
import com.yunha.backend.entity.User;
import com.yunha.backend.repository.TaskRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class TaskService {

    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }


    @Transactional
    public String createMyTask(TaskDTO newTaskDTO, Long userCode) {
        try{
            Task task = taskRepository.findById(newTaskDTO.getTaskCode()).orElseThrow();
            task.setTaskContent(newTaskDTO.getTaskContent());
            taskRepository.save(task);
            return "성공";

        } catch (Exception e){
            Task newTask = new Task(
                    newTaskDTO.getTaskCode(),
                    newTaskDTO.getTaskContent(),
                    newTaskDTO.getTaskStartDate(),
                    newTaskDTO.getTaskEndDate(),
                    false,
                    new User(userCode),
                    new Category(1L)
            );

            taskRepository.save(newTask);
            return "할 일 성공";
        }
    }


    @Transactional
    public void removeMyTask(Long taskCode) throws Exception {
        try{
            taskRepository.deleteById(taskCode);
        } catch (EntityNotFoundException e){
            throw new EntityNotFoundException("존재하지 않는 데이터입니다.");
        } catch (Exception e){
            throw new Exception("알 수 없는 에러입니다.");
        }
    }

    public List<TaskDTO> getMyTaskList(LocalDate calendarDate, Long userCode) {

        try {

            System.out.println("calendarDate : " + calendarDate);
            int year = calendarDate.getYear();
            int month = calendarDate.getMonthValue();

            List<Task> myTaskList = taskRepository.findAllByTaskUserCode(year, month, userCode);
            System.out.println("할일 myTasks : " + myTaskList);

            List<TaskDTO> taskDTOList = new ArrayList<>();
            //dto로 변환
            for(Task task: myTaskList){
                TaskDTO taskDTO = new TaskDTO(
                        task.getTaskCode(),
                        task.getTaskContent(),
                        task.getTaskStartDate(),
                        task.getTaskEndDate(),
                        task.isTaskState(),
                        task.getTaskUser().getUserId(),
                        task.getTaskCategory().getCategoryName()
                );
                taskDTOList.add(taskDTO);
            }
            System.out.println(taskDTOList);
            return taskDTOList;

        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public List<TaskDayDTO> getTaskOfDay(LocalDate day, Long userCode) {
        List<Task> entity = taskRepository.getTaskOfDay(day, userCode);
        List<TaskDayDTO> dtos = new ArrayList<>();
        try{
            for(Task t : entity){
                TaskDayDTO dto = new TaskDayDTO(
                        t.getTaskCode(),
                        t.getTaskContent(),
                        t.getTaskStartDate(),
                        t.getTaskEndDate(),
                        t.isTaskState(),
                        t.getTaskCategory().getCategoryName()
                );
                dtos.add(dto);
            }
            System.out.println("dtos = " + dtos);
            return dtos;
        } catch (Exception e){
            return null;
        }
    }
}
