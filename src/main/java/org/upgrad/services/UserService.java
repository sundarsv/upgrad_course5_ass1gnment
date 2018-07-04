package org.upgrad.services;

import org.springframework.stereotype.Service;
import org.upgrad.models.User;

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
}
