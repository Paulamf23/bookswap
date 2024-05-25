package com.paula.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.paula.model.Community;
import com.paula.model.User;
import com.paula.repository.CommunityRepository;

@Service
public class CommunityService {

    @Autowired
    private CommunityRepository communityRepository;

    public List<Community> getMessagesByUser(User user) {
        return communityRepository.findBySender(user);
    }

    public void deleteMessage(Integer messageId) {
        communityRepository.deleteById(messageId);
    }
}