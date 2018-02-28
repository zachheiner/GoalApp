package com.example.zachheiner.goalapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class DisplayActivity extends AppCompatActivity {
    private static final String TAG = "DisplayActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);
        Intent intent = getIntent();
        String username = intent.getStringExtra(MainActivity.EXTRA_USERNAME);
        String password = intent.getStringExtra(MainActivity.EXTRA_PASSWORD);

        String user = "Received intent with " + username + " & their password";
        Log.i(TAG, user);

        String outputWelcomeMessage = "Welcome to GoalApp " + username;

        TextView DisplayId;
        DisplayId = (TextView) (findViewById(R.id.TextView_Display));
        DisplayId.setText(outputWelcomeMessage);
    }
}
