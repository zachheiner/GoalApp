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
        String book = intent.getStringExtra(MainActivity.EXTRA_USERNAME);
        String chapter = intent.getStringExtra(MainActivity.EXTRA_PASSWORD);

        String scripture = "Received intent with " + book + " " + chapter;
        Log.i(TAG, scripture);

        String outputScripture = book + " " + chapter;

        TextView DisplayId;
        DisplayId = (TextView) (findViewById(R.id.TextView_Display));
        DisplayId.setText(outputScripture);
    }
}
