package com.solutions.myo.ankietapp.workflow.menu;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.FirebaseUser;
import com.solutions.myo.ankietapp.R;
import com.solutions.myo.ankietapp.firebase.analytics.FirebaseAnalyticsHelper;
import com.solutions.myo.ankietapp.common.BaseActivity;
import com.solutions.myo.ankietapp.common.IAuthAction;
import com.solutions.myo.ankietapp.databinding.ActivityMenuBinding;
import com.solutions.myo.ankietapp.logging.LogHelper;
import com.solutions.myo.ankietapp.workflow.survey.SurveyActivity;

public class MenuActivity extends BaseActivity implements View.OnClickListener {

    private static final String TAG = MenuActivity.class.getSimpleName();

    private ActivityMenuBinding binding;

    private FirebaseAnalyticsHelper mFirebaseAnalyticsHelper;
    private FirebaseAnalytics mFirebaseAnalytics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_menu);
        binding.contentMenu.setClickListener(this);
        binding.emailSignOutButton.setOnClickListener(this);

        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        mFirebaseAnalyticsHelper = new FirebaseAnalyticsHelper(mFirebaseAnalytics);

        updateUserInfo();


    }

    private void updateUserInfo() {
        FirebaseUser user = getFirebaseAuth().getCurrentUser();

        binding.contentMenu.userEmail.setText(String.format(getString(R.string.user_email), user.getEmail()));
        binding.contentMenu.userUid.setText(String.format(getString(R.string.user_uid), user.getUid()));

        LogHelper.log(TAG, "::userUid::" + user.getUid(), true);
        LogHelper.log(TAG, "::user image: " + user.getPhotoUrl(), true);
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
                setAuthAction(IAuthAction.SIGN_OUT);
                getFirebaseAuth().signOut();
                break;
        }

    }


    @Override
    protected void onStart() {
        super.onStart();
        LogHelper.log(TAG, "::onStart!", true);
    }

    @Override
    protected void onStop() {
        super.onStop();
        LogHelper.log(TAG, "::onStop!", true);
    }

     private void navigateToAnalytics() {
        //TODO make analytics
    }

    private void navigateToSurvey() {
        Intent myIntent = new Intent(this, SurveyActivity.class);
        startActivity(myIntent);
    }


}
