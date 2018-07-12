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
            answerService.addQuestion(answer,user.getId(),questionId);

            // Saving data in notification table, so that user's will get notification's.
            String message =  "User with userId "+ user.getId() + "has answered your question with questionId "+questionId ;
            notificationService.addnotification(user.getId(),message);

            return new ResponseEntity<>("Answer to questionId " + questionId + " added successfully.", HttpStatus.OK);
        }
    }


    /**
     * It is used to edit answer of a particular id
     * @param answerId answerId of a particular answer to be edited
     * @param answer answer description that needs to be edited
     * @param session HTTP session for status
     * @return Response entity to determine if answer is saved in database.
     * */
    @PutMapping("/api/answer/{answerId}")
    public ResponseEntity<?> editAnswer(@PathVariable("answerId") int answerId, String answer , HttpSession session) {

        // Checking if the user is already registered or not
        if (session.getAttribute("currUser")==null) {
            return new ResponseEntity<>("Please Login first to access this endpoint!",HttpStatus.UNAUTHORIZED);
        }
        else {
            User user = (User) session.getAttribute("currUser");
            int userId =  answerService.getUserIdAnswer(answerId)  ;

            if ( session.getAttribute("currUserRole").equals("admin")  || user.getId() == userId   ) {
                //Updating the new answer for that id.
                answerService.editAnswerById(answer, answerId);
                return new ResponseEntity<>("Answer with answerId" + answerId   +" edited successfully.", HttpStatus.OK);
            }
            else {
                return new ResponseEntity<>("You do not have rights to edit this answer.", HttpStatus.UNAUTHORIZED);
            }
        }
    }

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

    /*
     * It is used to return all answers for current user.
     * @param session HTTP session for status
     * @return Response entity that contains list of answers.
     */
    @GetMapping("/api/answer/all")
    public ResponseEntity<?> getAllAnswersByUser(HttpSession session) {

        // Checking if the user is already registered or not
        if (session.getAttribute("currUser")==null) {
            return new ResponseEntity<>("Please Login first to access this endpoint!",HttpStatus.UNAUTHORIZED);
        }
        else {
            User user = (User) session.getAttribute("currUser");
            // Returns all answers for current user
            return new ResponseEntity<>(answerService.getAllAnswersByUser(user.getId()), HttpStatus.OK);
        }
    }

    /*
     * It is used to return delete answer for corresponding answerId
     * @param session HTTP session for status
     * @return Response entity that gives success message after deleting
     * answer if you are authorized to do that.
     */
    @DeleteMapping("/api/answer/{answerId}")
    public ResponseEntity<?> deleteAnswer(@PathVariable("answerId") int answerId, HttpSession session) {

        if (session.getAttribute("currUser")==null) {
            return new ResponseEntity<>("Please Login first to access this endpoint!", HttpStatus.UNAUTHORIZED);
        }
        else {
            User user = (User) session.getAttribute("currUser");
            int userId =  answerService.getUserIdAnswer(answerId)  ;

            if ( session.getAttribute("currUserRole").equals("admin")  || user.getId() == userId   ) {
                answerService.deleteAnswerById(answerId);
                return new ResponseEntity<>("Answer with answerId "+ answerId  +" deleted successfully.!", HttpStatus.OK);
            }
            else {
                return new ResponseEntity<>("You do not have rights to delete this answer!", HttpStatus.UNAUTHORIZED);
            }
        }
    }


    //TODO : Can be implemented after likes API

    /* getAllAnswersByLikes - "/api/answer/likes/{questionId}"

    It should be a GET request.
    This endpoint must request the path variable 'questionId' of data type Integer for which
    all the answers to be retrieved and sorted in descending order based on the number of likes
    for each answer. The answer with most number of likes should come first.
    If the user is not logged in and tries to access the endpoint, return the JSON response
    "Please Login first to access this endpoint!" with the corresponding HTTP status.
    If the user is logged in and tries to access this endpoint, retrieve all the answers
    with their count of likes, sorted(descending order) based on the number of likes for
    specific questionId and return the JSON response of same with corresponding HTTP status. */

    /* @GetMapping("/api/answer/likes/{questionId}")
    public ResponseEntity<?> getAllAnswersByLikes(@PathVariable("questionId") int questionId, HttpSession session) {

        if (session.getAttribute("currUser")==null) {
            return new ResponseEntity<>("Please Login first to access this endpoint!", HttpStatus.UNAUTHORIZED);
        }
        else {

            return new ResponseEntity<>("Please Login first to access this endpoint!", HttpStatus.UNAUTHORIZED);

        }
    } */
}
