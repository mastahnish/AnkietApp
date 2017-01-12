package com.solutions.myo.ankietapp.workflow.survey;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.solutions.myo.ankietapp.R;
import com.solutions.myo.ankietapp.firebase.analytics.FirebaseAnalyticsHelper;
import com.solutions.myo.ankietapp.logging.LogHelper;
import com.solutions.myo.ankietapp.common.BaseStateManager;
import com.solutions.myo.ankietapp.databinding.ActivitySurveyBinding;
import com.solutions.myo.ankietapp.ui.breadcrumb.IAnimationListener;
import com.solutions.myo.ankietapp.workflow.survey.camera.permissions.IPermissionsListener;
import com.solutions.myo.ankietapp.workflow.survey.data.ISurveyHolder;
import com.solutions.myo.ankietapp.workflow.survey.data.SurveyDataManager;
import com.solutions.myo.ankietapp.workflow.survey.data.SurveyFlowMemory;
import com.solutions.myo.ankietapp.workflow.survey.state.SurveyStateManager;

import static com.solutions.myo.ankietapp.workflow.survey.camera.config.GMSConfig.RC_HANDLE_CAMERA_PERM;

public class SurveyActivity extends AppCompatActivity implements BaseStateManager.IStateChangeListener, View.OnClickListener, ISurveyHolder{

    private static final String TAG = SurveyActivity.class.getSimpleName();

    private ActivitySurveyBinding binding;

    private BaseStateManager.BaseState mState;

    private Integer cacheCurrentStep;

    private SurveyFlowMemory flowMemory;

    private SurveyDataManager surveyDataManager;

    private FirebaseAnalyticsHelper mFirebaseAnalyticsHelper;
    private FirebaseAnalytics mFirebaseAnalytics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        mFirebaseAnalyticsHelper = new FirebaseAnalyticsHelper(mFirebaseAnalytics);

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
        return mState;
    }

    private void modifyBreadcrumb(final int event) {
        switch(event){
            case BaseStateManager.EVENT_PREVIOUS:
                if(binding.breadcrumbs.getCurrentStep()> 0){
                    binding.breadcrumbs.prevStep(new IAnimationListener() {
                        @Override
                        public void onAnimationFinished() {
                            mState = mState.processState(event);

                        }
                    });
                }else{
                    mState = mState.processState(event);
                }
                break;
            case BaseStateManager.EVENT_NEXT:
                if(binding.breadcrumbs.getCurrentStep() < getResources().getInteger(R.integer.breadcrumbSize)-1) {
                    binding.breadcrumbs.nextStep(new IAnimationListener() {
                        @Override
                        public void onAnimationFinished() {
                            mState = mState.processState(event);
                        }
                    });
                }else{
                    mState = mState.processState(event);
                }
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
                LogHelper.log(TAG, " moving NEXT::data in flowmemory: " + flowMemory.toString(), true);

                if(mState instanceof SurveyStateManager.SendSurveyState){
                    showFinishSurveyProcess();
                }else{
                    processState(BaseStateManager.EVENT_NEXT);
                }

                break;
            case R.id.bt_prev:
                LogHelper.log(TAG, " moving PREVIOUS", true);

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

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode != RC_HANDLE_CAMERA_PERM) {
            LogHelper.log(TAG, "Got unexpected permission result: " + requestCode, true);
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
            return;
        }

        if (grantResults.length != 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            LogHelper.log(TAG, "Camera permission granted - initialize the camera source", true);
            // we have permission, so create the camerasource
            if(getCurrentFragment() instanceof IPermissionsListener){
                ((IPermissionsListener) getCurrentFragment()).onPermissionGranted();
            }

            return;
        }

        Log.e(TAG, "Permission not granted: results len = " + grantResults.length +
                " Result code = " + (grantResults.length > 0 ? grantResults[0] : "(empty)"));

        DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

                if(getCurrentFragment() instanceof IPermissionsListener){
                    ((IPermissionsListener) getCurrentFragment()).onPermissionRejected();
                }
//                finish();
            }
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getResources().getString(R.string.app_name))
                .setMessage(R.string.no_camera_permission)
                .setPositiveButton(R.string.ok, listener)
                .show();
    }

    protected Fragment getCurrentFragment() {
        return getSupportFragmentManager().findFragmentById(R.id.fragmentContainer);
    }


    private void showFinishSurveyProcess() {
        AlertDialog.Builder alert = new AlertDialog.Builder(this)
                .setMessage(R.string.finish_survey_question)
                .setTitle(R.string.finish_survey)
                .setIcon(android.R.drawable.ic_menu_close_clear_cancel)
                .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        LogHelper.log(TAG, "showFinishSurveyProcess::yes_clicked::", true);
                        processState(BaseStateManager.EVENT_NEXT);
                    }
                })
                .setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        LogHelper.log(TAG, "showFinishSurveyProcess::no_clicked::", true);
                        dialog.dismiss();
                    }
                });


        AlertDialog dialog_card = alert.create();

        dialog_card.getWindow().setGravity(Gravity.CENTER);
        dialog_card.show();
    }


    public FirebaseAnalyticsHelper getmFirebaseAnalyticsHelper() {
        return mFirebaseAnalyticsHelper;
    }

}
