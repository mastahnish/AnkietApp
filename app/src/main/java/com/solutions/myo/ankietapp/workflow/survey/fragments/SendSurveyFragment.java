package com.solutions.myo.ankietapp.workflow.survey.fragments;


import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.solutions.myo.ankietapp.R;
import com.solutions.myo.ankietapp.databinding.FragmentSendSurveyBinding;
import com.solutions.myo.ankietapp.workflow.survey.data.ISurveyHolder;

import java.util.Date;

public class SendSurveyFragment extends Fragment implements View.OnClickListener{

    private static final String TAG = SendSurveyFragment.class.getSimpleName();

    private FragmentSendSurveyBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_send_survey, container, false);
        binding.setClickListener(this);
        View root = binding.getRoot();
        return root;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        switch (id){
            case R.id.btnSendSurvey:
                Date currentDate = new Date(System.currentTimeMillis());
                ((ISurveyHolder) getActivity()).getSurveyFlowMemory().setSurveyDate(currentDate);

                ((ISurveyHolder) getActivity()).getSurveyDataManager().sendSurvey();

                break;
        }

    }


}
