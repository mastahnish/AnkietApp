package com.solutions.myo.ankietapp.utils;

import android.content.Context;

public class FontOverrideHelper {

    private FontOverrideHelper() {
    }

    public static void setCustomFonts(Context context) {
//        FontOverride.setDefaultFont(context, "MONOSPACE", "fonts/gotham_rounded_medium.ttf");
//        FontOverride.setDefaultFont(context, "SERIF", "fonts/gotham_rounded_book.ttf");
//        FontOverride.setDefaultFont(context, "SANS_SERIF", "fonts/gotham_rounded_bold.ttf");
        FontOverride.setDefaultFont(context, "MONOSPACE", "fonts/CroissantOne-Regular.ttf");
    }
}
