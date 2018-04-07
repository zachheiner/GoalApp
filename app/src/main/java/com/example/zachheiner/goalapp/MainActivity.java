package com.example.zachheiner.goalapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    /**
     * onCreate
     * Here our button and event listener are set and initialized for use.
     *
     * @param savedInstanceState
     *
     * @author Bingham/Stark/Heiner
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        FirebaseDatabase mFirebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference mUserRef = mFirebaseDatabase.getReference().child("user");
        DatabaseReference mGoalRef = mFirebaseDatabase.getReference().child("goal");
        DatabaseReference mJournalRef = mFirebaseDatabase.getReference().child("journal");


        // Initialize Firebase Authentication
        FirebaseAuth mFirebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser mFirebaseUser = mFirebaseAuth.getCurrentUser();
        if (mFirebaseUser == null) {
            // Not signed in, launch the Sign In activity
            Log.d(TAG, "No Firebase User Found, Sign In");
            startActivity(new Intent(this, SignInActivity.class));
            finish();
        } else {
            final String mUsername = mFirebaseUser.getDisplayName();
            if (mFirebaseUser.getPhotoUrl() != null) {
                Context context = getApplicationContext();
                CharSequence text = "Logged in as " + mUsername;
                int duration = Toast.LENGTH_SHORT;

                Toast loginToast = Toast.makeText(context, text, duration);
                loginToast.show();
                Log.d(TAG, "Firebase User Found, Show Goals");

                new Handler().postDelayed(new Runnable() {

                    public void run() {
                        startActivity(new Intent(MainActivity.this, DisplayActivity.class));
                        finish();
                    }
                }, 2000);

            }
        }

    }

}
