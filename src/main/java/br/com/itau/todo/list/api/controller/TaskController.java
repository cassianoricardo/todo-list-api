package br.com.itau.todo.list.api.controller;

import br.com.itau.todo.list.api.controller.docs.TaskControllerDocs;
import br.com.itau.todo.list.api.controller.request.TaskCreateRequest;
import br.com.itau.todo.list.api.controller.response.TaskResponse;
import br.com.itau.todo.list.api.enums.StatusTaskEnum;
import br.com.itau.todo.list.api.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("task")
public class TaskController implements TaskControllerDocs {

    @Autowired
    private TaskService todoService;

    @PostMapping
    @RolesAllowed("USER")
    public void create(@Valid @RequestBody TaskCreateRequest taskCreateRequest) {
        todoService.createTask(taskCreateRequest);
    }

    @GetMapping
    @RolesAllowed("USER")
    public ResponseEntity<List<TaskResponse>> getTaskByStatus(@RequestParam(required = false) Optional<StatusTaskEnum> status) {
        return ResponseEntity.ok(todoService.getTaskByUserAndStatus(status));
    }

    @GetMapping("all")
    @RolesAllowed("ADMIN")
    public ResponseEntity<List<TaskResponse>> getAll() {
        return ResponseEntity.ok(todoService.getAllTasks());
    }

}

