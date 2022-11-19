package br.com.itau.todo.list.api.repository;

import br.com.itau.todo.list.api.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    Boolean existsByName(String name);

    Boolean existsByEmail(String email);
}