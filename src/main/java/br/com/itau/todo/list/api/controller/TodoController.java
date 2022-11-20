package br.com.itau.todo.list.api.controller;

import br.com.itau.todo.list.api.controller.docs.TaskControllerDocs;
import br.com.itau.todo.list.api.controller.request.TodoCreateRequest;
import br.com.itau.todo.list.api.controller.response.TaskResponse;
import br.com.itau.todo.list.api.enums.StatusTaskEnum;
import br.com.itau.todo.list.api.service.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("todo")
public class TodoController implements TaskControllerDocs {

    @Autowired
    private TodoService todoService;

    @PostMapping
    @RolesAllowed("USER")
    public void create(@Valid @RequestBody TodoCreateRequest todoCreateRequest) {
        todoService.createTask(todoCreateRequest);
    }

    @PutMapping("/{id}")
    @RolesAllowed("USER")
    public void update(@Valid @RequestBody TodoCreateRequest todoCreateRequest, @PathVariable Long id) {
        todoService.updateTodo(id, todoCreateRequest);
    }

    @DeleteMapping("/{id}")
    @RolesAllowed("USER")
    public void delete(@Valid @PathVariable Long id) {
        todoService.deleteTodo(id);
    }

    @GetMapping
    @RolesAllowed("USER")
    public ResponseEntity<List<TaskResponse>> getTaskByStatus(@RequestParam(required = false) Optional<StatusTaskEnum> status) {
        return ResponseEntity.ok(todoService.getTodoByUserAndStatus(status));
    }

    @GetMapping("all")
    @RolesAllowed("ADMIN")
    public ResponseEntity<List<TaskResponse>> getAll() {
        return ResponseEntity.ok(todoService.getAllTasks());
    }

}

