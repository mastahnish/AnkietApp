package com.solutions.myo.ankietapp.firebase.database.models;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;
import com.solutions.myo.ankietapp.firebase.database.IFirebaseStorage;
import com.solutions.myo.ankietapp.firebase.database.utils.ConvertHelper;
import com.solutions.myo.ankietapp.objects.Question;
import com.solutions.myo.ankietapp.objects.Selfie;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Jacek on 2017-01-12.
 */
@IgnoreExtraProperties
public class FSurvey {

    private String uid;
    private String username;
    private List<Question> questions;
    private Date date;
    private Selfie selfie;

    public FSurvey() {
    }

    public FSurvey(String uid, String username, List<Question> questions, Date date, Selfie selfie) {
        this.uid = uid;
        this.username = username;
        this.questions = questions;
        this.date = date;
        this.selfie = selfie;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put(IFirebaseStorage.ISurvey.UID, uid);
        result.put(IFirebaseStorage.ISurvey.USERNAME, username);
        result.put(IFirebaseStorage.ISurvey.DATE, String.valueOf(date.getTime()));
        result.put(IFirebaseStorage.ISurvey.SELFIE, FSelfie.toMap(selfie));
        result.put(IFirebaseStorage.ISurvey.QUESTIONS, ConvertHelper.convertObjectToListProperty(questions));
        return result;
    }


}
