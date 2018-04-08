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

import static com.example.zachheiner.goalapp.SignInActivity.ID;
import static com.example.zachheiner.goalapp.SignInActivity.SHARED_FILE;

/**
 *
 * @author Heiner
 */
public class CreateGoal extends AppCompatActivity {
    private static final String TAG = "CreateGoal";
    static final String GOAL_CLASS = "goalClass";
    private DatabaseReference mFirebaseDatabaseReference;
    private FirebaseUser mFirebaseUser;

    /**
     *
1     *
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

    public void AddGoal(View view) {
        Log.d(TAG, "Adding info to database" );
        SharedPreferences sharedPref = getSharedPreferences(SHARED_FILE, Context.MODE_PRIVATE);
        String UID = sharedPref.getString(ID, "");
        mFirebaseDatabaseReference = FirebaseDatabase.getInstance().getReference();

        EditText goalNameText = (EditText) findViewById(R.id.goalNameText);
        EditText goalBegin = (EditText) findViewById(R.id.goalBegin);
        EditText goalEnd = (EditText) findViewById(R.id.goalEnd);
        EditText journal = (EditText) findViewById(R.id.journal);

        GoalClass goalClass = new GoalClass(UID, goalNameText.getText().toString(),
                goalBegin.getText().toString(), goalEnd.getText().toString(), journal.getText().toString());
        Log.d(TAG,"back from goal class going into DB: " + goalClass.getUID() + " " + goalClass.getGoalName());
        mFirebaseDatabaseReference.child(GOAL_CLASS).push().setValue(goalClass);

        finish();
    }
}
