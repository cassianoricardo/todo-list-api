package br.com.itau.todo.list.api.repository;

import br.com.itau.todo.list.api.model.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Role findByName(String name);

}