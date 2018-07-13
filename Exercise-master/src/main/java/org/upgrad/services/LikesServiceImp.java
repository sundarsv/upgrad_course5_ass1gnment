package org.upgrad.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.upgrad.repositories.LikesRepository;

/*
    Author - Sundar
    Date Created - 13 July, 2018
    Description - Implementing services for 'Likes' feature
 */

@Service
public class LikesServiceImp implements LikesService {

    @Autowired
    LikesRepository likesRepository;

    @Override
    public void giveLikes(int answerId, int user_id) {
        likesRepository.giveLikes(answerId, user_id);
    }

    @Override
    public String checkLikes (int answerId, int user_id) {
        return likesRepository.checkLikes(answerId, user_id);
    }

    @Override
    public void unlike(int answerId, int user_id){
        likesRepository.unlike(answerId, user_id);
    }

    @Override
    public int getCount(int answerId) {
        return likesRepository.getCount(answerId);
    }
}