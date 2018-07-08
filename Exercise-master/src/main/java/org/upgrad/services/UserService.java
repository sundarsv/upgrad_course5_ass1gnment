package org.upgrad.services;

import org.springframework.stereotype.Service;
import org.upgrad.models.User;
import org.upgrad.models.User_Profile;
import java.util.List;

/*
    Author - Sugandha
    Date - 2 July, 2018
    Description - Interface that contains UserService methods
 */

@Service
public interface UserService {

    public String findUserByUsername(String userName);
    public String findUserByEmail(String email) ;
    public Boolean registerUserDetails(User user);
    public String findUserPassword(String userName);
    public String findUserRole(String userName);
    public User_Profile retrieveUserProfile(int userId);
    public void deleteUserById(int id);
    public List<User> getAll();
}
