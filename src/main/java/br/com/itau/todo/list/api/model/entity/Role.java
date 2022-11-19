package br.com.itau.todo.list.api.model.entity;

import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;

@Entity
@NoArgsConstructor
public class Role implements Serializable, GrantedAuthority {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

/*    @ManyToMany(mappedBy = "roles")
    private Collection<User> users;*/

    @Override
    public String getAuthority() {
        return name;
    }

    public Role(Long id, String name){
        this.id=id;
        this.name=name;
    }

}