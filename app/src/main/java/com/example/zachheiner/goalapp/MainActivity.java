package com.example.zachheiner.goalapp;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
/*import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;*/

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    public static final String EXTRA_USER = "com.example.zachheiner.goalapp.EXTRA_USER";
    public static final String EXTRA_TOKEN = "com.example.zachheiner.goalapp.EXTRA_TOKEN";
    public static final String EXTRA_UID = "com.example.zachheiner.goalapp.EXTRA_UID";

    /**
     * onCreate
     * Here our button and event listener are set and initialized for use.
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FirebaseDatabase mFirebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference mUsersDatabaseReference = mFirebaseDatabase.getReference().child("user");
        DatabaseReference mGoalsDatabaseReference = mFirebaseDatabase.getReference().child("goal");
        DatabaseReference mJournalDatabaseReference = mFirebaseDatabase.getReference().child("journal");


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
                        Intent intent = getIntent();
                        String username = intent.getStringExtra(SignInActivity.EXTRA_USER);
                        String access_token = intent.getStringExtra(SignInActivity.EXTRA_TOKEN);
                        String userID = intent.getStringExtra(SignInActivity.EXTRA_UID);
                        Intent displayIntent = new Intent(MainActivity.this, DisplayActivity.class);
                        displayIntent.putExtra(EXTRA_USER, username);
                        displayIntent.putExtra(EXTRA_TOKEN, access_token);
                        displayIntent.putExtra(EXTRA_UID, userID);
                        startActivity(displayIntent);
                        finish();
                    }
                }, 2000);

            }
        }

    }

}
