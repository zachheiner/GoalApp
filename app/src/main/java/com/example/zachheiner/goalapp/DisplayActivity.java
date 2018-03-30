package com.example.zachheiner.goalapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

/**
 * DisplayActivity
 * Here our Second Activity receives the information from
 * the main activity and is able to pull down the information
 * needed by the user after their identity has been verified.
 */
public class DisplayActivity extends AppCompatActivity {
    private static final String TAG = "DisplayActivity";

    /**
     * onCreate
     * we will create instances of the objects that the user
     * has access to. In this case a listing of their current
     * goals if they have been created or a listener waiting
     * for the user to add a new goal for themselves.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);
        Intent intent = getIntent();
        String username = intent.getStringExtra(SignInActivity.EXTRA_USER);
        //String password = intent.getStringExtra(MainActivity.EXTRA_PASSWORD);

        String user = "Received intent with " + username;
        Log.i(TAG, user);

        String outputWelcomeMessage = "Welcome to GoalApp " + username;

        TextView DisplayId;
        DisplayId = (TextView) (findViewById(R.id.TextView_Display));
        DisplayId.setText(outputWelcomeMessage);
    }

    public void createGoal(View view){
        Intent createNewGoal = new Intent(this, CreateGoal.class);
        startActivity(createNewGoal);
    }
}
