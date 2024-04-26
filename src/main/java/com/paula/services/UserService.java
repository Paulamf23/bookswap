package com.paula.services;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.paula.model.Role;
import com.paula.model.User;
import com.paula.repository.UserRepository;

@Service
public class UserService {
    @Autowired
	UserRepository userRepository;
		
	public void createUser(User user) {
		userRepository.save(user);
	}
	
	public ArrayList<User> getUsers() {
		ArrayList<User> users = new ArrayList<User>();
		users = (ArrayList<User>)userRepository.findAll();
		return users;
	}
	
	public User searchUser(String email) {
		User searchUser = null;
		searchUser = userRepository.findByEmail(email);
		return searchUser;
	}
	
    public User getUser(String email) {
		User searchUser = null;
		searchUser = userRepository.findByEmail(email);
		return searchUser;
	}

	@SuppressWarnings("deprecation")
	public User getUserById(int id) {
		User searchUser = null;
		searchUser = userRepository.getById(id);
		return searchUser;
	}
	
	public int getUserId(String email) {
		User userId = null;
		userId = searchUser(email);
		int id = userId.getUserId();
		return id;
	}
	
	public void removeUser(User user) {
		userRepository.delete(user);
	}

	public ArrayList<User> getUsersRoles(Role role) {
		ArrayList<User> users = new ArrayList<User>();
		users = (ArrayList<User>)userRepository.findAllByRole(role);
		return users;
	}
}
