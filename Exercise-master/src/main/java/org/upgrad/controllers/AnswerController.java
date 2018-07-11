package org.upgrad.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.upgrad.models.User;
import org.upgrad.services.AnswerService;
import org.upgrad.services.NotificationService;
import org.upgrad.services.QuestionService;
import javax.servlet.http.HttpSession;

/*
 * Author - Sugandha
 * Date - 11 July 2018
 * Description - This class contains rest api corresponding to answer related methods.
*/

@RestController
public class AnswerController {

    @Autowired
    private AnswerService answerService ;

    @Autowired
    private QuestionService questionService;

    @Autowired
    private NotificationService notificationService;

    /**
     * It is used to save answer corresponding to userId and QuestionId
     * @param questionId questionId to answer question
     * @param answer answer description
     * @param session HTTP session for status
     * @return Response entity to determine if answer is saved in database.
     * */
    @PostMapping("/api/answer")
    public ResponseEntity<?> answerQuestion(@PathVariable("questionId") int questionId, String answer ,HttpSession session) {

        // Checking if the user is already registered or not
        if (session.getAttribute("currUser")==null) {
            return new ResponseEntity<>("Please Login first to access this endpoint!",HttpStatus.UNAUTHORIZED);
        }
        else {
            User user = (User) session.getAttribute("currUser");

            // Saving answer details in answer table.
            int userId = questionService.findUserIdfromQuestion(questionId);
            answerService.addQuestion(answer,userId,questionId);

            // Saving data in notification table, so that user's will get notification's.
            String message =  "User with userId "+ userId + "has answered your question with questionId "+questionId ;
            notificationService.addnotification(userId,message);

            return new ResponseEntity<>("Answer to questionId " + questionId + " added successfully.", HttpStatus.OK);
        }
    }
}
