package org.upgrad.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.upgrad.models.User;
import org.upgrad.repositories.UserProfileRepository;
import org.upgrad.repositories.UserRepository;

/*
    Author - Sugandha
    Date - 2 July, 2018
    Description - Implementations of the methods defined in UserService Interface.
 */

@Service
public class UserServiceImp implements UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserProfileRepository userProfileRepository;

    @Override
    public String findUserByUsername(String userName)
    {
        return String.valueOf(userRepository.findUserExist(userName)) ;
    }

    @Override
    public String findUserByEmail(String email)
    {
        return String.valueOf(userRepository.findUserEmail(email)) ;
    }

    @Override
    public Boolean registerUserDetails(User user)
    {
        Boolean success = false ;

        userRepository.addUserCredentials(user.getUserName(), user.getPassword(), user.getEmail(), "user") ;
        int user_id =  Integer.valueOf(userRepository.findUserId(user.getUserName())) ;

        userProfileRepository.addUserProfileCredentials(user_id, user.getFirstName(), user.getLastName(), user.getAboutMe(), user.getDob(), user.getContactNumber(),user.getCountry());
        success = true ;
        return  success ;
    }

    @Override
    public String findUserPassword(String userName)
    {
        return String.valueOf(userRepository.findUserPassword(userName)) ;
    }

    @Override
    public String findUserRole(String userName)
    {
        return String.valueOf(userRepository.findUserRole(userName)) ;
    }

}
