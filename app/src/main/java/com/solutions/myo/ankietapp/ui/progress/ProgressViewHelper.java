package com.solutions.myo.ankietapp.ui.progress;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;

import com.solutions.myo.ankietapp.R;

/**
 * Created by Jacek on 2017-01-13.
 */

public class ProgressViewHelper {

    private ProgressDialog mProgressDialog;
    private Context mContext;

    public ProgressViewHelper(Context context) {
        this.mContext = context;
    }

    public void showProgressDialog(final String message) {


        if(mContext!=null){
            ((Activity) mContext).runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (mProgressDialog == null) {
                        mProgressDialog = new ProgressDialog(mContext, R.style.ankietapp_dialog);
                        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                        mProgressDialog.setProgressNumberFormat(null);
                        mProgressDialog.setProgressPercentFormat(null);
                        mProgressDialog.setIndeterminate(true);
                        mProgressDialog.setCancelable(false);

                        mProgressDialog.setMessage(message);

                    }

                    mProgressDialog.show();
                }
            });
        }


    }

    public void hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {

            if(mContext!=null){
                ((Activity) mContext).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        mProgressDialog.dismiss();
                    }
                });
            }

        }
    }

}
