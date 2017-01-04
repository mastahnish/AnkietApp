package com.solutions.myo.ankietapp.objects;

/**
 * Created by Jacek on 2017-01-04.
 */

public class Question {

    private String description;
    private int questionNumber;
    private int rating;
    private boolean completed;

    public Question() {
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public int getQuestionNumber() {
        return questionNumber;
    }

    public void setQuestionNumber(int questionNumber) {
        this.questionNumber = questionNumber;
    }

    @Override
    public String toString() {
        return "Question{" +
                "description='" + description + '\'' +
                ", questionNumber=" + questionNumber +
                ", rating=" + rating +
                ", completed=" + completed +
                '}';
    }
}
