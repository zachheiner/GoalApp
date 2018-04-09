package com.example.zachheiner.goalapp;

import android.util.Log;

public class GoalClass {

    private String UID;
    private String goalName;
    private Double begin;
    private Double end;
    private Double currVal;
    private String journal;
    private Double nBegin;
    private Double nEnd;
    public GoalClass() {

    }

    public GoalClass(String UID, String goalName, Double begin, Double end, Double currVal, String journal){
        Log.d("goalClass","I've made it into the non-default constructor");
        this.UID = UID;
        this.goalName = goalName;
        this.begin = begin;
        this.end = end;
        this.journal = journal;
      //  nBegin = Integer.parseInt(this.begin);
      //  nEnd = Integer.parseInt(this.end);
        this.currVal = currVal;
        Log.d("goalClass","I've made it out of the non-default constructor");
    }

    public String getUID() {return UID;}

    public String getGoalName() {return goalName;}

    public Double getBegin() { return begin; }

    public Double getEnd() {return end - begin;}

    public Double getCurrVal() {return currVal;}

    public String getJournal() {return journal;}

    public void setUID(String UID) {
        this.UID = UID;
    }

    public void setGoalName(String goalName) {
        this.goalName = goalName;
    }

    //public void setBegin(String begin) {
      //  nBegin = Integer.parseInt(begin);
    //}

    //public void setEnd(String end) {
    //    nEnd = Integer.parseInt(end);
    //}

    public void setCurrVal(Double currVal){
        this.currVal = currVal;
    }

    public void setJournal() {
        this.journal = journal;
    }
    
}
