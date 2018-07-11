package org.upgrad.repositories;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.upgrad.models.Question;

import javax.transaction.Transactional;

/*
    Author - Sugandha
    Date - 11 July, 2018
    Description - Repository that contains CRUD operations for Notification table
 */

@Repository
public interface AnswerRepository extends CrudRepository<Question,Integer> {

    @Transactional
    @Modifying
    @Query(nativeQuery = true,value="insert into ANSWER (ans,date,user_id,question_id,modifiedOn) values (?1,NOW(),?2,?3,NOW())")
    int addAnswer( String ans, int user_id , int question_id );

    @Query(nativeQuery = true,value="select * from answer where user_id=?1 and question_id=?2")
    Iterable<String> getAllAnswersByUserByQuestion(int user_id, int question_id);

}
