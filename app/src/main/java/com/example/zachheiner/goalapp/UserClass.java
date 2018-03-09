package com.example.zachheiner.goalapp;

/**
 * Created by dpbin on 3/9/2018.
 */

public class UserClass {
    private String userName;
    private String password;
    private String journal;

    public String getUser() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public String geJournal() { return journal; }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setJournal(String journal) { this.journal = journal; }
}
