package com.solutions.myo.ankietapp.workflow.survey.camera.ui;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.MultiProcessor;
import com.google.android.gms.vision.Tracker;
import com.google.android.gms.vision.face.Face;
import com.google.android.gms.vision.face.FaceDetector;
import com.solutions.myo.ankietapp.workflow.survey.camera.config.GMSConfig;
import com.solutions.myo.ankietapp.workflow.survey.camera.permissions.PermissionsHelper;

import java.io.IOException;


public class CameraHelper extends PermissionsHelper {

    private static final String TAG = CameraHelper.class.getSimpleName();

    private Context mContext;

    private CameraSource mCameraSource;
    private CameraSourcePreview mCameraSourcePreview;

    public CameraHelper(Context context, GraphicOverlay graphicOverlay, CameraSourcePreview cameraSourcePreview) {
        super(context, graphicOverlay);
        this.mContext = context;
        this.mCameraSourcePreview = cameraSourcePreview;

    }

    public void createCameraSource(){
        FaceDetector detector = new FaceDetector.Builder(mContext)
                .setClassificationType(FaceDetector.ALL_CLASSIFICATIONS)
                .build();

        detector.setProcessor(
                new MultiProcessor.Builder<>(new GraphicFaceTrackerFactory(mGraphicOverlay))
                        .build());

        if (!detector.isOperational()) {
            // Note: The first time that an app using face API is installed on a device, GMS will
            // download a native library to the device in order to do detection.  Usually this
            // completes before the app is run for the first time.  But if that download has not yet
            // completed, then the above call will not detect any faces.
            //
            // isOperational() can be used to check if the required native library is currently
            // available.  The detector will automatically become operational once the library
            // download completes on device.
            Log.w(TAG, "Face detector dependencies are not yet available.");
        }

        mCameraSource = new CameraSource.Builder(mContext, detector)
                .setRequestedPreviewSize(640, 480)
                .setFacing(CameraSource.CAMERA_FACING_FRONT)
                .setRequestedFps(30.0f)
                .build();


    }

    private class GraphicFaceTrackerFactory implements MultiProcessor.Factory<Face> {

        private GraphicOverlay mOverlay;

        public GraphicFaceTrackerFactory(GraphicOverlay graphicOverlay) {
            mOverlay = graphicOverlay;

        }

        @Override
        public Tracker<Face> create(Face face) {
            return new GraphicFaceTracker(mOverlay);
        }
    }

    private class GraphicFaceTracker extends Tracker<Face> {
        private GraphicOverlay mOverlay;
        private FaceGraphic mFaceGraphic;

        GraphicFaceTracker(GraphicOverlay overlay) {
            mOverlay = overlay;
            mFaceGraphic = new FaceGraphic(overlay);
        }

        /**
         * Start tracking the detected face instance within the face overlay.
         */
        @Override
        public void onNewItem(int faceId, Face item) {
            mFaceGraphic.setId(faceId);
        }

        /**
         * Update the position/characteristics of the face within the overlay.
         */
        @Override
        public void onUpdate(FaceDetector.Detections<Face> detectionResults, Face face) {
            mOverlay.add(mFaceGraphic);
            mFaceGraphic.updateFace(face);
        }

        /**
         * Hide the graphic when the corresponding face was not detected.  This can happen for
         * intermediate frames temporarily (e.g., if the face was momentarily blocked from
         * view).
         */
        @Override
        public void onMissing(FaceDetector.Detections<Face> detectionResults) {
            mOverlay.remove(mFaceGraphic);
        }

        /**
         * Called when the face is assumed to be gone for good. Remove the graphic annotation from
         * the overlay.
         */
        @Override
        public void onDone() {
            mOverlay.remove(mFaceGraphic);
        }
    }

    public void startCameraSource() {

        // check that the device has play services available.
        int code = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(
                mContext.getApplicationContext());
        if (code != ConnectionResult.SUCCESS) {
            Activity ac = (Activity) mContext;
            if (ac != null) {
                Dialog dlg =
                        GoogleApiAvailability.getInstance().getErrorDialog(ac, code, GMSConfig.RC_HANDLE_GMS);
                dlg.show();
            }
        }

        if (mCameraSource != null) {
            try {
                mCameraSourcePreview.start(mCameraSource, mGraphicOverlay);
            } catch (IOException e) {
                Log.e(TAG, "Unable to start camera source.", e);
                mCameraSource.release();
                mCameraSource = null;
            }
        }
    }

    public void stopCameraSource() {
        if(mCameraSource !=null) {
            mCameraSourcePreview.stop();
        }
    }



    public CameraSource getmCameraSource() {
        return mCameraSource;
    }
}
