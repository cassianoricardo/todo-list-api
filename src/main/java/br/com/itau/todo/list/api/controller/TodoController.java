package br.com.itau.todo.list.api.controller;

import br.com.itau.todo.list.api.controller.docs.TodoControllerDocs;
import br.com.itau.todo.list.api.model.dto.TaskDTO;
import br.com.itau.todo.list.api.service.TodoService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import static br.com.itau.todo.list.api.util.TaskConstants.*;

@RestController
@RequestMapping(TODO)
@AllArgsConstructor
public class TodoController implements TodoControllerDocs {

    @Autowired
    private TodoService todoService;

    @PostMapping(TASK)
    public void createTask(@Valid @RequestBody TaskDTO taskDTO) {
        todoService.create(taskDTO);
    }
}

