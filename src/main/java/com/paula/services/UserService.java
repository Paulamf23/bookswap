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
    
    public User searchUser(String username) {
        return userRepository.findByUsername(username);
    }
    
    public User getUser(String username) {
        return userRepository.findByUsername(username);
    }

    @SuppressWarnings("deprecation")
	public User getUserById(int id) {
        return userRepository.getById(id);
    }
    
    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public int getUserId(String username) {
        User user = searchUser(username);
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
