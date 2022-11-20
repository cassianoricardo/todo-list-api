package br.com.itau.todo.list.api.service;

import br.com.itau.todo.list.api.AbstractTodoListTest;
import br.com.itau.todo.list.api.controller.request.TodoCreateRequest;
import br.com.itau.todo.list.api.controller.response.TaskResponse;
import br.com.itau.todo.list.api.enums.StatusTaskEnum;
import br.com.itau.todo.list.api.exception.NotFoundException;
import br.com.itau.todo.list.api.model.Role;
import br.com.itau.todo.list.api.model.Todo;
import br.com.itau.todo.list.api.model.User;
import br.com.itau.todo.list.api.repository.TodoRepository;
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
class TodoServiceTest extends AbstractTodoListTest {

    @InjectMocks
    TodoService todoService;

    @Mock
    TodoRepository todoRepository;

    @Test
    @DisplayName("createTodo :: success")
    void createTodo() {
        var taskCreateRequest = TodoCreateRequest.builder()
                .status(StatusTaskEnum.PENDING)
                .summary("Summary test")
                .description("Description Test")
                .build();

        try (MockedStatic<UserLoggedService> userLoggedServiceStatic = Mockito.mockStatic(UserLoggedService.class)) {
            var user = new User();

            userLoggedServiceStatic.when(UserLoggedService::getUserAuthenticated).thenReturn(user);

            when(todoRepository.save(any()))
                    .thenReturn(Todo.builder()
                            .id(1L)
                            .user(user)
                            .dateCreated(Calendar.getInstance())
                            .dateLastUpdate(Calendar.getInstance())
                            .status(StatusTaskEnum.PENDING)
                            .summary("test")
                            .description("test")
                            .build());

            todoService.createTask(taskCreateRequest);

            userLoggedServiceStatic.verify(UserLoggedService::getUserAuthenticated);
            verify(todoRepository).save(any());
        }
    }


    @Test
    @DisplayName("updateTodo :: success using user with role USER")
    void updateTodo_success_role_user() {
        var taskCreateRequest = TodoCreateRequest.builder()
                .status(StatusTaskEnum.PENDING)
                .summary("Summary test")
                .description("Description Test")
                .build();

        try (MockedStatic<UserLoggedService> userLoggedServiceStatic = Mockito.mockStatic(UserLoggedService.class)) {
            var user = User.builder().roles(List.of(new Role(1L,"USER"))).build();

            userLoggedServiceStatic.when(UserLoggedService::getUserAuthenticated).thenReturn(user);
            var id = Long.valueOf(1);

            when(todoRepository.findByIdAndUser(eq(id), eq(user)))
                    .thenReturn(Optional.of(Todo.builder()
                            .id(1L)
                            .user(user)
                            .dateCreated(Calendar.getInstance())
                            .dateLastUpdate(Calendar.getInstance())
                            .status(StatusTaskEnum.PENDING)
                            .summary("test")
                            .description("test")
                            .build()));


            todoService.updateTodo(id, taskCreateRequest);

            userLoggedServiceStatic.verify(UserLoggedService::getUserAuthenticated);
            verify(todoRepository).findByIdAndUser(eq(id), eq(user));
        }
        verify(todoRepository).save(any());
    }

    @Test
    @DisplayName("updateTodo :: success using user with role ADMIN")
    void updateTodo_success_role_admin() {
        var taskCreateRequest = TodoCreateRequest.builder()
                .status(StatusTaskEnum.PENDING)
                .summary("Summary test")
                .description("Description Test")
                .build();

        try (MockedStatic<UserLoggedService> userLoggedServiceStatic = Mockito.mockStatic(UserLoggedService.class)) {
            var user = User.builder().roles(List.of(new Role(1L,"ADMIN"))).build();

            userLoggedServiceStatic.when(UserLoggedService::getUserAuthenticated).thenReturn(user);
            var id = Long.valueOf(1);

            when(todoRepository.findById(eq(id)))
                    .thenReturn(Optional.of(Todo.builder()
                            .id(1L)
                            .user(user)
                            .dateCreated(Calendar.getInstance())
                            .dateLastUpdate(Calendar.getInstance())
                            .status(StatusTaskEnum.PENDING)
                            .summary("test")
                            .description("test")
                            .build()));


            todoService.updateTodo(id, taskCreateRequest);

            userLoggedServiceStatic.verify(UserLoggedService::getUserAuthenticated);
            verify(todoRepository).findById(eq(id));
        }
        verify(todoRepository).save(any());
    }


