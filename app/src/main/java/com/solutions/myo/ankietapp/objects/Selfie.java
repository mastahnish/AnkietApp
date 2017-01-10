package com.solutions.myo.ankietapp.objects;

/**
 * Created by Jacek on 2017-01-10.
 */

public class Selfie {

    private int smileMeasure;
    private String encodedPhoto;

    public int getSmileMeasure() {
        return smileMeasure;
    }

    public void setSmileMeasure(int smileMeasure) {
        this.smileMeasure = smileMeasure;
    }

    public String getEncodedPhoto() {
        return encodedPhoto;
    }

    public void setEncodedPhoto(String encodedPhoto) {
        this.encodedPhoto = encodedPhoto;
    }

    @Override
    public String toString() {
        return "Selfie{" +
                "smileMeasure=" + smileMeasure +
                ", encodedPhoto='" + encodedPhoto + '\'' +
                '}';
    }
}
