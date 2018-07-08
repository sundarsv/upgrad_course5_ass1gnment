package org.upgrad.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.upgrad.models.User;
import org.upgrad.services.UserService;

import javax.servlet.http.HttpSession;

@RestController
public class AdminController {
    @Autowired
    private UserService userService;

    @DeleteMapping("/api/admin/user/{userId}")
    public ResponseEntity<?> deleteUser(@PathVariable("userId") int userId, HttpSession session) {

        if (session.getAttribute("currUser")==null) {
            return new ResponseEntity<>("Please Login first to access this endpoint!", HttpStatus.UNAUTHORIZED);
        }
        else if (session.getAttribute("currUserRole").equals("admin")) {
            userService.deleteUserById(userId);
            return new ResponseEntity<>("User with userId: " + userId + " deleted successfully!", HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>("You do not have rights to delete a user!!", HttpStatus.UNAUTHORIZED);
        }
    }

    @GetMapping("/api/admin/users/all")
    public ResponseEntity<?> getAllUsers(HttpSession session) {

        if (session.getAttribute("currUser") == null) {
            return new ResponseEntity<>("Please Login first to access this endpoint!", HttpStatus.UNAUTHORIZED);
        } else if (session.getAttribute("currUserRole").equals("admin")) {
            return new ResponseEntity<>(userService.getAll(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>("You do not have rights to access all users!", HttpStatus.UNAUTHORIZED);
        }
    }

}