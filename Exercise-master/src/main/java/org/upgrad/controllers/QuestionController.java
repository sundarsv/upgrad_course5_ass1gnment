package org.upgrad.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.upgrad.models.Question;
import org.upgrad.models.User;
import org.upgrad.services.QuestionService;
import org.upgrad.services.UserService;
import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.Set;

/*
 * Author - Apoorva
 * Date - 8 July 2018
 * Description - This class contains rest api corresponding to question related methods.
 */

@RestController
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    @PostMapping("/api/question")
    public ResponseEntity<?> createQuestion(@RequestParam("categoryId") Set<Integer> categoryId, @RequestParam("content") String content, HttpSession session) {

        if (session.getAttribute("currUser")==null) {
            return new ResponseEntity<>("Please Login first to access this endpoint!",HttpStatus.UNAUTHORIZED);
        }
        else {
            User user = (User) session.getAttribute("currUser");
            Long id = System.currentTimeMillis() % 1000;
            questionService.addQuestion(id.intValue(), content, user.getId(),categoryId);
            return new ResponseEntity<>("Question added successfully.", HttpStatus.OK);
        }
    }

    @PostMapping("/api/question/all/{categoryId}")
    public ResponseEntity<?> getAllQuestionsByCategory(@PathVariable("categoryId") int categoryId, HttpSession session) {

        if (session.getAttribute("currUser")==null) {
            return new ResponseEntity<>("Please Login first to access this endpoint!",HttpStatus.UNAUTHORIZED);
        }

        else {
            User user = (User) session.getAttribute("currUser");
            return new ResponseEntity<>(questionService.getAllQuestionsByCategory(categoryId), HttpStatus.OK);
        }
    }

    @PostMapping("/api/question/all")
    public ResponseEntity<?> getAllQuestionsByUser(HttpSession session) {

        if (session.getAttribute("currUser")==null) {
            return new ResponseEntity<>("Please Login first to access this endpoint!",HttpStatus.UNAUTHORIZED);
        }

        else {
            User user = (User) session.getAttribute("currUser");
            return new ResponseEntity<>(questionService.getAllQuestionsByUser(user.getId()), HttpStatus.OK);
        }
    }

    @PostMapping("/api/question/{questionId}")
    public ResponseEntity<?> deleteQuestion(@PathVariable("questionId") int questionId,HttpSession session) {

        if (session.getAttribute("currUser")==null) {
            return new ResponseEntity<>("Please Login first to access this endpoint!",HttpStatus.UNAUTHORIZED);
        }

        else {
            User user = (User) session.getAttribute("currUser");
            int userId = questionService.findUserIdfromQuestion(questionId);
            if(userId == user.getId() || user.getRole().equalsIgnoreCase("admin")) {
                questionService.deleteQuestion(questionId);
                return new ResponseEntity<>("Question with questionId " + questionId + " deleted successfully", HttpStatus.OK);
            }
            else {
                return new ResponseEntity<>("You do not have rights to delete this question!", HttpStatus.FORBIDDEN);
            }
        }
    }

}
