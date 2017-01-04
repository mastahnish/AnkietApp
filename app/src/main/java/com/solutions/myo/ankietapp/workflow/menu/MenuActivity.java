package com.solutions.myo.ankietapp.workflow.menu;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.solutions.myo.ankietapp.R;
import com.solutions.myo.ankietapp.databinding.ActivityMenuBinding;
import com.solutions.myo.ankietapp.workflow.survey.SurveyActivity;

public class MenuActivity extends AppCompatActivity implements View.OnClickListener {

    private ActivityMenuBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_menu);
        binding.contentMenu.setClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        switch(id){
            case R.id.btnAnalyze:
                navigateToAnalytics();
                break;
            case R.id.btnAddSurvey:
                navigateToSurvey();
                break;
        }

    }

    private void navigateToAnalytics() {
        //TODO make analytics
    }

    private void navigateToSurvey() {
        Intent myIntent = new Intent(this, SurveyActivity.class);
        startActivity(myIntent);
    }
}
