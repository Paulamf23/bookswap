package com.paula.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.paula.model.User;
import com.paula.repository.UserRepository;

public class CustomUserDetailsService implements UserDetailsService{
   
    @Autowired
    private UserRepository userRepo;
     
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepo.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("El usuario no se ha encontrado");
        }
        return new CustomUserDetails(user);
    }
}
