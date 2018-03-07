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
    
      // Firebase instance variables
    private String mUsername;
    private String mPhotoUrl;
    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser mFirebaseUser;

    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mFirebaseDatabaseReference;
    private DatabaseReference mUsersDatabaseReference;
    private DatabaseReference mGoalsDatabaseReference;
    private DatabaseReference mJournalDatabaseReference;

    /**
     * onCreate
     * Here our button and event listener are set and initialized for use.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mUsersDatabaseReference = mFirebaseDatabase.getReference().child("user");
        mGoalsDatabaseReference = mFirebaseDatabase.getReference().child("goal");
        mJournalDatabaseReference = mFirebaseDatabase.getReference().child("journal");


        // Initialize Firebase Authentication
        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseUser = mFirebaseAuth.getCurrentUser();
        if (mFirebaseUser == null) {
            // Not signed in, launch the Sign In activity
            startActivity(new Intent(this, SignInActivity.class));
            finish();
            return;
        } else {
            mUsername = mFirebaseUser.getDisplayName();
            if (mFirebaseUser.getPhotoUrl() != null) {
                mPhotoUrl = mFirebaseUser.getPhotoUrl().toString();
            }
        }
        
    }

  

}
