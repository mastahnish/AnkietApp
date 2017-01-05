package com.solutions.myo.ankietapp.workflow.survey.camera.permissions;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.View;

import com.solutions.myo.ankietapp.R;
import com.solutions.myo.ankietapp.workflow.survey.camera.ui.GraphicOverlay;

import static com.solutions.myo.ankietapp.workflow.survey.camera.config.GMSConfig.RC_HANDLE_CAMERA_PERM;

/**
 * Created by Jacek on 2017-01-05.
 */

public class PermissionsHelper {

    private static final String TAG = PermissionsHelper.class.getSimpleName();

    private Context mContext;
    public GraphicOverlay mGraphicOverlay;

    public PermissionsHelper(Context context, GraphicOverlay graphicOverlay) {
        this.mContext = context;
        this.mGraphicOverlay = graphicOverlay;
    }

    public void requestCameraPermission() {
        Log.w(TAG, "Camera permission is not granted. Requesting permission");

        final String[] permissions = new String[]{Manifest.permission.CAMERA};

        final Activity ac = (Activity) mContext;
        if (ac != null) {


            if (!ActivityCompat.shouldShowRequestPermissionRationale(ac,
                    Manifest.permission.CAMERA)) {
                ActivityCompat.requestPermissions(ac, permissions, RC_HANDLE_CAMERA_PERM);
                return;
            }


            View.OnClickListener listener = new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ActivityCompat.requestPermissions(ac, permissions,
                            RC_HANDLE_CAMERA_PERM);
                }
            };

            Snackbar.make(mGraphicOverlay, ac.getResources().getString(R.string.permission_camera_rationale),
                    Snackbar.LENGTH_INDEFINITE)
                    .setAction(R.string.ok, listener)
                    .show();
        }
    }
}
