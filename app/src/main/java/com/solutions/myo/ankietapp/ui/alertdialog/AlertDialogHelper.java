package com.solutions.myo.ankietapp.ui.alertdialog;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.view.ContextThemeWrapper;

import com.solutions.myo.ankietapp.R;


public class AlertDialogHelper {

    private Context mContext;
    private IAlertDialogListener mDialogListener;
    private AlertDialog.Builder dialogBuilder;
    private AlertDialog alertDialog;


    public AlertDialogHelper(Context mContext) {
        this.mContext = mContext;
        this.mDialogListener = (IAlertDialogListener) mContext;
    }

    public void createAlertDialog(String title, String message) {
        dialogBuilder = new AlertDialog.Builder(new ContextThemeWrapper(
                mContext, R.style.ankietapp_dialog));
        dialogBuilder
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton(mContext.getResources().getString(R.string.yes),
                        new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                mDialogListener.onYesClicked();
                                dialog.dismiss();

                            }
                        })
                .setNegativeButton(mContext.getResources().getString(R.string.no),
                        new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                mDialogListener.onNoClicked();
                                dialog.dismiss();

                            }
                        });
        alertDialog = dialogBuilder.create();
        alertDialog.show();
    }


}
