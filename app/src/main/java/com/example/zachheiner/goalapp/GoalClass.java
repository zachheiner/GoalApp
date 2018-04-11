package com.example.zachheiner.goalapp;

import android.util.Log;


/**
 * GoalClass
 *
 * This class will save the goal of the user and allow us to push the information to
 * Firebase as one JSON object for simplicity in maintaining data.
 *
 * @author Heiner, Stark, Bingham
 */
public class GoalClass {

    // Variables
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

    // Non-Default constructor: sets all variables to the current values from the CreateGoal View.
    public GoalClass(String UID, String goalName, Double begin, Double end, Double currVal, String journal){
        Log.d("goalClass","I've made it into the non-default constructor");
        this.UID = UID;
        this.goalName = goalName;
        this.begin = begin;
        this.end = end;
        this.journal = journal;
        this.currVal = currVal;
        Log.d("goalClass","I've made it out of the non-default constructor");
    }

    // getters
    public String getUID() {return UID;}
    public String getGoalName() {return goalName;}
    public Double getBegin() { return begin; }
    public Double getEnd() {return end - begin;}
    public Double getCurrVal() {return currVal;}
    public String getJournal() {return journal;}

    // setters
    public void setUID(String UID) {
        this.UID = UID;
    }
    public void setGoalName(String goalName) {
        this.goalName = goalName;
    }
    public void setCurrVal(Double currVal){
        this.currVal = currVal;
    }
    public void setJournal() {
        this.journal = journal;
    }
}
