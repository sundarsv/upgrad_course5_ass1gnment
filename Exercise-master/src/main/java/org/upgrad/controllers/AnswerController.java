package org.upgrad.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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
    public ResponseEntity<?> answerQuestion(@RequestParam int questionId, String answer , HttpSession session) {

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


 /*   editAnswer - "/api/answer/{answerId}"

    It should be a PUT request.

    This endpoint must request the path variable 'answerId' of data type Integer and a parameter 'answer' with data type String which represents the edited/changed answer.
    If the user is not logged in and tries to access the endpoint, return the JSON response "Please Login first to access this endpoint!" with the corresponding HTTP status.
    If the user is logged in and tries to access this endpoint,
    They will be able to edit the answer only if the user logged in is the same person who created the answer or
    has the role of an ‘admin’.  Replace the current answer with the answer provided by the user and return the JSON response
    “Answer with answerId <answerId> edited successfully.” with corresponding HTTP status.
    If the role of the user logged in is neither ‘admin', nor is the user who created the answer, display the message “You do not have rights to edit this answer.”
     with corresponding HTTP status.  */


   /*
    * It is used to return all answers of the given questionId for current user.
    * @param questionId questionId for which all answers are returned
    * @param session HTTP session for status
    * @return Response entity that contains list of answers.
    */
    @GetMapping("/api/answer/all/{questionId}")
    public ResponseEntity<?> getAllAnswersToQuestion(@PathVariable("questionId") int questionId, HttpSession session) {

        // Checking if the user is already registered or not
        if (session.getAttribute("currUser")==null) {
            return new ResponseEntity<>("Please Login first to access this endpoint!",HttpStatus.UNAUTHORIZED);
        }
        else {
            User user = (User) session.getAttribute("currUser");
            // Returns all answers corresponding to questionId for current user
            return new ResponseEntity<>(answerService.getAllAnswersToQuestion(user.getId(), questionId), HttpStatus.OK);
        }
    }



}
