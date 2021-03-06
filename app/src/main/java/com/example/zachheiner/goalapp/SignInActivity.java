package com.example.zachheiner.goalapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.iid.FirebaseInstanceId;

import static android.app.PendingIntent.getActivity;

/**
 * Class SignInActivity Definition
 * Here we will sign in a user and verify that they have access.
 *
 * @author Bingham
 */
public class SignInActivity extends AppCompatActivity implements View.OnClickListener, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {
    private static final String TAG = "SignInActivity";
    static final String SHARED_FILE = "com.example.zachheiner.goalapp.SHARED_FILE";
    static final String ID = "UID";
    static final String USERNAME = "USERNAME";
    static final String TOKEN = "TOKEN";
    private static final int RC_SIGN_IN = 9001;

    // Firebase instance variables
    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser mFirebaseUser;
    private GoogleSignInAccount account;
    private GoogleApiClient mGoogleApiClient;
    private SignInButton mSignInButton;

    /**
     * onCreate
     * Here we will create an instance of FirebaseAuth.
     * We will get a current user and set it to mFirebaseUser
     * The sign in button will be created and we will set it's size and set an onClick listener.
     * It will also create all items needed for googleSignInClient.
     * @param savedInstanceState
     * @author Bingham
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

    }


    /**
     * onClick
     * When this function is activated it will call the signIn function.
     * @param v - sign in view.
     *
     * @author Bingham
     */
    //@Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sign_in_button:
                signIn();
                break;
        }
    }

    /**
     * signIn
     * here an intent is created and receives mGoogleApiClient it will then
     * call the startActivityForResult and pass in the SignInIntent and the RC_SIGN_IN code.
     *
     * @author Bingham
     */
    private void signIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    /**
     * onActivityResult
     * @param requestCode  RC_SIGN_IN
     * @param resultCode  Result Code
     * @param data  mGoogleApiClient
     *
     * This will check the requestCode if it is good then it will authorize the GoogleSignInApi
     * with the intent with the mGoogleApiClient and store the result in result.
     * if the result is successful then we will log that it was successful and that we captured
     * the correct username. We will then send the captured account to firebaseAuthWithGoogle.
     * if the result is unsuccessful then we will log a Google failed message and send a toast
     * to the user that their Login Failed.
     *
     * @author Bingham/Heiner/Stark
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if (result.isSuccess()) {
                Log.d(TAG, "Google Sign-In Success.");
                // Google Sign-In was successful, authenticated with Firebase
                account = result.getSignInAccount();
                Log.d(TAG, "Captured the username");
                firebaseAuthWithGoogle(account);
            } else {
                // G5oogle Sign-In failed
                Context context = getApplicationContext();
                CharSequence text = "Login Failed";
                int duration = Toast.LENGTH_SHORT;

                Toast failToast = Toast.makeText(context, text, duration);
                failToast.show();
                Log.e(TAG, "Google Sign-In failed.");
            }
        }
    }



    /**
     * firebaseAuthWithGoogle
     * @param acct
     *
     * this will receive the google account and will log which account we are receiving.
     * it will then create a credential by grabbing the IdToken from the account.
     * it will then authorize with firebase calling the signInWithcredential method and passing
     * the newly created credential.
     * if the addOnCompleteListener is successful then a success message will be logged.
     * if it it is not successful then an exception message will be logged and an
     * Authentication failed messaged will be presented to the user. if the account is not
     * null then the DisplayActivity will be invoked if it is equal to null then a
     * User came back as null message will be logged.
     *
     * @author Bingham/Heiner/Stark
     */
    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        Log.d(TAG, "firebaseAuthWithGooogle:" + acct.getId());
        final AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mFirebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @SuppressLint("ApplySharedPref")
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "signInWithCredential:onComplete:" + task.isSuccessful());

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "signInWithCredential", task.getException());
                            Toast.makeText(SignInActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            if (account != null) {
                                Log.d(TAG, "user found...");
                                mFirebaseUser = mFirebaseAuth.getCurrentUser();
                                String refreshedToken = FirebaseInstanceId.getInstance().getToken();
                                String userUID = mFirebaseUser.getUid();
                                String mUsername = account.getDisplayName();

                                // set up shared preferences and send items to it.
                                SharedPreferences sharedUID = getSharedPreferences(SHARED_FILE, Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedUID.edit();
                                editor.putString(ID, userUID);
                                editor.putString(TOKEN, refreshedToken);
                                editor.putString(USERNAME, mUsername);
                                editor.apply();

                                // verify that information is actually stored in shared preferences.
                                SharedPreferences sharedPref = getSharedPreferences(SHARED_FILE, Context.MODE_PRIVATE);
                                String UserId = sharedPref.getString(ID, "");
                                String UserToken = sharedPref.getString(TOKEN, "");
                                String UserUserName = sharedPref.getString(USERNAME, "");
                                Log.d(TAG, "UID from shared preferences: " + UserId);
                                Log.d(TAG, "TOKEN from shared preferences: " + UserToken);
                                Log.d(TAG, "USERNAME from shared preferences: " + UserUserName);

                                startActivity(new Intent(SignInActivity.this, MainActivity.class));
                                finish();
                            } else {
                                Log.e(TAG, "user not found...");
                            }
                        }
                    }
                });
    }

    /**
     *
     * @param bundle
     */
    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    /**
     *
     * @param i
     */
    @Override
    public void onConnectionSuspended(int i) {

    }

    /**
     * onConnectionFailed
     *
     * We have not set this up yet but it will handle the case when a user is not able
     * to authenticate with Firebase. Currently this is done within the google authentication
     * and just shows a toast stating that the Login failed.
     *
     * @param connectionResult
     */
    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    /**
     * onStart
     *
     * this method we initialize our variables and set up the signInButton then
     * we create the GoogleSignInOptions object and GoogleApiClient builder to
     * create a GoogleSignInClient. this is all called in the onCreate method.
     *
     * @Author Bingham
     */
    @Override
    public void onStart() {
        super.onStart();
        // Initialize FirebaseAuth
        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseUser = mFirebaseAuth.getCurrentUser();

        mSignInButton = (SignInButton) findViewById(R.id.sign_in_button);
        mSignInButton.setSize(SignInButton.SIZE_WIDE);
        mSignInButton.setOnClickListener(this);

        // Configure sign-in to request the user's ID, email address, and basic
        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this /* FragmentActivity*/,this /*OnConnectionFailedListener*/)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        // Build a GoogleSignInClient with the options specified by gso.
        GoogleSignInClient mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
    }

    /**
     * onPause
     *
     * currently not set up but is handled within shared preferences. Eventually
     * this will ensure that a user returns to the activity from which they left.
     * And stays logged in.
     *
     */
    @Override
    public void onPause() {
        super.onPause();

    }

    /**
     * onResume
     *
     * currently not set up but is currently being handled by shared preferences. Eventually
     * this will return the user to whatever activity we would like or just login the current
     * user that may not have been logged out.
     *
     */
    @Override
    public void onResume() {
        super.onResume();

    }
}