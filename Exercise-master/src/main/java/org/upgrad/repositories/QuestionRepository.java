package org.upgrad.repositories;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
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

    @Query(nativeQuery = true,value="select * from question where categoryId=?1")
    String getQuestionsByCategory(int categoryId);

    @Query(nativeQuery = true,value="select * from question where user_id=?1")
    String getQuestionsByUser(int user_id);

    @Transactional
    @Modifying
    @Query(nativeQuery = true,value="delete from question where id=?1 ")
    void deleteQuestionById(int id);

}
