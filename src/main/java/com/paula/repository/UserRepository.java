package com.paula.repository;

import com.paula.model.Role;
import com.paula.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    User findByEmail(String email);

    User findByUsername(String username);

    ArrayList<User> findAllByRole(Role role);


}
