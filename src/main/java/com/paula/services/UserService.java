package com.paula.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.paula.model.Role;
import com.paula.model.User;
import com.paula.repository.UserRepository;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
        
    public void createUser(User user) {
        userRepository.save(user);
    }
    
    public List<User> getUsers() {
        return userRepository.findAll();
    }
    
    public User searchUser(String email) {
        return userRepository.findByEmail(email);
    }
    
    public User getUser(String email) {
        return userRepository.findByEmail(email);
    }

    @SuppressWarnings("deprecation")
	public User getUserById(int id) {
        return userRepository.getById(id);
    }
    
    public int getUserId(String email) {
        User user = searchUser(email);
        if (user != null) {
            return user.getUserId();
        }
        return -1;
    }
    
    public void removeUser(User user) {
        userRepository.delete(user);
    }

    public List<User> getUsersRoles(Role role) {
        return userRepository.findAllByRole(role);
    }
}
