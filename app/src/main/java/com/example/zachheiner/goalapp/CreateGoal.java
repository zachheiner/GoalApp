package com.example.zachheiner.goalapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.firebase.ui.database.SnapshotParser;
import com.google.android.gms.appinvite.AppInviteInvitation;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.appindexing.Action;
import com.google.firebase.appindexing.FirebaseAppIndex;
import com.google.firebase.appindexing.FirebaseUserActions;
import com.google.firebase.appindexing.Indexable;
import com.google.firebase.appindexing.builders.Indexables;
import com.google.firebase.appindexing.builders.PersonBuilder;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import org.w3c.dom.Text;

import static com.example.zachheiner.goalapp.SignInActivity.ID;
import static com.example.zachheiner.goalapp.SignInActivity.SHARED_FILE;

/**
 * CreateGoal
 *
 * this class will allow us to create a goal and then send that goal to
 * Firebase for storage.
 *
 * @author Heiner
 */
public class CreateGoal extends AppCompatActivity {
    private static final String TAG = "CreateGoal";
    static final String GOAL_CLASS = "goalClass";
    private DatabaseReference mFirebaseDatabaseReference;
    private FirebaseUser mFirebaseUser;
    private static final String DEFAULT_GOAL_NAME = "Default";
    private static final Double DEFAULT_BEGIN_VALUE = Double.valueOf("0");
    private static final Double DEFAULT_END_VALUE = Double.valueOf("100");
    private static final String DEFAULT_JOURNAL = "Default";
    private String newGoalName;
    private String newGoalKey = "";
    private Double newGoalBegin;
    private Double newGoalEnd;
    private Double newCurrVal = Double.valueOf(0);
    private String newJournal;

    /**
     * onCreate
     *
     * This method will grab the user UID from shared preferences so that
     * we can authenticate and add the goal to the correct user in within
     * Firebase.
     *
     * @author Heiner
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_goal);
        SharedPreferences sharedPref = getSharedPreferences(SHARED_FILE, Context.MODE_PRIVATE);
        String UID = sharedPref.getString(ID, "");
    }

    /**
     * Cancel
     *
     * this method will cancel the goal and return to Display.
     *
     * @param view
     *
     * @author Heiner
     */
    public void Cancel(View view) {
        Log.d(TAG, "Cancel method called....");
        startActivity(new Intent(this, DisplayActivity.class));
        finish();
    }

    /**
     * AddGoal will get the goal input from the user for their goal and then validate
     * the input to verify there are string values available. It will then make an instance
     * of the goal class and send the new goal instance to firebase to create the new goal.
     *
     * @param view
     *
     * @author Heiner, Stark, Bingham
     */
    public void AddGoal(View view) {
        //Grabbing UID from the shared preferences and setting up database reference.
        Log.d(TAG, "Adding info to database" );
        SharedPreferences sharedPref = getSharedPreferences(SHARED_FILE, Context.MODE_PRIVATE);
        String UID = sharedPref.getString(ID, "");
        mFirebaseDatabaseReference = FirebaseDatabase.getInstance().getReference();

        // grabbing variables from the xml.
        EditText goalNameText = (EditText) findViewById(R.id.goalNameText);
        EditText goalBegin = (EditText) findViewById(R.id.goalBegin);
        EditText goalEnd = (EditText) findViewById(R.id.goalEnd);
        EditText journal = (EditText) findViewById(R.id.journal);

        /*
        * verify that xml fields are not empty and if they are it will set the default value.
        */
        // GoalName check
        if (TextUtils.isEmpty(goalNameText.getText())){
             newGoalName = DEFAULT_GOAL_NAME;
        } else {
            newGoalName = goalNameText.getText().toString();
        }
        // GoalBegin check
        if (TextUtils.isEmpty(goalBegin.getText())) {
            newGoalBegin = DEFAULT_BEGIN_VALUE;
        } else {
            newGoalBegin = Double.valueOf(goalBegin.getText().toString());
        }
        // GoalEnd check
        if (TextUtils.isEmpty(goalEnd.getText())) {
            newGoalEnd = DEFAULT_END_VALUE;
        } else {
            newGoalEnd = Double.valueOf(goalEnd.getText().toString());
        }
        // Journal check
        if (TextUtils.isEmpty(journal.getText())) {
            newJournal = DEFAULT_JOURNAL;
        } else {
            newJournal = journal.getText().toString();
        }

        // logs to verify data being pulled from the xml file.
        Log.d(TAG, "Goal Name: " + newGoalName);
        Log.d(TAG, "Goal Beginning number: " + newGoalBegin);
        Log.d(TAG, "Goal Ending number: " + newGoalEnd);
        Log.d(TAG, "Goal Current Value number: " + newCurrVal);
        Log.d(TAG, "Goal Journal: " + newJournal);


        // creating goal and logging the creation of it and passing it to database.
        GoalClass goalClass = new GoalClass(UID, newGoalKey, newGoalName, newGoalBegin, newGoalEnd, newCurrVal, newJournal);

        Log.d(TAG,"back from goal class going into DB: " + goalClass.getUID() + " " + goalClass.getGoalName());
        newGoalKey = mFirebaseDatabaseReference.child("users").child(UID).child("goalClass").push().getKey();
        goalClass.setGoalKey(newGoalKey);
        mFirebaseDatabaseReference.child("users").child(UID).child(GOAL_CLASS).child(newGoalKey).setValue(goalClass);



        // returning to  the Display Activity for GoalDisplay.
        startActivity(new Intent(this, DisplayActivity.class));
        finish();
        }
    }

