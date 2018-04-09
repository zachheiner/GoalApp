package com.example.zachheiner.goalapp;

import android.util.Log;

public class GoalClass {

    private String UID;
    private String goalName;
    private String begin;
    private String end;
    private int currVal;
    private String journal;
    private int nBegin;
    private int nEnd;
    public GoalClass() {

    }

    public GoalClass(String UID, String goalName, String begin, String end, int currVal, String journal){
        Log.d("goalClass","I've made it into the non-default constructor");
        this.UID = UID;
        this.goalName = goalName;
        this.begin = begin;
        this.end = end;
        this.journal = journal;
        nBegin = Integer.parseInt(this.begin);
        nEnd = Integer.parseInt(this.end);
        this.currVal = currVal;
        Log.d("goalClass","I've made it out of the non-default constructor");
    }

    public String getUID() {return UID;}

    public String getGoalName() {return goalName;}

    public double getBegin() { return nBegin; }

    public double getEnd() {return nEnd;}

    public int getCurrVal() {return currVal;}

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

    public void setCurrVal(int currVal){
        this.currVal = currVal;
    }

    public void setJournal() {
        this.journal = journal;
    }
    
}
