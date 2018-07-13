package org.upgrad.services;

import org.springframework.stereotype.Service;

/*
    Author - Sundar
    Date Created - 13 July, 2018
    Description - Services for 'Likes' feature
 */

@Service
public interface LikesService {
    void giveLikes(int answerId, int user_id);
    String checkLikes(int answerId, int user_id);
    void unlike(int answerId, int user_id);
}