package com.example.registerandloginproject;

//user class to help track user information
public class User {
    long userId;
    String name;
    String birthday;
    String password;
    String email;

    public User (long userId, String name, String email, String birthday, String password)
    {
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.birthday = birthday;
        this.password = password;
    }
}