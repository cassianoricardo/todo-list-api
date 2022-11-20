package br.com.itau.todo.list.api.repository;

import br.com.itau.todo.list.api.enums.StatusTaskEnum;
import br.com.itau.todo.list.api.model.Task;
import br.com.itau.todo.list.api.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {


    Optional<List<Task>> findByUser(User user);
    Optional<List<Task>> findByStatusAndUser(StatusTaskEnum status, User user);



}