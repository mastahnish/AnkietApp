package com.solutions.myo.ankietapp.utils;

/**
 * Created by Jacek on 2017-01-04.
 */

public class StringUtils {
    private StringUtils() {}

    public static String Blank = "";

    public static boolean isEmpty(String str) {
        return str == null || str.length() <= 0;
    }

    public static String notNull(String str) {
        return str == null ? Blank : str;
    }

    public static boolean containsIgnoreCase(CharSequence searchIn, CharSequence searchFor) {
        if (searchIn == null || searchFor == null) {
            return false;
        }
        return searchIn.toString().toLowerCase().contains(searchFor.toString().toLowerCase());
    }

    public static String formatItemStatusForDisplay(String status) {
        String resultStatus = StringUtils.notNull(status);
        return resultStatus.replace("_", " ");
    }

    public static boolean startsWithIgnoreCase(CharSequence searchIn, CharSequence searchFor) {
        if (searchIn == null || searchFor == null) {
            return false;
        }
        return searchIn.toString().toLowerCase().startsWith(searchFor.toString().toLowerCase());
    }


}
