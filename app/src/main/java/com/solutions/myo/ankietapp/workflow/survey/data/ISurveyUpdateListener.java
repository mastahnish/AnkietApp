package com.solutions.myo.ankietapp.workflow.survey.data;

/**
 * Created by Jacek on 2017-01-12.
 */

public interface ISurveyUpdateListener {

    void onSuccess();
    void onUnexpectedFailure();
    void onFailure(Object error);

}
