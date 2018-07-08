package org.upgrad.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.upgrad.services.CategoryService;
import org.upgrad.services.UserService;

import javax.servlet.http.HttpSession;

@RestController
public class AdminController {
    @Autowired
    private UserService userService;

    @Autowired
    private CategoryService categoryService;
    /*deletes a user having userId in the pathVariable
    accessible by admin
     */
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
    /*its a get request which gives id ,email and username of all the users
      accessible only by admin
     */
    @GetMapping("/api/admin/users/all")
    public ResponseEntity<?> getAllUsers(HttpSession session) {
        if (session.getAttribute("currUser") == null) {
            return new ResponseEntity<>("Please Login first to access this endpoint!", HttpStatus.UNAUTHORIZED);
        } else if (session.getAttribute("currUserRole").equals("admin")) {
            return new ResponseEntity<>(userService.getAllUsers(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>("You do not have rights to access all users!", HttpStatus.UNAUTHORIZED);
        }
    }
    /*creates a new category with descriptin and inserts into the database
      accessible only by admin
     */
    @PostMapping("/api/admin/category")
    public ResponseEntity<?> getAllUsers(@RequestParam String title,@RequestParam String description, HttpSession session) {
        if (session.getAttribute("currUser") == null) {
            return new ResponseEntity<>("Please Login first to access this endpoint!", HttpStatus.UNAUTHORIZED);
        } else if (session.getAttribute("currUserRole").equals("admin")) {
            categoryService.updateCategory(title,description);
            return new ResponseEntity<>(title+" category addes successfully.", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("You do not have rights to add categories.", HttpStatus.UNAUTHORIZED);
        }
    }
}