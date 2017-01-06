package com.solutions.myo.ankietapp.workflow.survey.fragments;


import android.Manifest;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.vision.CameraSource;
import com.solutions.myo.ankietapp.R;
import com.solutions.myo.ankietapp.databinding.FragmentPhotoBinding;
import com.solutions.myo.ankietapp.utils.StringUtils;
import com.solutions.myo.ankietapp.workflow.survey.camera.permissions.IPermissionsListener;
import com.solutions.myo.ankietapp.workflow.survey.camera.photo.PhotoHelper;
import com.solutions.myo.ankietapp.workflow.survey.camera.ui.CameraHelper;
import com.solutions.myo.ankietapp.workflow.survey.data.ISurveyHolder;
import com.solutions.myo.ankietapp.workflow.survey.data.SurveyFlowMemory;


public class PhotoFragment extends Fragment implements IPermissionsListener, View.OnClickListener {

    private static final String TAG = PhotoFragment.class.getSimpleName();

    private FragmentPhotoBinding binding;

    private CameraHelper mCameraHelper;

    private SurveyFlowMemory flowMemory;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_photo, container, false);
        binding.setClickListener(this);
        mCameraHelper = new CameraHelper(getActivity(), binding.faceOverlay, binding.cameraPreview);

        int rc = ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA);
        if (rc == PackageManager.PERMISSION_GRANTED) {
            mCameraHelper.createCameraSource();
        } else {
            Log.w(TAG, "Need to request camera permissions! ~Jacek");
            mCameraHelper.requestCameraPermission();
        }

        flowMemory = ((ISurveyHolder) getActivity()).getSurveyFlowMemory();

        View root = binding.getRoot();
        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mCameraHelper != null) {
            mCameraHelper.startCameraSource();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        binding.cameraPreview.stop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mCameraHelper != null) {
            if (mCameraHelper.getmCameraSource() != null) {
                mCameraHelper.getmCameraSource().release();
            }
        }
    }


    @Override
    public void onPermissionGranted() {
        if (mCameraHelper != null) {
            mCameraHelper.createCameraSource();
//            mCameraHelper.startCameraSource();
        }
    }

    @Override
    public void onPermissionRejected() {

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        switch (id) {
            case R.id.fab_take_photo:
                mCameraHelper.getmCameraSource().takePicture(shutterCallback, pictureCallback);
                break;
            case R.id.fab_retake_photo:
                if (mCameraHelper != null) {
                    mCameraHelper.startCameraSource();
                }
                break;
        }
    }

    CameraSource.PictureCallback pictureCallback = new CameraSource.PictureCallback() {
        @Override
        public void onPictureTaken(byte[] bytes) {
            Log.d(TAG, "onPictureTaken::bytes::" + bytes.toString());
            flowMemory.setEncodedPhoto(PhotoHelper.encodePhotoBytes(bytes));
        }
    };

    CameraSource.ShutterCallback shutterCallback = new CameraSource.ShutterCallback() {
        @Override
        public void onShutter() {
            mCameraHelper.stopCameraSource();
        }
    };


    //TODO check situation when photo is taken. If is then show the photo
    private void checkIfPhotoIsTaken(){
        if(!StringUtils.isEmpty(flowMemory.getEncodedPhoto())){

        }
    }
}
