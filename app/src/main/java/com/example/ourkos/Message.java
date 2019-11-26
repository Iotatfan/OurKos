package com.example.ourkos;

import java.util.Date;

public class Message {
    public String content;
    public String user;
    public String imageprofil;
    public long messageTime;

    public Message(String content,String user){
        this.content=content;
        this.user=user;
        messageTime=new Date().getTime();
    }
    public Message(){

    }

    public String getContent(){
        return content;
    }

    public long getMessageTime(){
        return messageTime;
    }
    public String getUser(){
        return user;
    }
    public String getImage(){
        return imageprofil;
    }
    public void setImage(String image){
        this.imageprofil=image;
    }
}
