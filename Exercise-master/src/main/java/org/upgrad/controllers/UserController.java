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
import org.upgrad.models.User_Profile;
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
     *  It checks signup the user along with its details if user is not already present.
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
            userService.registerUserDetails(user);
            message = userName + " successfully registered";
            return new ResponseEntity <> (message, HttpStatus.OK);
        }
    }

    /**
     * It is used to login the user if username and password exists
     * @param userName username for login
     * @param password password for user
     * @param session HTTP session for status
     * @return Response entity to determine if login is successful or not
     */
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
            if(session.getAttribute("currUser")== null) {
                session.setAttribute("currUser", user);
            }
            return new ResponseEntity <> (message, HttpStatus.OK);
        }
    }

    /**
     * It is used to logout current user
     * @param session HTTP Sesion to store status
     * @return Response entity to determine if logout is successful or not
     */
    @PostMapping("api/user/logout")
    public ResponseEntity<String> signout(HttpSession session){
        if(session.getAttribute("currUser")== null)
            return new ResponseEntity<>("You are currently not logged in",HttpStatus.UNAUTHORIZED);
        else{
            session.removeAttribute("currUser");
            return new ResponseEntity<>("You have logged out successfully!",HttpStatus.OK);}
    }

    /**
     * It is used to retrieve user profile details for a user Id
     * @param userId user Id for which user profile is to be retreived
     * @param session HTTP Session object
     * @return Response entity with userprofile details if userId exists
     */
    @GetMapping("/api/user/userprofile/{userId}")
    public ResponseEntity<?> userProfile(@PathVariable("userId") int userId, HttpSession session) {
        if (session.getAttribute("currUser") == null)
            return new ResponseEntity<>("Please Login first to access this endpoint", HttpStatus.UNAUTHORIZED);
        else {
            User_Profile userProfile = userService.retrieveUserProfile(userId);
            if (userProfile!=null) {
                return new ResponseEntity<>(userService.retrieveUserProfile(userId), HttpStatus.OK);
            }
            else {
                return new ResponseEntity<>("User Profile not found!", HttpStatus.NOT_FOUND);
            }
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

