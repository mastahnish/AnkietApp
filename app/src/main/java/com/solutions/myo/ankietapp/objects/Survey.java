package com.solutions.myo.ankietapp.objects;

import java.util.Date;
import java.util.List;

/**
 * Created by Jacek on 2017-01-10.
 */

public class Survey {

    private List<Question> questions;
    private Date date;
    private Selfie selfie;

    public Survey() {
    }

    public Survey(List<Question> questions, Date date, Selfie selfie) {
        this.questions = questions;
        this.date = date;
        this.selfie = selfie;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Selfie getSelfie() {
        return selfie;
    }

    public void setSelfie(Selfie selfie) {
        this.selfie = selfie;
    }

    @Override
    public String toString() {
        return "Survey{" +
                "questions=" + questions +
                ", date=" + date +
                ", selfie=" + selfie +
                '}';
    }
}
