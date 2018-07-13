package org.upgrad.repositories;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.upgrad.models.Likes;
import org.upgrad.models.Question;
import javax.transaction.Transactional;

/*
    Author - Sundar
    Date Created - 13 July, 2018
    Description - Repository that contains CRUD operations for Likes table
 */

@Repository
public interface LikesRepository extends CrudRepository<Likes, Integer> {

    // Query to 'Like' an 'Answer'
    @Transactional
    @Modifying
    @Query(nativeQuery = true,value="insert into likes (answer_id, user_id) values (?1, ?2)")
    void giveLikes (int answerId, int user_id);

    // Query to check if an 'answer' is already 'liked' by an 'user'
    @Query(nativeQuery = true,value="select * from likes where answer_id=?1 and user_id=?2")
    String checkLikes (int answerId, int user_id);

    // Query to unlike an 'answer'
    @Transactional
    @Modifying
    @Query(nativeQuery = true,value="delete from likes where answer_id=?1 and user_id=?2")
    void unlike (int answerId, int user_id);

}