package br.com.itau.todo.list.api.service;

import br.com.itau.todo.list.api.model.entity.User;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class UserLoggedService {

    public static User getUserAuthenticated(){
        var securityContext = SecurityContextHolder.getContext();
        return (User) securityContext.getAuthentication().getPrincipal();
    }

}
