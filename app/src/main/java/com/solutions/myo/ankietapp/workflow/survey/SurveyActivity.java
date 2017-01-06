package com.solutions.myo.ankietapp.workflow.survey;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.solutions.myo.ankietapp.R;
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

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode != RC_HANDLE_CAMERA_PERM) {
            Log.d(TAG, "Got unexpected permission result: " + requestCode);
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
            return;
        }

        if (grantResults.length != 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            Log.d(TAG, "Camera permission granted - initialize the camera source");
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


}
