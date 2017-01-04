package com.solutions.myo.ankietapp.workflow.survey.fragments;


import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.eugeneek.smilebar.SmileBar;
import com.solutions.myo.ankietapp.R;
import com.solutions.myo.ankietapp.databinding.FragmentQuestionBinding;
import com.solutions.myo.ankietapp.objects.Question;
import com.solutions.myo.ankietapp.utils.StringUtils;
import com.solutions.myo.ankietapp.workflow.survey.data.ISurveyHolder;

import java.util.List;

public class QuestionFragment extends Fragment implements SmileBar.OnRatingSliderChangeListener {

    private static final String TAG = QuestionFragment.class.getSimpleName();

    private static final String QUESTION_TEXT_KEY = TAG + "_Question";
    private static final String QUESTION_NUMBER_KEY = TAG + "_QuestionNumber";

    private FragmentQuestionBinding binding;

    private Question question;

    private int questionNumber = 0;
    private String questionString;

    private Question cachedQuestion;

    public static Bundle generateArgument(String question, int number) {
        Log.d(TAG, "getInstance::question:: " + question );
        Bundle arg = new Bundle();
        arg.putString(QUESTION_TEXT_KEY, question);
        arg.putInt(QUESTION_NUMBER_KEY, number);
        return arg;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_question, container, false);
        Log.d(TAG, "onCreateView!");

        Bundle arg = getArguments();

        questionString = getQuestion(arg);

        question = new Question();

        if(!StringUtils.isEmpty(questionString)){
            Log.d(TAG, "getQuestion()!=null");
            binding.questionContent.question.setText(questionString);
            binding.questionContent.starBar.setOnRatingSliderChangeListener(this);
            question.setDescription(questionString);
        }

        questionNumber = getQuestionNumber(arg);

        if(questionNumber!=0){
            question.setQuestionNumber(questionNumber);
        }

        cachedQuestion = checkQuestionInMemory();

        View view = binding.getRoot();
        return view;
    }

    private String getQuestion(Bundle arg){
        String question = null;

        if(arg !=null){
            question = arg.getString(QUESTION_TEXT_KEY);
            Log.d(TAG, "getQuestion: question : " + question);
        }

        return question ;
    }

    private int getQuestionNumber(Bundle arg){
        int number = 0;

        if(arg !=null){
            number = arg.getInt(QUESTION_NUMBER_KEY);
            Log.d(TAG, "getQuestion: questionNumber : " + questionNumber);
        }
        return number;
    }

    private Question checkQuestionInMemory(){
        List<Question> questionList = ((ISurveyHolder) getActivity()).getSurveyFlowMemory().getCurrentQuestionList();

        Question currentQuestion = null;

        if(questionList !=null && !questionList.isEmpty()){
            for(Question question : questionList ){
                if(question.getQuestionNumber() == questionNumber){
                    currentQuestion = question;
                }
            }
        }

        return currentQuestion;
    }

    @Override
    public void onResume() {
        super.onResume();
        propagateCachedQuestion();
    }

    private void propagateCachedQuestion(){
        if(cachedQuestion!=null){
            binding.questionContent.starBar.setRating(cachedQuestion.getRating());
        }
    }

    @Override
    public void onPendingRating(int i) {
        Log.d(TAG, "onPendingRating!");
    }

    @Override
    public void onFinalRating(int i) {
        Log.d(TAG, "onFinalRating!:: i : " + i );
        if(question!=null){
            question.setRating(i);
            question.setCompleted(i!=0);
        }


    }

    @Override
    public void onCancelRating() {
        Log.d(TAG, "onCancelRating!");

    }

    @Override
    public void onPause() {
        super.onPause();
        ((ISurveyHolder) getActivity()).getSurveyFlowMemory().setCurrentQuestion(question);
    }
}
