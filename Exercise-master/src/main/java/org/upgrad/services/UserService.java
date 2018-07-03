package org.upgrad.services;

import org.upgrad.models.User;

/*
    Author - Sugandha
    Date - 2 July, 2018
    Description - Interface that contains UserService methods
 */

public interface UserService {

    public String findUserByUsername(String userName);
    public String findUserByEmail(String email) ;
    public Boolean registerUserDetails(User user);


}
