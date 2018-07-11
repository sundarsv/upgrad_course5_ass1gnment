package org.upgrad.repositories;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.upgrad.models.User;
import org.springframework.data.repository.CrudRepository;
import java.util.List;


/*
    Author - Sugandha
    Date - 2 July, 2018
    Description - Repository that contains CRUD operations for Users table
 */

@Repository
public interface UserRepository extends CrudRepository<User, Integer>{

    @Query(nativeQuery = true,value="SELECT userName FROM USERS WHERE UPPER(USERNAME) = UPPER (?1) ")
    String findUserExist(String userName);

    @Query(nativeQuery = true,value="SELECT email FROM USERS WHERE UPPER(EMAIL) = UPPER (?1) ")
    String findUserEmail(String email);

    @Query(nativeQuery = true,value="SELECT id FROM USERS WHERE UPPER(USERNAME) = UPPER (?1) ")
    int findUserId(String userName);

    @Transactional
    @Modifying
    @Query(nativeQuery = true,value="INSERT INTO USERS (userName,password,email,role) VALUES (?1,?2,?3,?4)")
    void addUserCredentials(String uname,String password,String email, String role );

    @Query(nativeQuery = true,value="SELECT password FROM USERS WHERE UPPER(USERNAME) = UPPER (?1) ")
    String findUserPassword(String userName);

    @Query(nativeQuery = true,value="SELECT role FROM USERS WHERE UPPER(USERNAME) = UPPER (?1) ")
    String findUserRole(String userName);

    @Query(nativeQuery = true,value="SELECT id FROM USERS WHERE UPPER(USERNAME) = UPPER (?1) ")
    int findId(String userName);

    @Transactional
    @Modifying
    @Query(nativeQuery = true, value = "DELETE FROM users WHERE id = ?1")
    void deleteUserById(int id);

    @Query(nativeQuery = true,value="SELECT id,email,username FROM USERS ")
    List<String> findAllUsers();

    @Query(nativeQuery = true,value="SELECT * FROM USERS WHERE UPPER(USERNAME) = UPPER (?1) ")
    User getUserByUsername(String userName);

}
