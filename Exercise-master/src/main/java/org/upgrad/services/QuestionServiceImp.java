package org.upgrad.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.upgrad.repositories.QuestionRepository;

/*
    Author - Apoorva
    Date - 8 July, 2018
    Description - Implementations of the methods defined in QuestionService Interface
 */

@Service
public class QuestionServiceImp implements QuestionService {

    @Autowired
    QuestionRepository questionRepository;

    @Override
    public void addQuestion(int id, String content, int user_id) {
        questionRepository.addQuestion(id,content,user_id);
    }

    @Override
    public void getQuestionsByCategory(int categoryId) {
        questionRepository.getQuestionsByCategory(categoryId);
    }

    @Override
    public void getQuestionsByUser(int user_id) {
        questionRepository.getQuestionsByUser(user_id);
    }

    @Override
    public void deleteQuestion(int id) {
        questionRepository.deleteQuestionById(id);
    }
}
