package com.example.dicodingeventjava.utils;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.UUID;

public class SharedPreferenceUtil {
    public static final String PREF_KEY = "PREF_KEY";
    public static final String PREF_IS_DARK_MODE = "IS_DARK_MODE";
    public static final String PREF_IS_NOTIFICATION = "IS_NOTIFICATION";
    public static final String PREF_IS_NOTIFICATION_ID = "IS_NOTIFICATION_ID";

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

    public UUID getNotificationId() {
        String uuidString = sharedPreferences.getString(PREF_IS_NOTIFICATION_ID, "");
        if (!uuidString.isEmpty()) {
            return UUID.fromString(uuidString); // Convert string back to UUID
        }
        return null;
    }

    public void setNotificationId(UUID value) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(PREF_IS_NOTIFICATION_ID, value.toString());
        editor.apply();
    }
}
