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
    public static final String EXTRA_USERNAME = "com.example.zachheiner.goalapp.EXTRA_USERNAME";
    public static final String EXTRA_PASSWORD = "com.example.zachheiner.goalapp.EXTRA_PASSWORD";

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

    public void openLoginActivity() {
        EditText UsernameId = (EditText) findViewById(TextView_usernameField);
        EditText PasswordId = (EditText) findViewById(TextView_passwordField);

        String username = UsernameId.getText().toString();
        String password = PasswordId.getText().toString();

        // Log message to see if scripture was created.
        String user = "About to create intent with " + username + " " + password;
        Log.i(TAG, user);

        Intent intent = new Intent(this, DisplayActivity.class);
        intent.putExtra(EXTRA_USERNAME, username);
        intent.putExtra(EXTRA_PASSWORD, password);
        startActivity(intent);
    }
}
