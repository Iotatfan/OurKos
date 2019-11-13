package com.example.ourkos;

public class User {
    public String email;
    public String username;
    public String gender;
    public String phone;
    public String type;

    public User(){

    }
    public User(String email,String username, String gender, String phone, String type){
        this.username = username;
        this.email = email;
        this.gender = gender;
        this.phone = phone;
        this.type=type;
    }
}
