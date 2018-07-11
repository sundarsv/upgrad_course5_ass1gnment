package org.upgrad.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.upgrad.models.Answer;
import org.upgrad.models.Question;
import org.upgrad.repositories.AnswerRepository;
import org.upgrad.repositories.QuestionRepository;

import java.util.Set;

@Service
public interface AnswerService {

    int addQuestion(String ans, int user_id ,int question_id) ;
    Iterable<String> getAllAnswersToQuestion(int user_id, int question_id);
}
