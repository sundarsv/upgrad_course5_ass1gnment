package org.upgrad.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.upgrad.models.Question;
import org.upgrad.repositories.QuestionRepository;

import java.util.Set;

/*
    Author - Apoorva
    Date - 9 July, 2018
    Description - Implementations of the methods defined in QuestionService Interface
 */

@Service
public class QuestionServiceImp implements QuestionService {

    @Autowired
    QuestionRepository questionRepository;

    @Override
    public void addQuestion(int question_id, String content, int user_id,Set<Integer> categoryId) {
        Long id = System.currentTimeMillis() % 1000;
        for(Integer catId : categoryId) {
            questionRepository.addCategory(id.intValue(), question_id, catId);
        }
        questionRepository.addQuestion(question_id,content,user_id);
    }

    @Override
    public Iterable<Question> getQuestionsByCategory(int categoryId) {
        return questionRepository.getQuestionsByQuestionId(categoryId);
    }

    @Override
    public Iterable<Question> getQuestionsByUser(int user_id) {
        return questionRepository.getQuestionsByUser(user_id);
    }

    @Override
    public void deleteQuestion(int id) {
        questionRepository.deleteQuestionById(id);
    }

    @Override
    public int getQuestionId(int categoryId) {
        return questionRepository.getQuestionId(categoryId);
    }
}
