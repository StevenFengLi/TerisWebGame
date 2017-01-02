package com.wzc.Entity;

/**
 * Created by 87485 on 2016/12/6.
 */
public class User {
    public String getUserName(){
        return this.username;
    }

    public int getScore(){
        return this.score;
    }

    public void setUsername(String username){
        this.username=username;
    }
    public void setScore(int score){
        this.score=score;
    }

    private String username;
    private String password;
    private int score;
}