    @Test
    @DisplayName("updateTodo :: todo not found using user with role USER")
    void updateTodo_todo_not_found_role_user() {
        var taskCreateRequest = TodoCreateRequest.builder()
                .status(StatusTaskEnum.PENDING)
                .summary("Summary test")
                .description("Description Test")
                .build();

        try (MockedStatic<UserLoggedService> userLoggedServiceStatic = Mockito.mockStatic(UserLoggedService.class)) {
            var user = User.builder().roles(List.of(new Role(1L,"USER"))).build();

            userLoggedServiceStatic.when(UserLoggedService::getUserAuthenticated).thenReturn(user);
            var id = Long.valueOf(1);

            when(todoRepository.findByIdAndUser(eq(id), eq(user)))
                    .thenReturn(Optional.empty());

            NotFoundException exception = assertThrows(NotFoundException.class, () -> todoService.updateTodo(id, taskCreateRequest));
            Assertions.assertEquals(exception.getMessage(), "todo not found");

            userLoggedServiceStatic.verify(UserLoggedService::getUserAuthenticated);
            verify(todoRepository).findByIdAndUser(eq(id), eq(user));
        }
    }

    @Test
    @DisplayName("updateTodo :: todo not found using user with role ADMIN")
    void updateTodo_todo_not_found_role_admin() {
        var taskCreateRequest = TodoCreateRequest.builder()
                .status(StatusTaskEnum.PENDING)
                .summary("Summary test")
                .description("Description Test")
                .build();

        try (MockedStatic<UserLoggedService> userLoggedServiceStatic = Mockito.mockStatic(UserLoggedService.class)) {
            var user = User.builder().roles(List.of(new Role(1L,"ADMIN"))).build();

            userLoggedServiceStatic.when(UserLoggedService::getUserAuthenticated).thenReturn(user);
            var id = Long.valueOf(1);

            when(todoRepository.findById(eq(id)))
                    .thenReturn(Optional.empty());

            NotFoundException exception = assertThrows(NotFoundException.class, () -> todoService.updateTodo(id, taskCreateRequest));
            Assertions.assertEquals(exception.getMessage(), "todo not found");

            userLoggedServiceStatic.verify(UserLoggedService::getUserAuthenticated);
            verify(todoRepository).findById(eq(id));
        }
    }

    @Test
    @DisplayName("deleteTodo :: success using user with role USER")
    void removeTodo_success_role_user() {

        try (MockedStatic<UserLoggedService> userLoggedServiceStatic = Mockito.mockStatic(UserLoggedService.class)) {
            var user = User.builder().roles(List.of(new Role(1L,"USER"))).build();

            userLoggedServiceStatic.when(UserLoggedService::getUserAuthenticated).thenReturn(user);
            var id = Long.valueOf(1);

            when(todoRepository.findByIdAndUser(eq(id), eq(user)))
                    .thenReturn(Optional.of(Todo.builder()
                            .id(1L)
                            .user(user)
                            .dateCreated(Calendar.getInstance())
                            .dateLastUpdate(Calendar.getInstance())
                            .status(StatusTaskEnum.PENDING)
                            .summary("test")
                            .description("test")
                            .build()));

            todoService.deleteTodo(id);

            userLoggedServiceStatic.verify(UserLoggedService::getUserAuthenticated);
            verify(todoRepository).findByIdAndUser(eq(id), eq(user));
        }
        verify(todoRepository).delete(any());
    }

