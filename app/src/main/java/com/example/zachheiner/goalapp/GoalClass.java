package com.example.zachheiner.goalapp;


import android.util.Log;
import static java.lang.Double.parseDouble;

public class GoalClass {

    private String UID;
    private String goalName;
    private String begin;
    private String end;
    private String journal;
    private double  nBegin;
    private double nEnd;
    public GoalClass() {

    }

    public GoalClass(String UID, String goalName, String begin, String end, String journal){
        setUID(UID);
        setGoalName(goalName);
        setBegin(begin);
        setEnd(end);
        setJournal(journal);
        nBegin = parseDouble(this.begin);
        nEnd = parseDouble(this.end);
    }

    public String getUID() {return UID;}

    public String getGoalName() {return goalName;}

    public double getBegin() { return nBegin; }

    public double getEnd() {return nEnd;}

    public String getJournal() {return journal;}

    public void setUID(String UID) {
        this.UID = UID;
    }

    public void setGoalName(String goalName) {
        this.goalName = goalName;
    }

    public void setBegin(String begin) {
        nBegin = Integer.parseInt(begin);
    }

    public void setEnd(String end) {
        nEnd = Integer.parseInt(end);
    }

    public void setJournal(String journal) {
        this.journal = journal;
    }
    
}
