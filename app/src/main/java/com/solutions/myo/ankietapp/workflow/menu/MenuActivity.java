package com.solutions.myo.ankietapp.workflow.menu;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.solutions.myo.ankietapp.R;
import com.solutions.myo.ankietapp.analytics.FirebaseAnalyticsHelper;
import com.solutions.myo.ankietapp.analytics.logging.LogHelper;
import com.solutions.myo.ankietapp.databinding.ActivityMenuBinding;
import com.solutions.myo.ankietapp.workflow.login.LoginActivity;
import com.solutions.myo.ankietapp.workflow.survey.SurveyActivity;

public class MenuActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = MenuActivity.class.getSimpleName();

    private ActivityMenuBinding binding;

    private FirebaseAnalyticsHelper mFirebaseAnalyticsHelper;
    private FirebaseAnalytics mFirebaseAnalytics;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_menu);
        binding.contentMenu.setClickListener(this);
        binding.emailSignOutButton.setOnClickListener(this);

        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        mFirebaseAnalyticsHelper = new FirebaseAnalyticsHelper(mFirebaseAnalytics);


        initializeFirebaseAuth();
        updateUserInfo();


    }

    private void updateUserInfo() {
        FirebaseUser user = mAuth.getCurrentUser();

        binding.contentMenu.userEmail.setText(String.format(getString(R.string.user_email), user.getEmail()));
        binding.contentMenu.userUid.setText(String.format(getString(R.string.user_uid), user.getUid()));

        LogHelper.log(TAG, "::userUid::" + user.getUid(), true);
        LogHelper.log(TAG, "::user image: " + user.getPhotoUrl(), true);
    }

    private void initializeFirebaseAuth(){
        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user == null) {
                    LogHelper.log(TAG, "onAuthStateChanged:signed_out", true);
                    navigateToLogin();
                }
            }
        };
    }

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(mAuthListener!=null){
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        switch(id){
            case R.id.btnAnalyze:
                mFirebaseAnalyticsHelper.logAnalyzeButtonClickedEvent();
                navigateToAnalytics();
                break;
            case R.id.btnAddSurvey:
                mFirebaseAnalyticsHelper.logSurveyButtonClickedEvent();
                navigateToSurvey();
                break;
            case R.id.email_sign_out_button:
                mFirebaseAnalyticsHelper.logSignOutButtonClickedEvent();
                mAuth.signOut();
                break;
        }

    }

    private void navigateToLogin() {
        Intent myIntent = new Intent(this, LoginActivity.class);
        startActivity(myIntent);
    }

    private void navigateToAnalytics() {
        //TODO make analytics
    }

    private void navigateToSurvey() {
        Intent myIntent = new Intent(this, SurveyActivity.class);
        startActivity(myIntent);
    }


}
