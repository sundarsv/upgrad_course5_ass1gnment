package org.upgrad.controllers;

import com.google.common.hash.Hashing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;
import org.upgrad.models.User;
import org.upgrad.services.UserService;
import org.upgrad.services.UserServiceImp;
import javax.servlet.http.HttpSession;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;

/*
 * Author - Sugandha
 * Date - 1 July 2018
 * Description - This class contains rest api corresponding to user related methods.
 */
@RestController
public class UserController {

    @Autowired
    private UserService userService;

    /*
     * This returns true if username already exists.
     * Otherwise it returns false.
     */
    public Boolean registerUserWithExistingUsername(String userName) throws Exception {
        String userPresent = String.valueOf(userService.findUserByUsername(userName));
        if (!(userPresent.equalsIgnoreCase("null"))) {
            return true;
        } else {
            return false;
        }
    }

    /*
     *  This returns true if email already exists.
     *  Otherwise it returns false.
     */
    public Boolean registerUserWithExistingEmail(String email) throws Exception {
        String userPresent = String.valueOf(userService.findUserByEmail(email));
        if (!(userPresent.equalsIgnoreCase("null"))) {
            return true;
        } else {
            return false;
        }
    }
    /*
     *  It checks signup the user along with it's details if user is not already present.
     *  This method gives relevant messages in case username or email is already registered
     */
    @PostMapping("/api/user/signup/")
    public ResponseEntity registerUser(@RequestParam String userName, @RequestParam String email, @RequestParam String password, @RequestParam String firstName, @RequestParam(value = "lastName",required = false) String lastName, @RequestParam(value = "aboutMe",required = false) String aboutMe, @RequestParam(value = "contactNumber",required = false) String contactNumber, @RequestParam String dob, @RequestParam String country) throws Exception {

        // Setting url data into user model class.
        User user = new User();
        user.setPassword(hashPassword(password));
        user.setLastName(lastName);
        user.setFirstName(firstName);
        user.setEmail(email);
        user.setCountry(country);
        user.setContactNumber(contactNumber);
        user.setUserName(userName);
        user.setDob(new SimpleDateFormat("yyyy-MM-dd").parse(dob));
        user.setAboutMe(aboutMe);

        String message = null;
        if (registerUserWithExistingUsername(user.getUserName())) {
            message = "Try any other Username, this Username has already been taken.";
            return new ResponseEntity < > (message, HttpStatus.FORBIDDEN);
        } else if (registerUserWithExistingEmail(user.getEmail())) {
            message = "This user has already been registered, try with any other emailId.";
            return new ResponseEntity < > (message, HttpStatus.FORBIDDEN);
        } else {
            //TODO : Make Parameters optional
            userService.registerUserDetails(user);
            message = userName + " successfully registered";
            return new ResponseEntity < > (message, HttpStatus.OK);
        }
    }

    @PostMapping("/api/user/login")
    public ResponseEntity sigin(@RequestParam String userName, @RequestParam String password, HttpSession session) {
        String message = null;
        String passwordHash = hashPassword(password);
        String passwordU = String.valueOf(userService.findUserPassword(userName));
        if (!(passwordU.equalsIgnoreCase(passwordHash))) {
            message = "Invalid Credentials";
            return new ResponseEntity < > (message, HttpStatus.UNAUTHORIZED);
        } else {
            String role = String.valueOf(userService.findUserRole(userName));
            if (role.equalsIgnoreCase("admin")) {
                message = "You have logged in as admin!";
            } else if (role.equalsIgnoreCase("user")) {
                message = "You have logged in successfully!";
            }
            User user = new User(userName);
            session.setAttribute("currUser", user);
            return new ResponseEntity < > (message, HttpStatus.OK);
        }
    }

    /* This is a helper function that encrypts a plain text password using the
     * SHA-256 encryption
     *
     * @param password the plain text String that we want to encrypt
     *
     * @return the SAH-256 encrypted version of the password
     */
    private String hashPassword(String password) {
        String passwordHash = Hashing.sha256().hashString(password, StandardCharsets.UTF_8).toString();
        return passwordHash;
    }
}

