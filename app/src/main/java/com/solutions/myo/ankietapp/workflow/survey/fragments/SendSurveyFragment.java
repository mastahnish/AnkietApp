package com.solutions.myo.ankietapp.workflow.survey.fragments;


import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.solutions.myo.ankietapp.R;
import com.solutions.myo.ankietapp.logging.LogHelper;
import com.solutions.myo.ankietapp.databinding.FragmentSendSurveyBinding;
import com.solutions.myo.ankietapp.workflow.survey.SurveyActivity;
import com.solutions.myo.ankietapp.workflow.survey.data.ISurveyHolder;
import com.solutions.myo.ankietapp.workflow.survey.data.SurveyFlowMemory;

import java.util.Date;

public class SendSurveyFragment extends Fragment implements View.OnClickListener{

    private static final String TAG = SendSurveyFragment.class.getSimpleName();

    private FragmentSendSurveyBinding binding;

    private SurveyFlowMemory flowMemory;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_send_survey, container, false);
        binding.setClickListener(this);
        flowMemory = ((ISurveyHolder) getActivity()).getSurveyFlowMemory();
        View root = binding.getRoot();
        return root;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        switch (id){
            case R.id.btnSendSurvey:
                LogHelper.log(TAG, "onClick::send_survey::", true);
                Activity ac = getActivity();
                if(ac!=null){
                    ((SurveyActivity) ac).getmFirebaseAnalyticsHelper().logSendSurveyClickedEvent();
                }


                Date currentDate = new Date(System.currentTimeMillis());
                flowMemory.setSurveyDate(currentDate);

                ((ISurveyHolder) getActivity()).getSurveyDataManager().sendSurvey();

                break;
        }

    }


}
