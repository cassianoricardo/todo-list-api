package br.com.todo.list.api.service;

import br.com.todo.list.api.model.dto.TaskDTO;
import br.com.todo.list.api.repository.TaskRepository;
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
