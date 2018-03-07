package com.example.zachheiner.goalapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import static com.example.zachheiner.goalapp.R.id.TextView_usernameField;
import static com.example.zachheiner.goalapp.R.id.TextView_passwordField;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser mFirebaseUser;

    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mFirebaseDatabaseReference;
    private DatabaseReference mUsersDatabaseReference;
    private DatabaseReference mGoalsDatabaseReference;
    private DatabaseReference mJournalDatabaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mUsersDatabaseReference = mFirebaseDatabase.getReference().child("user");
        mGoalsDatabaseReference = mFirebaseDatabase.getReference().child("goal");
        mJournalDatabaseReference = mFirebaseDatabase.getReference().child("journal");

        Button loginButton = (Button) findViewById(R.id.Button_login);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                openLoginActivity();
            }
        });
    }

}
