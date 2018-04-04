package com.example.zachheiner.goalapp;

/**
 * Created by dpbin on 3/9/2018.
 */

public class UserClass {
    private String id;
    private String userName;
    private String password;
    private String journal;

    public UserClass() {

    }

    public UserClass(String id, String userName, String password, String journal){
        this.id = id;
        this.userName = userName;
        this.password = password;
        this.journal = journal;

    }

    public String getId() {return id;}

    public String getUser() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public String getJournal() { return journal; }

    public void setId(String id) { this.id = id; }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setJournal(String journal) { this.journal = journal; }
}
