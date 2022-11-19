package br.com.itau.todo.list.api.service;

import br.com.itau.todo.list.api.controller.request.TaskCreateRequest;
import br.com.itau.todo.list.api.controller.response.TaskResponse;
import br.com.itau.todo.list.api.enums.StatusTaskEnum;
import br.com.itau.todo.list.api.model.entity.Task;
import br.com.itau.todo.list.api.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static br.com.itau.todo.list.api.service.UserLoggedService.getUserAuthenticated;

@Service
public class TaskService {

    @Autowired
    TaskRepository taskRepository;

    public void createTask(TaskCreateRequest taskCreateRequest) {

        var task = Task.builder()
            .user(getUserAuthenticated())
            .dateCreated(Calendar.getInstance())
            .dateLastUpdate(Calendar.getInstance())
            .summary(taskCreateRequest.getSummary())
            .description(taskCreateRequest.getDescription())
            .status(taskCreateRequest.getStatus())
            .build();

        taskRepository.save(task);
    }

    public List<TaskResponse> getAllTasks(){
        List<Task> tasks = taskRepository.findAll();
        return tasks.stream()
                    .map(task -> TaskResponse.builder().id(task.getId())
                    .dateCreated(task.getDateCreated())
                    .dateLastUpdate(task.getDateLastUpdate())
                    .summary(task.getSummary())
                    .description(task.getDescription())
                    .user(task.getUser().getUsername())
                    .status(task.getStatus())
                    .build()).collect(Collectors.toList());
    }

    public List<TaskResponse> getTaskByUserAndStatus(Optional<StatusTaskEnum> status) {
        final String TASK_NOT_FOUND = "Task not found";

        var user = getUserAuthenticated();
        List<Task> tasks;
        if(status.isEmpty()) {
            tasks = taskRepository.findByUser(user).
                    orElseThrow(() -> new RuntimeException(TASK_NOT_FOUND));
        }else{
            tasks = taskRepository.findByStatusAndUser(status.get(),user)
                    .orElseThrow(() -> new RuntimeException(TASK_NOT_FOUND));;
        }
        return tasks.stream().map(task -> TaskResponse.builder().id(task.getId())
                                .summary(task.getSummary())
                                .description(task.getDescription())
                                .status(task.getStatus())
                                .dateLastUpdate(task.getDateLastUpdate())
                                .dateCreated(task.getDateCreated())
                                .build()).collect(Collectors.toList());

    }


}
