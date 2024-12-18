package com.example.dicodingeventjava.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferenceUtil {
    public static final String PREF_KEY = "PREF_KEY";
    public static final String PREF_IS_DARK_MODE = "IS_DARK_MODE";
    public static final String PREF_IS_NOTIFICATION = "IS_NOTIFICATION";

    private final SharedPreferences sharedPreferences;

    public SharedPreferenceUtil(Context context) {
        sharedPreferences = context.getSharedPreferences(PREF_KEY, Context.MODE_PRIVATE);
    }

    public Boolean isDarkMode() {
        return sharedPreferences.getBoolean(PREF_IS_DARK_MODE, false);
    }

    public void setDarkMode(Boolean value) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(PREF_IS_DARK_MODE, value);
        editor.apply();
    }

    public Boolean isNotification() {
        return sharedPreferences.getBoolean(PREF_IS_NOTIFICATION, false);
    }

    public void setNotification(Boolean value) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(PREF_IS_NOTIFICATION, value);
        editor.apply();
    }
}
