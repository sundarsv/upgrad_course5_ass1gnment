package org.upgrad.services;

import org.springframework.stereotype.Service;
import org.upgrad.models.Comment;
/*
    Author - Kanishka
    Date Created - 13 July, 2018
    Description - Services for 'comment' feature
 */
@Service
public interface CommentService {
    public void addComment( String comment,int user_id,int answer_id);
    public int findUserIdfromComment(int commentId);
    public void editCommentById(String comment,int commentId);
    public void deleteCommentById(int commentId);
    public Iterable<Comment> getAllComments(int answerId);
}
