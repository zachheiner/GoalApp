package com.example.zachheiner.goalapp;

public class GoalClass {

    private String UID;
    private String goalName;
    private int begin;
    private int end;
    private String journal;

    public GoalClass() {

    }

    public GoalClass(String UID, String goalName, int begin, int end, String journal){
        this.UID = UID;
        this.goalName = goalName;
        this.begin = begin;
        this.end = end;
        this.journal = journal;

    }

    public String getUID() {return UID;}

    public String getGoalName() {return goalName;}

    public int getBegin() { return begin; }

    public int getEnd() {return end;}

    public String getJournal() {return journal;}

    public void setUID() {
        this.UID = UID;
    }

    public void setGoalName() {
        this.goalName = goalName;
    }

    public void setBegin(int begin) {
        this.begin = begin;
    }

    public void setEnd(int end) {
        this.end = end;
    }

    public void setJournal() {
        this.journal = journal;
    }
    
}
