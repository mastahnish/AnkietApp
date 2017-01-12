package com.solutions.myo.ankietapp.firebase.analytics;

import android.os.Bundle;

import com.google.firebase.analytics.FirebaseAnalytics;

/**
 * Created by Jacek on 2017-01-10.
 */

public class FirebaseAnalyticsHelper {

    private FirebaseAnalytics mFirebaseAnalytics;

    public FirebaseAnalyticsHelper(FirebaseAnalytics mFirebaseAnalytics) {
        this.mFirebaseAnalytics = mFirebaseAnalytics;
    }

    public void logStartButtonClickedEvent() {
        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.LOCATION, IFirebaseAnalytics.ILocation.SPLASH);
        bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, IFirebaseAnalytics.IButtons.START);
        mFirebaseAnalytics.logEvent(IFirebaseAnalytics.IEvent.START_BUTTON_CLICKED, bundle);
    }

    public void logLoginButtonClickedEvent() {
        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.LOCATION, IFirebaseAnalytics.ILocation.LOGIN);
        bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, IFirebaseAnalytics.IButtons.LOGIN);
        mFirebaseAnalytics.logEvent(IFirebaseAnalytics.IEvent.LOGIN_BUTTON_CLICKED, bundle);
    }

    public void logSignUpButtonClickedEvent() {
        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.LOCATION, IFirebaseAnalytics.ILocation.LOGIN);
        bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, IFirebaseAnalytics.IButtons.SIGN_UP);
        mFirebaseAnalytics.logEvent(IFirebaseAnalytics.IEvent.SIGN_UP_BUTTON_CLICKED, bundle);
    }

    public void logSignOutButtonClickedEvent() {
        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.LOCATION, IFirebaseAnalytics.ILocation.MENU);
        bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, IFirebaseAnalytics.IButtons.SIGN_OUT);
        mFirebaseAnalytics.logEvent(IFirebaseAnalytics.IEvent.SIGN_OUT_BUTTON_CLICKED, bundle);
    }


    public void logAnalyzeButtonClickedEvent() {
        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.LOCATION, IFirebaseAnalytics.ILocation.MENU);
        bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, IFirebaseAnalytics.IButtons.ANALYTICS);
        mFirebaseAnalytics.logEvent(IFirebaseAnalytics.IEvent.MENU_ANALYTICS_BUTTON_CLICKED, bundle);
    }

    public void logSurveyButtonClickedEvent(){
        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.LOCATION, IFirebaseAnalytics.ILocation.MENU);
        bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, IFirebaseAnalytics.IButtons.SURVEY);
        mFirebaseAnalytics.logEvent(IFirebaseAnalytics.IEvent.MENU_SURVEY_BUTTON_CLICKED, bundle);
    }

    public void logTakePhotoClickedEvent(){
        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.LOCATION, IFirebaseAnalytics.ILocation.SURVEY);
        bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, IFirebaseAnalytics.IButtons.TAKE_PHOTO);
        mFirebaseAnalytics.logEvent(IFirebaseAnalytics.IEvent.SURVEY_TAKE_PHOTO_BUTTON_CLICKED, bundle);
    }


    public void logRetakePhotoClickedEvent() {
        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.LOCATION, IFirebaseAnalytics.ILocation.SURVEY);
        bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, IFirebaseAnalytics.IButtons.RETAKE_PHOTO);
        mFirebaseAnalytics.logEvent(IFirebaseAnalytics.IEvent.SURVEY_RETAKE_PHOTO_BUTTON_CLICKED, bundle);
    }


    public void logSendSurveyClickedEvent() {
        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.LOCATION, IFirebaseAnalytics.ILocation.SURVEY);
        bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, IFirebaseAnalytics.IButtons.SEND_SURVEY);
        mFirebaseAnalytics.logEvent(IFirebaseAnalytics.IEvent.SURVEY_SEND_BUTTON_CLICKED, bundle);
    }
}
