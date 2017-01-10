package com.solutions.myo.ankietapp.workflow.survey.camera.photo;

import android.util.Base64;

import com.solutions.myo.ankietapp.utils.StringUtils;


public class PhotoHelper {

    public static String encodePhotoBytes(byte[] bytes){
        return new String(Base64.encode(bytes, Base64.DEFAULT));
    }

    public static byte[] decodePhotoBytes(String string){
        if(StringUtils.isEmpty(string)){
            return null;
        }
        return Base64.decode(string, Base64.DEFAULT);
    }
}
