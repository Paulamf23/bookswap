package com.paula.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.paula.model.*;
import com.paula.repository.*;



@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BookService bookService;

    @Autowired
        private CommunityRepository communityRepository;

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

    public void updateUser(User user) {
        userRepository.save(user);
    }

    public void deleteUser(Integer userId) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            List<Book> userBooks = bookService.getBooksByUser(user);
            for (Book book : userBooks) {
                bookService.deleteBook(book.getId());
            }
            userRepository.deleteById(userId);
        } else {
            throw new RuntimeException("El usuario con ID " + userId + " no fue encontrado");
        }
    }

    public List<Community> getAllMessages() {
        return communityRepository.findAll();
    }
    
    public void saveMessage(Community message) {
        message.setTimestamp(LocalDateTime.now());
        communityRepository.save(message);
    }

    public List<Community> getNewMessages() {
        return communityRepository.findTop10ByOrderByTimestampDesc();
    }
}