    @Test
    @DisplayName("deleteTodo :: success using user with role ADMIN")
    void removeTodo_success_role_admin() {

        try (MockedStatic<UserLoggedService> userLoggedServiceStatic = Mockito.mockStatic(UserLoggedService.class)) {
            var user = User.builder().roles(List.of(new Role(1L,"ADMIN"))).build();

            userLoggedServiceStatic.when(UserLoggedService::getUserAuthenticated).thenReturn(user);
            var id = Long.valueOf(1);

            when(todoRepository.findById(eq(id)))
                    .thenReturn(Optional.of(Todo.builder()
                            .id(1L)
                            .user(user)
                            .dateCreated(Calendar.getInstance())
                            .dateLastUpdate(Calendar.getInstance())
                            .status(StatusTaskEnum.PENDING)
                            .summary("test")
                            .description("test")
                            .build()));

            todoService.deleteTodo(id);

            userLoggedServiceStatic.verify(UserLoggedService::getUserAuthenticated);
            verify(todoRepository).findById(eq(id));
        }
        verify(todoRepository).delete(any());
    }


    @Test
    @DisplayName("removeTodo :: todo not found using user with role USER")
    void removeTodo_todo_not_found_role_user() {

        try (MockedStatic<UserLoggedService> userLoggedServiceStatic = Mockito.mockStatic(UserLoggedService.class)) {
            var user = User.builder().roles(List.of(new Role(1L,"USER"))).build();

            userLoggedServiceStatic.when(UserLoggedService::getUserAuthenticated).thenReturn(user);
            var id = Long.valueOf(1);

            when(todoRepository.findByIdAndUser(eq(id), eq(user)))
                    .thenReturn(Optional.empty());

            NotFoundException exception = assertThrows(NotFoundException.class, () -> todoService.deleteTodo(id));
            Assertions.assertEquals(exception.getMessage(), "todo not found");

            userLoggedServiceStatic.verify(UserLoggedService::getUserAuthenticated);
            verify(todoRepository).findByIdAndUser(eq(id), eq(user));
        }
    }

    @Test
    @DisplayName("removeTodo :: todo not found using user with role ADMIN")
    void removeTodo_todo_not_found_role_admin() {

        try (MockedStatic<UserLoggedService> userLoggedServiceStatic = Mockito.mockStatic(UserLoggedService.class)) {
            var user = User.builder().roles(List.of(new Role(1L,"ADMIN"))).build();

            userLoggedServiceStatic.when(UserLoggedService::getUserAuthenticated).thenReturn(user);
            var id = Long.valueOf(1);

            when(todoRepository.findById(eq(id)))
                    .thenReturn(Optional.empty());

            NotFoundException exception = assertThrows(NotFoundException.class, () -> todoService.deleteTodo(id));
            Assertions.assertEquals(exception.getMessage(), "todo not found");

            userLoggedServiceStatic.verify(UserLoggedService::getUserAuthenticated);
            verify(todoRepository).findById(eq(id));
        }
    }

