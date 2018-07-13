package org.upgrad.repositories;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.upgrad.models.Question;
import javax.transaction.Transactional;
import java.sql.ResultSet;

/*
    Author - Apoorva
    Date - 9 July, 2018
    Description - Repository that contains CRUD operations for Question table
 */

@Repository
public interface QuestionRepository extends CrudRepository<Question,Integer> {

    @Transactional
    @Modifying
    @Query(nativeQuery = true,value="insert into question (id,content,date,user_id) values (DEFAULT,?1,NOW(),?2)")
    void addQuestion(String content, int user_id);

    @Query(nativeQuery = true,value="SELECT currval(pg_get_serial_sequence('question', 'id'))")
    int getInsertedId();

    @Transactional
    @Modifying
    @Query(nativeQuery = true,value="insert into question_category (id,question_id,category_id) values (DEFAULT,?2,?3)")
    void addCategory(int question_id, int category_id);

    @Query(nativeQuery = true,value="select * from question where id IN ?1")
    Iterable<Question> getQuestionsByQuestionId(Iterable<Integer> id);

    @Query(nativeQuery = true,value="select * from question where user_id=?1")
    Iterable<Question> getQuestionsByUser(int user_id);

    @Transactional
    @Modifying
    @Query(nativeQuery = true,value="delete from question where id=?1 ")
    void deleteQuestionById(int id);

    @Query(nativeQuery = true,value="select question_id from question_category where category_id=?1")
    Iterable<Integer> getQuestionId(int categoryId);

    @Query(nativeQuery = true,value="select user_id from question where id=?1")
    int getUserIdByQuestion(int questionId);
}
