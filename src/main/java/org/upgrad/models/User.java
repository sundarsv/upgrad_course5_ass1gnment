package org.upgrad.models;
import java.util.Date;

/*
    Author - Sugandha
    Date - 2 July, 2018
    Descrition -Model class to contain user and userProfile Data
 */
public class User {
    private int id;
    private String userName;
    private String email;
    private String password;
    private Date dob;
    private String firstName;
    private String lastName;
    private String country;
    private String aboutMe;
    private String contactNumber;

    public User() {}

    public User(String userName) {
        this.userName = userName;
    }

    /*
     Getters and setters
     */
    public int getId() {
        return id;
    }

    public String getUserName() {
        return userName;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public Date getDob() {
        return dob;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getCountry() {
        return country;
    }

    public String getAboutMe() {
        return aboutMe;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setAboutMe(String aboutMe) {
        this.aboutMe = aboutMe;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }
}
