package com.solutions.myo.ankietapp.analytics;

public interface IFirebaseAnalytics {

    interface IEvent {
        String START_BUTTON_CLICKED = "start_btn_clicked";
        String LOGIN_BUTTON_CLICKED = "login_btn_clicked";
        String SIGN_UP_BUTTON_CLICKED = "sign_up_btn_clicked";
        String SIGN_OUT_BUTTON_CLICKED = "sign_out_btn_clicked";
        String MENU_ANALYTICS_BUTTON_CLICKED = "menu_analytics_btn_clicked";
        String MENU_SURVEY_BUTTON_CLICKED = "menu_survey_btn_clicked";
        String SURVEY_TAKE_PHOTO_BUTTON_CLICKED = "survey_take_photo_btn_clicked";
        String SURVEY_RETAKE_PHOTO_BUTTON_CLICKED = "survey_retake_photo_btn_clicked";
        String SURVEY_SEND_BUTTON_CLICKED = "survey_send_btn_clicked";
    }

    interface ILocation {
        String SURVEY = "survey_screen";
        String ANALYTICS = "analytics_screen";
        String MENU = "menu_screen";
        String LOGIN = "login_screen";
        String SPLASH = "splash_screen";
    }

    interface IButtons {
        String ANALYTICS = "analytics_button";
        String SURVEY = "survey_button";
        String TAKE_PHOTO = "take_photo_button";
        String RETAKE_PHOTO = "retake_photo_button";
        String SEND_SURVEY = "send_survey_button";
        String LOGIN = "login_button";
        String SIGN_UP = "sign_up_button";
        String SIGN_OUT = "sign_up_button";
        String START = "start_button";
    }

}
