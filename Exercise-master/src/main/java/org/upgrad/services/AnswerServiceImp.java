package org.upgrad.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.upgrad.models.Answer;
import org.upgrad.repositories.AnswerRepository;

@Service
public class AnswerServiceImp implements AnswerService{

    @Autowired
    AnswerRepository answerRepository;

    @Override
    public int addAnswer(String ans, int user_id ,int question_id) {
       return answerRepository.addAnswer(ans,user_id, question_id) ;
    }

    public Iterable<String> getAllAnswersToQuestion(int user_id, int question_id)
    {
        return answerRepository.getAllAnswersByUserByQuestion(user_id, question_id);
    }

    public Iterable<String> getAllAnswersByUser(int user_id)
    {
        return answerRepository.getAllAnswersByUser(user_id);
    }

    public int findUserIdfromAnswer(int answer_id)
    {
        return answerRepository.getUserId(answer_id);
    }

    public void deleteAnswerById(int answer_id)
    {
        answerRepository.deleteAnswerById(answer_id);
    }

    public void editAnswerById(String answer ,int answer_id){
        answerRepository.editAnswerById(answer ,answer_id);
    }

}
