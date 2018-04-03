package com.example.zachheiner.goalapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

/**
 *
 * @author Heiner
 */
public class CreateGoal extends AppCompatActivity {
    private static final String TAG = "CreateGoal";
    /**
     *
1     *
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
        Log.d(TAG, "Cancel method called....");
        startActivity(new Intent(this, DisplayActivity.class));
        finish();
    }
}
