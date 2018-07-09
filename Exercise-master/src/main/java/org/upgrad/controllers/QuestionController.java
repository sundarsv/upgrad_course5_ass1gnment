package org.upgrad.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
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
            questionService.addQuestion(id.intValue(), content, user.getUser_profile().getUser_id(),categoryId);
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
            int question_id = questionService.getQuestionId(categoryId);
            return new ResponseEntity<>(questionService.getQuestionsByCategory(question_id), HttpStatus.OK);
        }
    }

    @PostMapping("/api/question/all")
    public ResponseEntity<?> getAllQuestionsByUser(HttpSession session) {

        if (session.getAttribute("currUser")==null) {
            return new ResponseEntity<>("Please Login first to access this endpoint!",HttpStatus.UNAUTHORIZED);
        }

        else {
            User user = (User) session.getAttribute("currUser");
            return new ResponseEntity<>(questionService.getQuestionsByUser(user.getUser_profile().getUser_id()), HttpStatus.OK);
        }
    }

    @PostMapping("/api/question/{questionId}")
    public ResponseEntity<?> deleteQuestion(@PathVariable("questionId") int questionId,HttpSession session) {

        if (session.getAttribute("currUser")==null) {
            return new ResponseEntity<>("Please Login first to access this endpoint!",HttpStatus.UNAUTHORIZED);
        }

        else {
            questionService.deleteQuestion(questionId);
            return new ResponseEntity<>("Question with questionId " +questionId +" deleted successfully",HttpStatus.OK);
        }
    }

}
