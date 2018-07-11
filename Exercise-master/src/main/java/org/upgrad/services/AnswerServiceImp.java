package org.upgrad.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.upgrad.repositories.AnswerRepository;

@Service
public class AnswerServiceImp implements AnswerService{

    @Autowired
    AnswerRepository answerRepository;

    @Override
    public void addQuestion(String ans, int user_id ,int question_id) {
        answerRepository.addAnswer(ans,user_id, question_id) ;
    }

}
