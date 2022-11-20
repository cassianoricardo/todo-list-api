package br.com.itau.todo.list.api.service;

import br.com.itau.todo.list.api.controller.request.TodoCreateRequest;
import br.com.itau.todo.list.api.controller.response.TaskResponse;
import br.com.itau.todo.list.api.enums.StatusTaskEnum;
import br.com.itau.todo.list.api.exception.NotFoundException;
import br.com.itau.todo.list.api.model.Todo;
import br.com.itau.todo.list.api.model.User;
import br.com.itau.todo.list.api.repository.TodoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static br.com.itau.todo.list.api.service.UserLoggedService.getUserAuthenticated;

@Slf4j
@Service
public class TodoService {

    @Autowired
    TodoRepository todoRepository;

    public void createTask(TodoCreateRequest todoCreateRequest) {

        var task = Todo.builder()
            .user(getUserAuthenticated())
            .dateCreated(Calendar.getInstance())
            .dateLastUpdate(Calendar.getInstance())
            .summary(todoCreateRequest.getSummary())
            .description(todoCreateRequest.getDescription())
            .status(todoCreateRequest.getStatus())
            .build();

        todoRepository.save(task);
    }

    public List<TaskResponse> getAllTasks(){
        List<Todo> todos = todoRepository.findAll();
        return todos.stream()
                    .map(todo -> TaskResponse.builder().id(todo.getId())
                    .dateCreated(todo.getDateCreated())
                    .dateLastUpdate(todo.getDateLastUpdate())
                    .summary(todo.getSummary())
                    .description(todo.getDescription())
                    .user(todo.getUser().getUsername())
                    .status(todo.getStatus())
                    .build()).collect(Collectors.toList());
    }

    public List<TaskResponse> getTodoByUserAndStatus(Optional<StatusTaskEnum> status) {
        final String TODO_NOT_FOUND = "Todo not found";

        var user = getUserAuthenticated();
        List<Todo> todos;
        if(status.isEmpty()) {
            todos = todoRepository.findByUserOrderByStatusDesc(user).orElseThrow(() -> {
                        log.error(TODO_NOT_FOUND);
                        throw new NotFoundException(TODO_NOT_FOUND);
                    });
        }else{
            todos = todoRepository.findByStatusAndUserOrderByStatusDesc(status.get(),user).orElseThrow(() ->
            {
                log.error(TODO_NOT_FOUND);
                throw new NotFoundException(TODO_NOT_FOUND);
            });
        }
        return todos.stream().map(todo -> TaskResponse.builder().id(todo.getId())
                                .summary(todo.getSummary())
                                .description(todo.getDescription())
                                .status(todo.getStatus())
                                .dateLastUpdate(todo.getDateLastUpdate())
                                .dateCreated(todo.getDateCreated())
                                .build()).collect(Collectors.toList());

    }


    public void updateTodo(Long id, TodoCreateRequest todoCreateRequest) {

        var user = getUserAuthenticated();
        var todo = new Todo();

        if(isAdmin(user)){
            todo = todoRepository.findById(id).orElseThrow(()-> {
                log.error("todo id : {}, not found", id);
                throw new NotFoundException("todo not found");
            });
        }else{
            todo = todoRepository.findByIdAndUser(id, user).orElseThrow(()-> {
                log.error("todo id : {}, not found", id);
                throw new NotFoundException("todo not found");
            });
        }

        todo.setSummary(todoCreateRequest.getSummary() != null ? todoCreateRequest.getSummary() : todo.getSummary());
        todo.setDescription(todoCreateRequest.getDescription() != null ? todoCreateRequest.getDescription() : todo.getDescription());
        todo.setStatus(todoCreateRequest.getStatus() != null ? todoCreateRequest.getStatus() : todo.getStatus());
        todo.setDateLastUpdate(Calendar.getInstance());

        todoRepository.save(todo);
    }

    private static boolean isAdmin(User user) {
        return user.getAuthorities().stream().anyMatch(role -> role.getAuthority().equals("ADMIN"));
    }

    public void deleteTodo(Long id) {

        var user = getUserAuthenticated();
        var todo = new Todo();

         if(isAdmin(user)){
            todo = todoRepository.findById(id).orElseThrow(()-> {
                log.error("todo id : {}, not found",id);
                throw new NotFoundException(String.format("todo id: %d not found", id));
            });
        }else{
             todo = todoRepository.findByIdAndUser(id, user).orElseThrow(()-> {
                 log.error("todo id : {}, not found",id);
                 throw new NotFoundException(String.format("todo id: %d not found", id));
             });
         }
        todoRepository.delete(todo);
    }
}
