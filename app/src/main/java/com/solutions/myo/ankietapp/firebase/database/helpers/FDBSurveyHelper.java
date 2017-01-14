package com.solutions.myo.ankietapp.firebase.database.helpers;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.firebase.database.FirebaseDatabase;
import com.solutions.myo.ankietapp.firebase.database.IFirebaseStorage;
import com.solutions.myo.ankietapp.firebase.database.models.FSurvey;
import com.solutions.myo.ankietapp.logging.LogHelper;
import com.solutions.myo.ankietapp.objects.Survey;

import java.util.HashMap;
import java.util.Map;

/**
 * All methods to manipulate surveys will be here
 */

public class FDBSurveyHelper {

    private static final String TAG = FDBSurveyHelper.class.getSimpleName();

    public static void insertNewSurvey(String uid, String username, Survey survey, OnFailureListener failureListener, OnCompleteListener successListener){
        LogHelper.log(TAG, "::insertNewSurvey::", true);

        String key = FirebaseDatabase.getInstance().getReference().child(IFirebaseStorage.IRootChild.SURVEYS).push().getKey();
        //TODO create separate model for Firebase Updates
        FSurvey fSurvey = new FSurvey(key, uid, username, survey.getQuestions(), survey.getDate(), survey.getSelfie());
        Map<String, Object> surveyValues = fSurvey.toMap();

        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put(IFirebaseStorage.IRootChildUpdates.SURVEYS + uid + "/" + key, surveyValues);

        FirebaseDatabase.getInstance().getReference().updateChildren(childUpdates).addOnFailureListener(failureListener).addOnCompleteListener(successListener);

    }

}
