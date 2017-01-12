package com.solutions.myo.ankietapp.logging;

import android.content.Context;
import android.util.Log;

import com.google.firebase.crash.FirebaseCrash;

/**
 * Created by Jacek on 2017-01-10.
 */

public class LogHelper {

    private static final String TAG = LogHelper.class.getSimpleName() + "::";

    private static LogHelper mInstance;

    public LogHelper() {
    }

    public static void init(Context context){
        if(mInstance==null){
            mInstance = new LogHelper();
        }
    }

    /**
        Method allows to specify logType
     **/
    public static void log(int logType, String tag, String message, boolean showInFirebase){
        if(showInFirebase){
            FirebaseCrash.log(TAG + tag + message);
        }
        FirebaseCrash.logcat(logType, TAG+tag, message);
    }

    /**
      Method that logs Log.DEBUG type by default
   **/
    public static void log(String tag, String message, boolean showInFirebase){
        if(showInFirebase){
            FirebaseCrash.log(TAG+tag + message);
        }
        FirebaseCrash.logcat(Log.DEBUG, TAG+tag, message);
    }

    /**
     Method that logs Log.DEBUG type by default and reports Exception object to Firebase
     **/
    public static void log(String tag, String message, boolean showInFirebase, Exception ex){
        if(showInFirebase){
            FirebaseCrash.log(TAG+tag + message);
            FirebaseCrash.report(ex);
        }
        FirebaseCrash.logcat(Log.DEBUG, TAG+tag, message);
    }
}
