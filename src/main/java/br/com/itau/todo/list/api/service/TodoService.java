package br.com.itau.todo.list.api.service;

import br.com.itau.todo.list.api.repository.TaskRepository;
import br.com.itau.todo.list.api.model.dto.TaskDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TodoService {

    @Autowired
    TaskRepository taskRepository;

    public void create(TaskDTO taskDTO) {
        taskRepository.save(taskDTO.toEntity());
    }

}