    @Test
    @DisplayName("getAllTodos :: success")
    void getAllTodos() {

        List<Todo> todos = List.of(Todo.builder()
                        .id(1L)
                        .user(User.builder().id(1L).email("test@test.com").build())
                        .dateCreated(Calendar.getInstance())
                        .dateLastUpdate(Calendar.getInstance())
                        .status(StatusTaskEnum.PENDING)
                        .summary("test")
                        .description("test")
                        .build(),

                Todo.builder()
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

        when(todoRepository.findAll()).thenReturn(todos);

        Assertions.assertEquals(taskResponseList, todoService.getAllTasks());

        verify(todoRepository).findAll();
    }

    @Test
    @DisplayName("getTodoByUserAndStatus :: todo not found without status")
    void getTodoByUserAndStatus_without_status_task_not_found() {

        var user = new User();
        when(todoRepository.findByUserOrderByStatusDesc(user)).thenReturn(Optional.empty());

        try (MockedStatic<UserLoggedService> userLoggedServiceStatic = Mockito.mockStatic(UserLoggedService.class)) {

            userLoggedServiceStatic.when(UserLoggedService::getUserAuthenticated).thenReturn(user);

            RuntimeException exception = assertThrows(RuntimeException.class, () -> todoService.getTodoByUserAndStatus(Optional.empty()));
            Assertions.assertEquals("Todo not found", exception.getMessage());

            userLoggedServiceStatic.verify(UserLoggedService::getUserAuthenticated);
        }

        verify(todoRepository).findByUserOrderByStatusDesc(eq(user));
    }

    @Test
    @DisplayName("getTodoByUserAndStatus :: todo with status success")
    void getTodoByUserAndStatus_with_status_success() {

        var user = new User();
        var status = Optional.of(StatusTaskEnum.PENDING);

        var now = Calendar.getInstance();

        List<Todo> todos = List.of(Todo.builder()
                        .id(1L)
                        .user(User.builder().id(1L).email("test@test.com").build())
                        //.dateCreated(now)
                        //.dateLastUpdate(now)
                        .status(StatusTaskEnum.PENDING)
                        .summary("test")
                        .description("test")
                        .build(),

                Todo.builder()
                        .id(2L)
                        .user(User.builder().id(2L).email("test@test.com").build())
                        //.dateCreated(now)
                        //.dateLastUpdate(now)
                        .status(StatusTaskEnum.PENDING)
                        .summary("test")
                        .description("test")
                        .build());

        when(todoRepository.findByStatusAndUserOrderByStatusDesc(eq(status.get()),eq(user))).thenReturn(Optional.of(todos));

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

            Assertions.assertEquals(taskResponseList, todoService.getTodoByUserAndStatus(status));

            userLoggedServiceStatic.verify(UserLoggedService::getUserAuthenticated);
        }

        verify(todoRepository).findByStatusAndUserOrderByStatusDesc(eq(status.get()),eq(user));
    }

    @Test
    @DisplayName("getTodoByUserAndStatus :: todo not found with status")
    void getTodoByUserAndStatus_without_status_not_found() {

        var user = new User();
        var status = Optional.of(StatusTaskEnum.PENDING);

        when(todoRepository.findByStatusAndUserOrderByStatusDesc(eq(status.get()),eq(user))).thenReturn(Optional.empty());

        try (MockedStatic<UserLoggedService> userLoggedServiceStatic = Mockito.mockStatic(UserLoggedService.class)) {

            userLoggedServiceStatic.when(UserLoggedService::getUserAuthenticated).thenReturn(user);

            RuntimeException exception = assertThrows(RuntimeException.class, () -> todoService.getTodoByUserAndStatus(status));
            Assertions.assertEquals("Todo not found", exception.getMessage());

            userLoggedServiceStatic.verify(UserLoggedService::getUserAuthenticated);
        }

        verify(todoRepository).findByStatusAndUserOrderByStatusDesc(eq(status.get()),eq(user));
    }

    @Test
    @DisplayName("getTodoByUserAndStatus :: todo without status success")
    void getTodoByUserAndStatus_without_status_success() {

        var user = new User();

        var now = Calendar.getInstance();

        List<Todo> todos = List.of(Todo.builder()
                        .id(1L)
                        .user(User.builder().id(1L).email("test@test.com").build())
                        //.dateCreated(now)
                        //.dateLastUpdate(now)
                        .status(StatusTaskEnum.PENDING)
                        .summary("test")
                        .description("test")
                        .build(),

                Todo.builder()
                        .id(2L)
                        .user(User.builder().id(2L).email("test@test.com").build())
                        //.dateCreated(now)
                        //.dateLastUpdate(now)
                        .status(StatusTaskEnum.PENDING)
                        .summary("test")
                        .description("test")
                        .build());

        when(todoRepository.findByUserOrderByStatusDesc(eq(user))).thenReturn(Optional.of(todos));

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

            Assertions.assertEquals(taskResponseList, todoService.getTodoByUserAndStatus(Optional.empty()));

            userLoggedServiceStatic.verify(UserLoggedService::getUserAuthenticated);
        }

        verify(todoRepository).findByUserOrderByStatusDesc(eq(user));
    }
}