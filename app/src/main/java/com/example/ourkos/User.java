package com.example.ourkos;

public class User {
    public String email;
    public String username;
    public String gender;
    public String phone;

    public User(){

    }

    public User(String email,String username, String gender, String phone){
        this.username = username;
        this.email = email;
        this.gender = gender;
        this.phone = phone;
    }


}