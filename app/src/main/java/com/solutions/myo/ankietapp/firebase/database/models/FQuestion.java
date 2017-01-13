package com.solutions.myo.ankietapp.firebase.database.models;

import com.google.firebase.database.Exclude;
import com.solutions.myo.ankietapp.firebase.database.utils.ConvertHelper;
import com.solutions.myo.ankietapp.objects.Question;

import java.util.Map;

/**
 * Created by Jacek on 2017-01-13.
 */

public class FQuestion {

    private String description;
    private int questionNumber;
    private int rating;
    private boolean completed;

    public FQuestion(String description, int questionNumber, int rating, boolean completed) {
        this.description = description;
        this.questionNumber = questionNumber;
        this.rating = rating;
        this.completed = completed;
    }

    public FQuestion() {
    }

    @Exclude
    public Map<String, Object> toMap(Question question) {
/*        HashMap<String, Object> result = new HashMap<>();
        result.put(IFirebaseStorage.IQuestion.DESCRIPTION, description);
        result.put(IFirebaseStorage.IQuestion.QUESTION_NUMBER, String.valueOf(questionNumber));
        result.put(IFirebaseStorage.IQuestion.RATING, String.valueOf(rating));
        result.put(IFirebaseStorage.IQuestion.COMPLETED, String.valueOf(completed));
        return result;*/
        return ConvertHelper.convertObjectToProperty(question);
    }
}
