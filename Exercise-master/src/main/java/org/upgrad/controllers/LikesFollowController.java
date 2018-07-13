package org.upgrad.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.upgrad.models.User;
import org.upgrad.services.AnswerService;
import org.upgrad.services.LikesService;
import org.upgrad.services.NotificationService;
import javax.servlet.http.HttpSession;

/*
 * Author - Sundar
 * Date - 13 July 2018
 * Description - Rest API Controller for 'Likes' feature.
 */


@RestController
public class LikesFollowController {

    @Autowired
    LikesService likesService;

    @Autowired
    NotificationService notificationService;

    @Autowired
    AnswerService answerService;

    /* Method to 'like' an 'answer'.
    * Input Param answerId is required.
    * User has to be logged in.
    * User can only 'like' an answer 'once'.
    * The user who wrote the 'liked' 'answer' will be notified.
    */
    @PostMapping ("/api/like/{answerId}")
    public ResponseEntity<?> giveLikes(@PathVariable("answerId") int answerId, HttpSession session) {
        if (session.getAttribute("currUser")==null) {
            return new ResponseEntity<>("Please Login first to access this endpoint!", HttpStatus.UNAUTHORIZED);
        }
        else {
            User user = (User) session.getAttribute("currUser");
            if (likesService.checkLikes(answerId, user.getId())!=null) {
                return  new ResponseEntity<>("You have already liked this answer!", HttpStatus.CONFLICT);
            }
            else {
                likesService.giveLikes(answerId, user.getId());
                //Adding a notification to the 'user' who created the 'answer'
                notificationService.addnotification(answerService.getUserIdAnswer(answerId), "User with userId"
                        + user.getId() + " has liked your answer with answerId " + answerId);
                return new ResponseEntity<>("answerId " + answerId + " liked successfully.", HttpStatus.OK);
            }
        }
    }

    /* Method to 'unlike' an 'answer'.
     * Input Param answerId is required.
     * User has to be logged in.
     * User can only 'unlike' an that they have 'liked'.
     */
    @DeleteMapping ("/api/unlike/{answerId}")
    public ResponseEntity<?> unlike (@PathVariable("answerId") int answerId, HttpSession session) {
        if (session.getAttribute("currUser") == null) {
            return new ResponseEntity<>("Please Login first to access this endpoint!", HttpStatus.UNAUTHORIZED);
        } else {
            User user = (User) session.getAttribute("currUser");
            if (likesService.checkLikes(answerId, user.getId()) == null) {
                return new ResponseEntity<>("You have not liked this answer.", HttpStatus.CONFLICT);
            } else {
                likesService.unlike(answerId, user.getId());
                return new ResponseEntity<>("You have unliked answer with answerId " + answerId + " successfully.", HttpStatus.OK);
            }
        }
    }
}