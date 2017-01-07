package com.solutions.myo.ankietapp.workflow.survey.camera.photo;

import android.util.Base64;

/**
 * Created by Jacek on 2017-01-05.
 */

public class PhotoHelper {

    public static String encodePhotoBytes(byte[] bytes){
        return new String(Base64.encode(bytes, Base64.DEFAULT));
    }

    public static byte[] decodePhotoBytes(String string){
        return Base64.decode(string, Base64.DEFAULT);
    }
}
