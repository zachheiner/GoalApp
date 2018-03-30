package com.example.zachheiner.goalapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GetTokenResult;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;

/**
 * DisplayActivity
 * Here our Second Activity receives the information from
 * the main activity and is able to pull down the information
 * needed by the user after their identity has been verified.
 */
public class DisplayActivity extends AppCompatActivity {
    private static final String TAG = "DisplayActivity";
    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser mFirebaseUser;
    private boolean hasAccess = true;

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

        Button signOutButton = (Button) findViewById(R.id.sign_out_button);
        signOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                signOut();
            }
        });

        Intent intent = getIntent();
        String username = intent.getStringExtra(SignInActivity.EXTRA_USER);
        Bundle tokenBundle = getIntent().getExtras();
        String access_token = tokenBundle.getString("TOKEN");

        Log.d(TAG, "Access Token: " + access_token);

        mFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        mFirebaseUser.getIdToken(hasAccess);

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

    public void signOut() {
        Log.d(TAG, "Sign Out Button Clicked and sign out succeeded.");
        mFirebaseAuth.getInstance().signOut();
        hasAccess = false;
        finish();
        startActivity(new Intent(this, MainActivity.class));
    }
}

