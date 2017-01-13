package com.solutions.myo.ankietapp.firebase.database.models;

import com.google.firebase.database.Exclude;
import com.solutions.myo.ankietapp.firebase.database.utils.ConvertHelper;
import com.solutions.myo.ankietapp.objects.Selfie;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Jacek on 2017-01-13.
 */

public class FSelfie {

    private int smileMeasure;
    private String encodedPhoto;

    public FSelfie() {
    }

    public FSelfie(int smileMeasure, String encodedPhoto) {
        this.smileMeasure = smileMeasure;
        this.encodedPhoto = encodedPhoto;
    }

    @Exclude
    public static Map<String, Object> toMap(Selfie selfie) {
        HashMap<String, Object> result = new HashMap<>();
/*        if(!StringUtils.isEmpty(selfie.getEncodedPhoto())) result.put(IFirebaseStorage.ISelfie.PHOTO, selfie.getEncodedPhoto());
        result.put(IFirebaseStorage.ISelfie.SMILE_MEASURE, String.valueOf(selfie.getSmileMeasure()));
        return result;*/
        return ConvertHelper.convertObjectToProperty(selfie);
    }
}
