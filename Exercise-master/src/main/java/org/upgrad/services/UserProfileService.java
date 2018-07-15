package org.upgrad.services;

import org.springframework.stereotype.Service;
import org.upgrad.models.User_Profile;
/*
    Author - Sugandha
    Date - 2 July, 2018
    Description - Interface that contains UserProfileService methods
 */
@Service
public interface UserProfileService {

    public User_Profile retrieveUserProfile(int userId);

}
