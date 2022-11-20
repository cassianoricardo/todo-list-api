package br.com.itau.todo.list.api.service;

import br.com.itau.todo.list.api.AbstractTodoListTest;
import br.com.itau.todo.list.api.controller.request.TaskCreateRequest;
import br.com.itau.todo.list.api.controller.response.TaskResponse;
import br.com.itau.todo.list.api.enums.StatusTaskEnum;
import br.com.itau.todo.list.api.model.Task;
import br.com.itau.todo.list.api.model.User;
import br.com.itau.todo.list.api.repository.TaskRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Calendar;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TaskServiceTest extends AbstractTodoListTest {

    @InjectMocks
    TaskService taskService;

    @Mock
    TaskRepository taskRepository;

    @Test
    @DisplayName("createTask :: success")
    void createTask() {
        var taskCreateRequest = TaskCreateRequest.builder()
                .status(StatusTaskEnum.PENDING)
                .summary("Summary test")
                .description("Description Test")
                .build();

        try (MockedStatic<UserLoggedService> userLoggedServiceStatic = Mockito.mockStatic(UserLoggedService.class)) {
            var user = new User();

            userLoggedServiceStatic.when(UserLoggedService::getUserAuthenticated).thenReturn(user);

            when(taskRepository.save(any()))
                    .thenReturn(Task.builder()
                            .id(1L)
                            .user(user)
                            .dateCreated(Calendar.getInstance())
                            .dateLastUpdate(Calendar.getInstance())
                            .status(StatusTaskEnum.PENDING)
                            .summary("test")
                            .description("test")
                            .build());

            taskService.createTask(taskCreateRequest);

            userLoggedServiceStatic.verify(UserLoggedService::getUserAuthenticated);
            verify(taskRepository).save(any());
        }
    }

    @Test
    @DisplayName("getAllTasks :: success")
    void getAllTasks() {

        List<Task> tasks = List.of(Task.builder()
                        .id(1L)
                        .user(User.builder().id(1L).email("test@test.com").build())
                        .dateCreated(Calendar.getInstance())
                        .dateLastUpdate(Calendar.getInstance())
                        .status(StatusTaskEnum.PENDING)
                        .summary("test")
                        .description("test")
                        .build(),

                Task.builder()
                        .id(2L)
                        .user(User.builder().id(2L).email("test@test.com").build())
                        .dateCreated(Calendar.getInstance())
                        .dateLastUpdate(Calendar.getInstance())
                        .status(StatusTaskEnum.PENDING)
                        .summary("test")
                        .description("test")
                        .build());

        var taskResponseList = List.of(TaskResponse.builder()
                        .id(1L)
                        .dateCreated(Calendar.getInstance())
                        .dateLastUpdate(Calendar.getInstance())
                        .status(StatusTaskEnum.PENDING)
                        .summary("test")
                        .description("test")
                        .build(),
                TaskResponse.builder()
                        .id(2L)
                        .dateCreated(Calendar.getInstance())
                        .dateLastUpdate(Calendar.getInstance())
                        .status(StatusTaskEnum.PENDING)
                        .summary("test")
                        .description("test")
                        .build());

        when(taskRepository.findAll()).thenReturn(tasks);

        Assertions.assertEquals(taskResponseList, taskService.getAllTasks());

        verify(taskRepository).findAll();
    }

    @Test
    @DisplayName("getTaskByUserAndStatus :: task not found without status")
    void getTaskByUserAndStatus_without_status_task_not_found() {

        var user = new User();
        when(taskRepository.findByUserOrderByStatusDesc(user)).thenReturn(Optional.empty());

        try (MockedStatic<UserLoggedService> userLoggedServiceStatic = Mockito.mockStatic(UserLoggedService.class)) {

            userLoggedServiceStatic.when(UserLoggedService::getUserAuthenticated).thenReturn(user);

            RuntimeException exception = assertThrows(RuntimeException.class, () -> taskService.getTaskByUserAndStatus(Optional.empty()));
            Assertions.assertEquals("Task not found", exception.getMessage());

            userLoggedServiceStatic.verify(UserLoggedService::getUserAuthenticated);
        }

        verify(taskRepository).findByUserOrderByStatusDesc(eq(user));
    }

    @Test
    @DisplayName("getTaskByUserAndStatus :: task with status success")
    void getTaskByUserAndStatus_with_status_success() {

        var user = new User();
        var status = Optional.of(StatusTaskEnum.PENDING);

        var now = Calendar.getInstance();

        List<Task> tasks = List.of(Task.builder()
                        .id(1L)
                        .user(User.builder().id(1L).email("test@test.com").build())
                        //.dateCreated(now)
                        //.dateLastUpdate(now)
                        .status(StatusTaskEnum.PENDING)
                        .summary("test")
                        .description("test")
                        .build(),

                Task.builder()
                        .id(2L)
                        .user(User.builder().id(2L).email("test@test.com").build())
                        //.dateCreated(now)
                        //.dateLastUpdate(now)
                        .status(StatusTaskEnum.PENDING)
                        .summary("test")
                        .description("test")
                        .build());

        when(taskRepository.findByStatusAndUserOrderByStatusDesc(eq(status.get()),eq(user))).thenReturn(Optional.of(tasks));

        var taskResponseList = List.of(TaskResponse.builder()
                        .id(1L)
                        //.dateCreated(Calendar.getInstance())
                        //.dateLastUpdate(Calendar.getInstance())
                        .status(StatusTaskEnum.PENDING)
                        .summary("test")
                        .description("test")
                        .build(),
                TaskResponse.builder()
                        .id(2L)
                        //.dateCreated(Calendar.getInstance())
                        //.dateLastUpdate(Calendar.getInstance())
                        .status(StatusTaskEnum.PENDING)
                        .summary("test")
                        .description("test")
                        .build());

        try (MockedStatic<UserLoggedService> userLoggedServiceStatic = Mockito.mockStatic(UserLoggedService.class)) {

            userLoggedServiceStatic.when(UserLoggedService::getUserAuthenticated).thenReturn(user);

            Assertions.assertEquals(taskResponseList, taskService.getTaskByUserAndStatus(status));

            userLoggedServiceStatic.verify(UserLoggedService::getUserAuthenticated);
        }

        verify(taskRepository).findByStatusAndUserOrderByStatusDesc(eq(status.get()),eq(user));
    }

    @Test
    @DisplayName("getTaskByUserAndStatus :: task not found with status")
    void getTaskByUserAndStatus_without_status_not_found() {

        var user = new User();
        var status = Optional.of(StatusTaskEnum.PENDING);

        when(taskRepository.findByStatusAndUserOrderByStatusDesc(eq(status.get()),eq(user))).thenReturn(Optional.empty());

        try (MockedStatic<UserLoggedService> userLoggedServiceStatic = Mockito.mockStatic(UserLoggedService.class)) {

            userLoggedServiceStatic.when(UserLoggedService::getUserAuthenticated).thenReturn(user);

            RuntimeException exception = assertThrows(RuntimeException.class, () -> taskService.getTaskByUserAndStatus(status));
            Assertions.assertEquals("Task not found", exception.getMessage());

            userLoggedServiceStatic.verify(UserLoggedService::getUserAuthenticated);
        }

        verify(taskRepository).findByStatusAndUserOrderByStatusDesc(eq(status.get()),eq(user));
    }

    @Test
    @DisplayName("getTaskByUserAndStatus :: task without status success")
    void getTaskByUserAndStatus_without_status_success() {

        var user = new User();

        var now = Calendar.getInstance();

        List<Task> tasks = List.of(Task.builder()
                        .id(1L)
                        .user(User.builder().id(1L).email("test@test.com").build())
                        //.dateCreated(now)
                        //.dateLastUpdate(now)
                        .status(StatusTaskEnum.PENDING)
                        .summary("test")
                        .description("test")
                        .build(),

                Task.builder()
                        .id(2L)
                        .user(User.builder().id(2L).email("test@test.com").build())
                        //.dateCreated(now)
                        //.dateLastUpdate(now)
                        .status(StatusTaskEnum.PENDING)
                        .summary("test")
                        .description("test")
                        .build());

        when(taskRepository.findByUserOrderByStatusDesc(eq(user))).thenReturn(Optional.of(tasks));

        var taskResponseList = List.of(TaskResponse.builder()
                        .id(1L)
                        //.dateCreated(Calendar.getInstance())
                        //.dateLastUpdate(Calendar.getInstance())
                        .status(StatusTaskEnum.PENDING)
                        .summary("test")
                        .description("test")
                        .build(),
                TaskResponse.builder()
                        .id(2L)
                        //.dateCreated(Calendar.getInstance())
                        //.dateLastUpdate(Calendar.getInstance())
                        .status(StatusTaskEnum.PENDING)
                        .summary("test")
                        .description("test")
                        .build());

        try (MockedStatic<UserLoggedService> userLoggedServiceStatic = Mockito.mockStatic(UserLoggedService.class)) {

            userLoggedServiceStatic.when(UserLoggedService::getUserAuthenticated).thenReturn(user);

            Assertions.assertEquals(taskResponseList, taskService.getTaskByUserAndStatus(Optional.empty()));

            userLoggedServiceStatic.verify(UserLoggedService::getUserAuthenticated);
        }

        verify(taskRepository).findByUserOrderByStatusDesc(eq(user));
    }
}