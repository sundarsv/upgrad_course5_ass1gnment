package org.upgrad.services;

/*
    Author - Sundar
    Date Created - 13 July, 2018
    Description - Services for 'Follow' feature
 */

import org.springframework.stereotype.Service;

@Service
public interface FollowService {
    void addFollowCategory(int category_id, int user_id);
    String checkFollows(int category_id, int user_id);
    void unFollow (int category_id, int user_id);

}
