package org.upgrad.services;

import org.springframework.stereotype.Service;

/*
    Author - Apoorva
    Date - 8 July, 2018
    Description - Interface that contains QuestionService methods
 */

@Service
public interface QuestionService {

    void addQuestion(int id,String content, int user_id);
    void getQuestionsByCategory(int categoryId);
    void getQuestionsByUser(int user_id);
    void deleteQuestion(int id);
}
