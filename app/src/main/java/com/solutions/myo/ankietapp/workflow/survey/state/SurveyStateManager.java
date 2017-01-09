package com.solutions.myo.ankietapp.workflow.survey.state;

import android.app.Activity;
import android.support.v4.app.FragmentManager;
import android.util.Log;

import com.solutions.myo.ankietapp.R;
import com.solutions.myo.ankietapp.common.BaseStateManager;
import com.solutions.myo.ankietapp.workflow.survey.fragments.PhotoFragment;
import com.solutions.myo.ankietapp.workflow.survey.fragments.QuestionFragment;
import com.solutions.myo.ankietapp.workflow.survey.fragments.SendSurveyFragment;

/**
 * Created by Jacek on 2017-01-04.
 */

public class SurveyStateManager extends BaseStateManager{

    private static final String TAG = SurveyStateManager.class.getSimpleName();

    private FirstQuestionState mFirstQuestionState;
    private SecondQuestionState mSecondQuestionState;
    private ThirdQuestionState mThirdQuestionState;
    private FourthQuestionState mFourthQuestionState;
    private FifthQuestionState mFifthQuestionState;
    private SixthQuestionState mSixthQuestionState;
    private PhotoState mPhotoState;
    private SendSurveyState mSendSurveyState;




    protected SurveyStateManager(FragmentManager fragmentManager, Activity activity) {
        super(fragmentManager, activity);



        if(mFirstQuestionState == null){
            mFirstQuestionState = new FirstQuestionState ();
            mSecondQuestionState = new SecondQuestionState ();
            mThirdQuestionState = new ThirdQuestionState ();
            mFourthQuestionState = new FourthQuestionState ();
            mFifthQuestionState = new FifthQuestionState ();
            mSixthQuestionState = new SixthQuestionState ();
            mPhotoState = new PhotoState ();
            mSendSurveyState = new SendSurveyState ();

        }

    }

    private static SurveyStateManager mInstance;

    public static BaseState initState(FragmentManager fragmentManager, Activity activity) {

        mInstance = new SurveyStateManager(fragmentManager, activity);

        BaseState initialState = mInstance.getInitialState();
        initialState.enter();
        return initialState;
    }

    @Override
    protected BaseState getInitialState() {
        return mFirstQuestionState;
    }

    private class FirstQuestionState extends BaseState {

        @Override
        public void enter() {
            Log.d(TAG, "mActivity.getString(R.string.question_first): " + mActivity.getString(R.string.question_first));
            addNewFragment(new QuestionFragment(), QuestionFragment.generateArgument(mActivity.getString(R.string.question_first),1));
        }

        @Override
        public void exit(int event) {

        }

        @Override
        public BaseState nextState(int event) {
            switch (event) {
                case EVENT_NEXT:
                    return mSecondQuestionState;
                case EVENT_PREVIOUS:
                    return mFinishProcessState;
                case EVENT_FINISH:
                    return mFinishProcessState;
                default:
                    return this;
            }
        }
    }


    private class SecondQuestionState extends BaseState {

        @Override
        public void enter() {
            addNewFragment(new QuestionFragment(), QuestionFragment.generateArgument(mActivity.getString(R.string.question_second),2));
        }

        @Override
        public void exit(int event) {

        }

        @Override
        public BaseState nextState(int event) {
            switch (event) {
                case EVENT_NEXT:
                    return mThirdQuestionState;
                case EVENT_PREVIOUS:
                    return mFirstQuestionState;
                default:
                    return this;
            }
        }
    }

    private class ThirdQuestionState extends BaseState {

        @Override
        public void enter() {
            addNewFragment(new QuestionFragment(), QuestionFragment.generateArgument(mActivity.getString(R.string.question_third),3));
        }

        @Override
        public void exit(int event) {

        }

        @Override
        public BaseState nextState(int event) {
            switch (event) {
                case EVENT_NEXT:
                    return mFourthQuestionState;
                case EVENT_PREVIOUS:
                    return mSecondQuestionState;
                default:
                    return this;
            }
        }
    }

    private class FourthQuestionState extends BaseState {

        @Override
        public void enter() {
            addNewFragment(new QuestionFragment(), QuestionFragment.generateArgument(mActivity.getString(R.string.question_fourth),4));
        }

        @Override
        public void exit(int event) {

        }

        @Override
        public BaseState nextState(int event) {
            switch (event) {
                case EVENT_NEXT:
                    return mFifthQuestionState;
                case EVENT_PREVIOUS:
                    return mThirdQuestionState;
                default:
                    return this;
            }
        }
    }

    private class FifthQuestionState extends BaseState {

        @Override
        public void enter() {
            addNewFragment(new QuestionFragment(), QuestionFragment.generateArgument(mActivity.getString(R.string.question_fifth),5));
        }

        @Override
        public void exit(int event) {

        }

        @Override
        public BaseState nextState(int event) {
            switch (event) {
                case EVENT_NEXT:
                    return mSixthQuestionState;
                case EVENT_PREVIOUS:
                    return mFourthQuestionState;
                default:
                    return this;
            }
        }
    }

    private class SixthQuestionState extends BaseState {

        @Override
        public void enter() {
            addNewFragment(new QuestionFragment(), QuestionFragment.generateArgument(mActivity.getString(R.string.question_sixth),6));
        }

        @Override
        public void exit(int event) {

        }

        @Override
        public BaseState nextState(int event) {
            switch (event) {
                case EVENT_NEXT:
                    return mPhotoState;
                case EVENT_PREVIOUS:
                    return mFifthQuestionState;
                default:
                    return this;
            }
        }
    }

    private class PhotoState extends BaseState {

        @Override
        public void enter() {
            addNewFragment(new PhotoFragment());
        }

        @Override
        public void exit(int event) {

        }

        @Override
        public BaseState nextState(int event) {
            switch (event) {
                case EVENT_NEXT:
                    return mSendSurveyState;
                case EVENT_PREVIOUS:
                    return mSixthQuestionState;
                default:
                    return this;
            }
        }
    }

    public class SendSurveyState extends BaseState {

        @Override
        public void enter() {
            addNewFragment(new SendSurveyFragment());
        }

        @Override
        public void exit(int event) {

        }

        @Override
        public BaseState nextState(int event) {
            switch (event) {
                case EVENT_NEXT:
                    return mFinishProcessState;
                case EVENT_PREVIOUS:
                    return mPhotoState;
                default:
                    return this;
            }
        }
    }

}
