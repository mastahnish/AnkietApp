package com.solutions.myo.ankietapp.workflow.survey.fragments;


import android.Manifest;
import android.app.Activity;
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
import com.google.android.gms.vision.face.Face;
import com.solutions.myo.ankietapp.R;
import com.solutions.myo.ankietapp.analytics.logging.LogHelper;
import com.solutions.myo.ankietapp.databinding.FragmentPhotoBinding;
import com.solutions.myo.ankietapp.workflow.survey.SurveyActivity;
import com.solutions.myo.ankietapp.workflow.survey.camera.permissions.IPermissionsListener;
import com.solutions.myo.ankietapp.workflow.survey.camera.photo.PhotoHelper;
import com.solutions.myo.ankietapp.workflow.survey.camera.ui.CameraHelper;
import com.solutions.myo.ankietapp.workflow.survey.camera.ui.IFaceListener;
import com.solutions.myo.ankietapp.workflow.survey.data.ISurveyHolder;
import com.solutions.myo.ankietapp.workflow.survey.data.SurveyFlowMemory;


public class PhotoFragment extends Fragment implements IPermissionsListener, View.OnClickListener, IFaceListener {

    private static final String TAG = PhotoFragment.class.getSimpleName();

    private FragmentPhotoBinding binding;

    private CameraHelper mCameraHelper;

    private SurveyFlowMemory flowMemory;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_photo, container, false);
        binding.setClickListener(this);

        flowMemory = ((ISurveyHolder) getActivity()).getSurveyFlowMemory();

        mCameraHelper = new CameraHelper(getActivity(), binding.faceOverlay, binding.cameraPreview, this);

        int rc = ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA);
        if (rc == PackageManager.PERMISSION_GRANTED) {
            binding.fabTakePhoto.setVisibility(View.VISIBLE);
            mCameraHelper.createCameraSource();
        } else {
            LogHelper.log(Log.WARN, TAG, "Need to request camera permissions! ~Jacek", true);
            binding.fabTakePhoto.setVisibility(View.GONE);
            mCameraHelper.requestCameraPermission();
        }

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
            binding.fabTakePhoto.setVisibility(View.VISIBLE);
            mCameraHelper.createCameraSource();
//            mCameraHelper.startCameraSource();
        }
    }

    @Override
    public void onPermissionRejected() {
        binding.fabTakePhoto.setVisibility(View.GONE);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        Activity ac = getActivity();
        switch (id) {
            case R.id.fab_take_photo:
                LogHelper.log(TAG, "onClick::take_photo::", true);
                if(ac!=null){
                    ((SurveyActivity) ac).getmFirebaseAnalyticsHelper().logTakePhotoClickedEvent();
                }

                mCameraHelper.getmCameraSource().takePicture(null, pictureCallback);
                manageRetryPhotoVisibility(true);



                //TODO do przemyślenia:
          /*      if(StringUtils.isEmpty(flowMemory.getEncodedPhoto())){
                    mCameraHelper.getmCameraSource().takePicture(null, pictureCallback);
                    manageRetryPhotoVisibility(true);
                }else{
                    showOverwritePhotoDialog();
                }*/

                break;
            case R.id.fab_retake_photo:
                LogHelper.log(TAG, "onClick::retake_photo::", true);
                if(ac!=null){
                    ((SurveyActivity) ac).getmFirebaseAnalyticsHelper().logRetakePhotoClickedEvent();
                }


                if (mCameraHelper != null) {
                    mCameraHelper.startCameraSource();
                    manageRetryPhotoVisibility(false);
                }


                break;
        }
    }

    CameraSource.PictureCallback pictureCallback = new CameraSource.PictureCallback() {
        @Override
        public void onPictureTaken(byte[] bytes) {
            LogHelper.log(TAG, "onPictureTaken::bytes::" + bytes.toString(), true);
            flowMemory.setEncodedPhoto(PhotoHelper.encodePhotoBytes(bytes));
            mCameraHelper.stopCameraSource();
        }
    };

    private void manageRetryPhotoVisibility(boolean makeVisible){
        binding.fabRetakePhoto.setVisibility(makeVisible?View.VISIBLE:View.INVISIBLE);
    }

    @Override
    public void onFaceUpdated(Face face) {

        final int currentHappiness = Math.round(face.getIsSmilingProbability()*5);
        LogHelper.log(TAG, "::onFaceUpdated:: "  + String.valueOf(currentHappiness),  false);

        if(currentHappiness>=0 && currentHappiness != binding.happinessMeasure.getRating()) {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    binding.happinessMeasure.setRating(currentHappiness);
                }
            });
        }

    }


    //TODO do przemyślenia:
    //TODO style this dialog appropriately. Probably crete new DialogFragment here
   /* private void showOverwritePhotoDialog() {
        AlertDialog.Builder alert = new AlertDialog.Builder(getActivity())
                .setMessage(R.string.overwrite_photo)
                .setTitle(R.string.photo)
                .setIcon(R.drawable.take_photo)
                .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mCameraHelper.getmCameraSource().takePicture(null, pictureCallback);
                        manageRetryPhotoVisibility(true);
                    }
                })
                .setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });


        AlertDialog dialog_card = alert.create();

        dialog_card.getWindow().setGravity(Gravity.BOTTOM);
        dialog_card.show();
    }*/
}
