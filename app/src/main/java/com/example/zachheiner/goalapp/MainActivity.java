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

import static com.example.zachheiner.goalapp.R.id.TextView_usernameField;
import static com.example.zachheiner.goalapp.R.id.TextView_passwordField;

/**
 * MainActivity
 * this will be our login activity that will call the firebase authentication
 * and verify that the user has access to certain aspects of the app.
 */

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    public static final String EXTRA_USERNAME = "com.example.zachheiner.goalapp.EXTRA_USERNAME";
    public static final String EXTRA_PASSWORD = "com.example.zachheiner.goalapp.EXTRA_PASSWORD";

    // Firebase instance variables
    private String mUsername;
    private String mPhotoUrl;
    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser mFirebaseUser;

    /**
     * onCreate
     * Here our button and event listener are set and initialized for use.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



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

        Button loginButton = (Button) findViewById(R.id.Button_login);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                openLoginActivity();
            }
        });
    }

    /**
     * openLoginActivity
     * This is where our intent will be created and information will be sent to
     * our DisplayActivity.
     */
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
