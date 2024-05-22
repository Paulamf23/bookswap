package com.paula.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.paula.model.Community;

public interface CommunityRepository extends JpaRepository<Community, Integer>{

    List<Community> findTop10ByOrderByTimestampDesc();

}
