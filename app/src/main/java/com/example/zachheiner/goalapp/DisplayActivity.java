package com.example.zachheiner.goalapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
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

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
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

import static com.example.zachheiner.goalapp.SignInActivity.ID;
import static com.example.zachheiner.goalapp.SignInActivity.SHARED_FILE;
import static com.example.zachheiner.goalapp.SignInActivity.TOKEN;
import static com.example.zachheiner.goalapp.SignInActivity.USERNAME;

/**
 * DisplayActivity
 * Here our Second Activity receives the information from
 * the main activity and is able to pull down the information
 * needed by the user after their identity has been verified.
 *
 * @author Bingham
 */
public class DisplayActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {
    private static final String TAG = "DisplayActivity";
    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser mFirebaseUser;
    private GoogleApiClient mGoogleApiClient;

    /**
     * onCreate
     * we will create instances of the objects that the user
     * has access to. In this case a listing of their current
     * goals if they have been created or a listener waiting
     * for the user to add a new goal for themselves.
     * @param savedInstanceState
     *
     * @author Bingham
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);

        
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this /* FragmentActivity*/,  this /*OnConnectionFailedListener*/)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        Button signOutButton = (Button) findViewById(R.id.sign_out_button);
        signOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                signOut();
            }
        });

        SharedPreferences sharedPref = getSharedPreferences(SHARED_FILE, Context.MODE_PRIVATE);
        String UserId = sharedPref.getString(ID, "");
        String UserToken = sharedPref.getString(TOKEN, "");
        String username = sharedPref.getString(USERNAME, "");
        Log.d(TAG, "the user ID from shared preferences is: " + UserId);
        Log.d(TAG, "the user token from shared preferences is: " + UserToken);
        Log.d(TAG, "the users username from shared preferences is: " + username);

        mFirebaseUser = mFirebaseAuth.getInstance().getCurrentUser();
        String currentUser = mFirebaseUser.getDisplayName();
        String verifyUID = mFirebaseUser.getUid();
        Log.d(TAG, "Current User: " + currentUser);
        Log.d(TAG, "Verify UID: " + verifyUID);
        Log.d(TAG, "Original UID: " + UserId);

        TextView DisplayId;
        String outputWelcomeMessage = "Welcome to GoalApp " + username;
        DisplayId = (TextView) (findViewById(R.id.TextView_Display));
        DisplayId.setText(outputWelcomeMessage);
    }

    /**
     * createGoal
     *
     * @param view
     * @author Heiner
     */
    public void createGoal(View view){
    Intent createNewGoal = new Intent(this, CreateGoal.class);
    startActivity(createNewGoal);
    finish();
    }

    /**
     * signOut
     * This method will change the authentication state for the firebase user and
     * also log out the google user in an Async task. It will then return to the Main
     * Activity.
     *
     * @author Bingham.
     */
    public void signOut() {
        String userUID = "";
        String refreshedToken = "";
        String mUsername = "";

        mFirebaseAuth.getInstance().signOut();
        // Google sign out
        Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                new ResultCallback<Status>() {
                    @Override
                    public void onResult(@NonNull Status status) {
                    }
                });

        // setting SharedPreferences back to default.
        SharedPreferences sharedUID = getSharedPreferences(SHARED_FILE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedUID.edit();
        editor.putString(ID, userUID);
        editor.putString(TOKEN, refreshedToken);
        editor.putString(USERNAME, mUsername);
        editor.apply();

        // verify that SharedPreferences are back at default values.
        SharedPreferences sharedPref = getSharedPreferences(SHARED_FILE, Context.MODE_PRIVATE);
        String UserId = sharedPref.getString(ID, "");
        String UserToken = sharedPref.getString(TOKEN, "");
        String username = sharedPref.getString(USERNAME, "");
        Log.d(TAG, "the user ID from shared preferences is: " + UserId);
        Log.d(TAG, "the user token from shared preferences is: " + UserToken);
        Log.d(TAG, "the users username from shared preferences is: " + username);

        Log.d(TAG, "Sign Out Button Clicked and sign out succeeded.");
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}

