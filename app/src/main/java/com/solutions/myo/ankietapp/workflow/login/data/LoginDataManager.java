package com.solutions.myo.ankietapp.workflow.login.data;

import com.google.firebase.auth.FirebaseUser;
import com.solutions.myo.ankietapp.logging.LogHelper;
import com.solutions.myo.ankietapp.firebase.database.helpers.FDBUserHelper;
import com.solutions.myo.ankietapp.workflow.survey.data.SurveyDataManager;

public class LoginDataManager {

    private static final String TAG = SurveyDataManager.class.getSimpleName();

    public void writeNewUser(FirebaseUser user){
        LogHelper.log(TAG, "::writeNewUser::", true);
        FDBUserHelper.addUser(user);

    }

}
