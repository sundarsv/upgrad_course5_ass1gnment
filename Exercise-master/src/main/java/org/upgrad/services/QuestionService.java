package org.upgrad.services;

import org.springframework.stereotype.Service;
import org.upgrad.models.Question;

import java.util.Set;

/*
    Author - Apoorva
    Date - 8 July, 2018
    Description - Interface that contains QuestionService methods
 */

@Service
public interface QuestionService {

    void addQuestion(int id, String content, int user_id, Set<Integer> categoryId);
    Iterable<Question> getAllQuestionsByCategory(int categoryId);
    Iterable<Question> getAllQuestionsByUser(int user_id);
    void deleteQuestion(int id);
    Iterable<Integer> getQuestionId(int categoryId);
    int findUserIdfromQuestion(int questionId);
}
