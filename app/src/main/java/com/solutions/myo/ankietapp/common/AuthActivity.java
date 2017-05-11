package com.solutions.myo.ankietapp.common;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.solutions.myo.ankietapp.logging.LogHelper;
import com.solutions.myo.ankietapp.workflow.login.LoginActivity;
import com.solutions.myo.ankietapp.workflow.login.data.LoginDataManager;
import com.solutions.myo.ankietapp.workflow.menu.MenuActivity;

/**
 * Created by Jacek on 2017-01-12.
 */

public abstract class AuthActivity extends AppCompatActivity {

    private static final String TAG = AuthActivity.class.getSimpleName();

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    private int authAction;

    private LoginDataManager loginDataManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LogHelper.log(TAG, "::onCreate!", true);
        initializeFirebaseAuth();
    }

    @Override
    protected void onStart() {
        super.onStart();
        LogHelper.log(TAG, "::onStart!", true);
        if(mAuth!=null && mAuthListener!=null) {
            mAuth.addAuthStateListener(mAuthListener);
        }else{
            initializeFirebaseAuth();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        LogHelper.log(TAG, "::onStop!", true);
        if(mAuthListener!=null){
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

    private void initializeFirebaseAuth(){
        LogHelper.log(TAG, "::initializeFirebaseAuth!", true);
        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                toggleAction(firebaseAuth);

            }
        };
    }

    public void setAuthAction(int newAction){
        authAction = newAction;
    }

    public FirebaseAuth getFirebaseAuth(){
        return mAuth;
    }

    public void createLoginDataManager(){
        if(loginDataManager == null){
            loginDataManager = new LoginDataManager();
        }
    }

    public LoginDataManager getLoginDataManager(){
        if(loginDataManager==null){
            LogHelper.log(TAG, "::loginDataManager is unexpectedly null!", true);
           createLoginDataManager();
        }
       return loginDataManager;
    }

    private void toggleAction(FirebaseAuth firebaseAuth){
        LogHelper.log(TAG, "::toggleAction::authAction: " + authAction,  true);

        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();

        switch(authAction){
            case IAuthAction.SIGN_IN:
                if (firebaseUser != null) {
                    // User is signed in
                    LogHelper.log(TAG, "::sign_in::onAuthStateChanged:signed_in:" + firebaseUser.getUid(), true);
                    //TODO save user in SQLite ?
                    navigateNext();
                } else {
                    LogHelper.log(TAG, "::sign_in::onAuthStateChanged::user is unexpectedly null", true);
                }

                break;

            case IAuthAction.SIGN_UP:
                if (firebaseUser != null) {
                    // User is signed up
                    LogHelper.log(TAG, "::sign_up::onAuthStateChanged:signed_up:" + firebaseUser.getUid(), true);
                    navigateNext();
                    //TODO save user in SQLite ?
                    //TODO save user in RealtimeDatabase
                    //Add user to RealDatabase only when signing up
                    if(getLoginDataManager()!=null){
                        getLoginDataManager().writeNewUser(firebaseUser);
                    }

                } else {
                    // User is signed out
                    LogHelper.log(TAG, "::sign_up::onAuthStateChanged::user is unexpectedly null", true);

                }

                break;
            case IAuthAction.SIGN_OUT:
                setAuthAction(IAuthAction.AMBIGUOUS);
                if (firebaseUser == null) {
                    LogHelper.log(TAG, "::sign_out::onAuthStateChanged:signed_out", true);
                    navigateToLogin();
                }
                break;
            default:
                break;
        }

    }


    public void navigateNext() {
        Intent myIntent = new Intent(this, MenuActivity.class);
        startActivity(myIntent);
        finish();
    }

    private void navigateToLogin() {
        Intent myIntent = new Intent(this, LoginActivity.class);
        startActivity(myIntent);
        finish();
    }



}
