package com.solutions.myo.ankietapp.workflow.survey;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.solutions.myo.ankietapp.R;
import com.solutions.myo.ankietapp.common.BaseStateManager;
import com.solutions.myo.ankietapp.databinding.ActivitySurveyBinding;
import com.solutions.myo.ankietapp.workflow.survey.data.ISurveyHolder;
import com.solutions.myo.ankietapp.workflow.survey.data.SurveyDataManager;
import com.solutions.myo.ankietapp.workflow.survey.data.SurveyFlowMemory;
import com.solutions.myo.ankietapp.workflow.survey.state.SurveyStateManager;

public class SurveyActivity extends AppCompatActivity implements BaseStateManager.IStateChangeListener, View.OnClickListener, ISurveyHolder {

    private ActivitySurveyBinding binding;

    private BaseStateManager.BaseState mState;

    private Integer cacheCurrentStep;

    private SurveyFlowMemory flowMemory;

    private SurveyDataManager surveyDataManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_survey);
        binding.setClickListener(this);

        initBreadcrumb();

        mState = SurveyStateManager.initState(getSupportFragmentManager(), this);

        flowMemory = new SurveyFlowMemory();
        surveyDataManager = new SurveyDataManager();
    }

    @Override
    public BaseStateManager.BaseState processState(int event) {

        modifyBreadcrumb(event);

        mState = mState.processState(event);



        return mState;
    }

    private void modifyBreadcrumb(int event) {
        switch(event){
            case BaseStateManager.EVENT_PREVIOUS:
                if(binding.breadcrumbs.getCurrentStep()> 0) binding.breadcrumbs.prevStep();
                break;
            case BaseStateManager.EVENT_NEXT:
                if(binding.breadcrumbs.getCurrentStep() < getResources().getInteger(R.integer.breadcrumbSize)-1) binding.breadcrumbs.nextStep();
                break;
        }
    }

    private void initBreadcrumb(){
        if (getLastCustomNonConfigurationInstance() == null) {
            cacheCurrentStep = 0;
        } else {
            cacheCurrentStep = (Integer) getLastCustomNonConfigurationInstance();
        }
        binding.breadcrumbs.setCurrentStep(cacheCurrentStep);
    }

    @Override
    public Object onRetainCustomNonConfigurationInstance() {
        return binding.breadcrumbs.getCurrentStep();
    }

    @Override
    public void onBackPressed() {
        processState(BaseStateManager.EVENT_PREVIOUS);
        super.onBackPressed();
    }


    @Override
    public void onClick(View v) {
        int id = v.getId();

        switch(id){
            case R.id.bt_next:
                processState(BaseStateManager.EVENT_NEXT);
                break;
            case R.id.bt_prev:
                processState(BaseStateManager.EVENT_PREVIOUS);
                break;
        }
    }

    @Override
    public SurveyFlowMemory getSurveyFlowMemory() {
        return flowMemory;
    }

    @Override
    public SurveyDataManager getSurveyDataManager() {
        return surveyDataManager;
    }
}
