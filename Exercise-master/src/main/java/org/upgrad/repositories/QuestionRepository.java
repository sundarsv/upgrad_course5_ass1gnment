package org.upgrad.repositories;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.upgrad.models.Question;

import javax.transaction.Transactional;

/*
    Author - Apoorva
    Date - 8 July, 2018
    Description - Repository that contains CRUD operations for Question table
 */

@Repository
public interface QuestionRepository {

    @Transactional
    @Modifying
    @Query(nativeQuery = true,value="insert into question (id,content,date,user_id) values (?1,?2,NOW(),?3)")
    void addQuestion(int id, String content, int user_id);

    @Transactional
    @Modifying
    @Query(nativeQuery = true,value="insert into question_category (id,question_id,int category_id) values (?1,?2,?3)")
    void addCategory(int id, int question_id, int category_id);

    @Query(nativeQuery = true,value="select * from question where id=?1")
    Iterable<Question> getQuestionsByQuestionId(int id);

    @Query(nativeQuery = true,value="select * from question where user_id=?1")
    Iterable<Question> getQuestionsByUser(int user_id);

    @Transactional
    @Modifying
    @Query(nativeQuery = true,value="delete from question where id=?1 ")
    void deleteQuestionById(int id);

    @Query(nativeQuery = true,value="select question_id from question_category where category_id=?1")
    int getQuestionId(int categoryId);
}
