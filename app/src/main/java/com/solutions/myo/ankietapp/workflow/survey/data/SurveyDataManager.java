package com.solutions.myo.ankietapp.workflow.survey.data;

import com.solutions.myo.ankietapp.objects.Question;
import com.solutions.myo.ankietapp.objects.Selfie;
import com.solutions.myo.ankietapp.objects.Survey;

import java.util.List;

//User for sending data to Firebase
public class SurveyDataManager {
    private static final String TAG = SurveyDataManager.class.getSimpleName();

    //TODO define argument dependnin db structure
    public void sendSurvey(){
        //TODO sendSurvey to Firebase
    }

    public List<Survey> getAllSurveys(){
        return null;
    }

    public List<Selfie> getAllSelfies(){
        return null;
    }

    public List<Question> getAllQuestions(String questionNumber){
        return null;
    }

}
