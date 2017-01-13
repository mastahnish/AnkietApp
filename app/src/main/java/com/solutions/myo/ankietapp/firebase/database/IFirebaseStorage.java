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

    interface IRootChildUpdates {
        String SURVEYS = "/surveys/";
    }

    interface IUser {
        String USERNAME = "username";
        String EMAIL = "email";
    }

    interface ISurvey {
        String UID = "uid";
        String USERNAME = "username";
        String DATE = "date";
        String SELFIE = "selfie";
        String QUESTIONS = "questions";
//        String Q1 = "q1";
//        String Q2 = "q2";
//        String Q3 = "q3";
//        String Q4 = "q4";
//        String Q5 = "q5";
//        String Q6 = "q6";

    }

    interface ISelfie {
        String SMILE_MEASURE = "smileMeasure";
        String PHOTO = "encodedPhoto";
    }

    interface IQuestion {
        String DESCRIPTION = "description";
        String QUESTION_NUMBER = "question_number";
        String RATING = "rating";
        String COMPLETED = "completed";
    }

    //FIXME think if it's compulsory
    interface ISurveyService {
        String USERNAME = "username";
        String PHOTO = "encodedPhoto";
        String SMILE_MEASURE = "smileMeasure";
        String DATE = "date";
    }
}
