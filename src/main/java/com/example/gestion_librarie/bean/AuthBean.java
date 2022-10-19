package com.example.gestion_librarie.bean;


import javax.inject.Named;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.example.gestion_librarie.model.Users;
import com.example.gestion_librarie.repository.UsersRepository;


@Named
public class AuthBean {

    String username;
    Users currentUser;

    @Autowired
    UsersRepository usersRepository;

    public String getUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        username = authentication.getName();
        return username;
    }

    public Users getCurrentUser() {
        currentUser= usersRepository.findByUsername(getUsername());
        return currentUser;
    }

}
