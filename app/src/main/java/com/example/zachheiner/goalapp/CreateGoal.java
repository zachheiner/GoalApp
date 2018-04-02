package com.example.zachheiner.goalapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

/**
 *
 * @author Heiner
 */
public class CreateGoal extends AppCompatActivity {

    /**
     *
     * @param savedInstanceState
     *
     * @author Heiner
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_goal);
    }

    /**
     *
     * @param view
     *
     * @author Heiner
     */
    public void Cancel(View view) {
        finish();
    }
}
