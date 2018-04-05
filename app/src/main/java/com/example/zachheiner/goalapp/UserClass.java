package com.example.zachheiner.goalapp;

/**
 * Created by Heiner, Stark.
 */

public class UserClass {
    private String id;
    private String userName;
    private String begin;
    private String end;
    private String journal;

    public UserClass() {

    }

    public UserClass(String id, String userName, String begin, String end, String journal){
        this.id = id;
        this.userName = userName;
        this.begin = begin;
        this.end = end;
        this.journal = journal;

    }

    public String getId() {return id;}

    public String getUser() {
        return userName;
    }

    public String getBegin() { return begin; }

    public String getEnd() {return end;}

    public String getJournal() { return journal; }

    public void setId(String id) { this.id = id; }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setBegin(String begin) {
        this.begin = begin;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public void setJournal(String journal) { this.journal = journal; }
}
