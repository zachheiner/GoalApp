package com.example.zachheiner.goalapp;

public class GoalClass {

    private String UID;
    private String goalName;
    private String begin;
    private String end;
    private String journal;
    private int nBegin;
    private int nEnd;
    public GoalClass() {

    }

    public GoalClass(String UID, String goalName, String begin, String end, String journal){
        this.UID = UID;
        this.goalName = goalName;
        this.begin = begin;
        this.end = end;
        this.journal = journal;
        nBegin = Integer.parseInt(this.begin);
        nEnd = Integer.parseInt(this.end);

    }

    public String getUID() {return UID;}

    public String getGoalName() {return goalName;}

    public int getBegin() { return nBegin; }

    public int getEnd() {return nEnd;}

    public String getJournal() {return journal;}

    public void setUID() {
        this.UID = UID;
    }

    public void setGoalName() {
        this.goalName = goalName;
    }

    public void setBegin(String begin) {
        nBegin = Integer.parseInt(begin);
    }

    public void setEnd(String end) {
        nEnd = Integer.parseInt(end);
    }

    public void setJournal() {
        this.journal = journal;
    }
    
}
