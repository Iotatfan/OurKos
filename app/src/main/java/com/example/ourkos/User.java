package com.example.ourkos;

public class User {
    public String email;
    public String username;
    public String gender;
    public String phone;
    public String type;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public User(){

    }
    public User(String email,String username, String gender, String phone, String type){
        this.username = username;
        this.email = email;
        this.gender = gender;
        this.phone = phone;
        this.type = type;
    }
}
