package com.solutions.myo.ankietapp.workflow.survey.data;

import com.solutions.myo.ankietapp.objects.Question;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Jacek on 2017-01-04.
 */
public class SurveyFlowMemory {

    private static final String TAG = SurveyFlowMemory.class.getSimpleName();

    private Date surveyDate;

    private Question currentQuestion;

    private List<Question> currentQuestionList;

    public Question getCurrentQuestion() {
        return currentQuestion;
    }

    public void setCurrentQuestion(Question currentQuestion) {

        if(currentQuestion!=null){
            if(currentQuestionList == null || currentQuestionList.isEmpty()){
                currentQuestionList = new ArrayList<>();
                currentQuestionList.add(currentQuestion);
            }else{
                currentQuestionList.add(currentQuestion);
            }
        }



        this.currentQuestion = currentQuestion;
    }

    public List<Question> getCurrentQuestionList() {
        return currentQuestionList;
    }

    public void setCurrentQuestionList(List<Question> currentQuestionList) {
        this.currentQuestionList = currentQuestionList;
    }

    public Date getSurveyDate() {
        return surveyDate;
    }

    public void setSurveyDate(Date surveyDate) {
        this.surveyDate = surveyDate;
    }
}
