package com.solutions.myo.ankietapp.firebase.database;

/**
 * Created by Jacek on 2017-01-12.
 */

public interface IFirebaseStorage {

    interface IRootChild {
        String SURVEY_SELFIES = "survey-selfies";
        String SURVEYS = "surveys";
        String USERS = "users";
    }

    interface IUser {
        String USERNAME = "username";
        String EMAIL = "email";
    }

    interface ISurvey {
        String USERNAME = "username";
        String Q1 = "q1";
        String Q2 = "q2";
        String Q3 = "q3";
        String Q4 = "q4";
        String Q5 = "q5";
        String Q6 = "q6";
        String SMILE_MEASURE = "smileMeasure";
        String PHOTO = "encodedPhoto";
        String DATE = "date";
    }

    //FIXME think if it's compulsory
    interface ISurveyService {
        String USERNAME = "username";
        String PHOTO = "encodedPhoto";
        String SMILE_MEASURE = "smileMeasure";
        String DATE = "date";
    }
}
