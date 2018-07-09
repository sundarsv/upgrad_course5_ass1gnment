package org.upgrad.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.time.LocalDateTime;

/*
    Author - Apoorva
    Date - 7 July, 2018
    Description - Persistence Class for Question table
 */
@Entity
@Table(name = "question")
public class Question {

    @Id
    @Column(name = "id")
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    @Column(name = "content")

    private String content ;

    @Column(name = "date")
    private LocalDateTime date ;

    @Column(name = "user_id")
    private int user_id ;

//    @ManyToOne(fetch = FetchType.EAGER)
//    @JsonIgnore
//    private User user;

//    @ManyToMany(fetch = FetchType.EAGER)
//    @JsonIgnore
//    private Category category;

//    public Category getCategory() {
//        return category;
//    }
//
//    public void setCategory(Category category) {
//        this.category = category;
//    }
}
