package com.paula.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.paula.model.Community;
import com.paula.model.User;

@Repository
public interface CommunityRepository extends JpaRepository<Community, Integer>{

    List<Community> findBySender(User sender);
}
