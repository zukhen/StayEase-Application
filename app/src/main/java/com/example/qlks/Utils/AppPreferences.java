package com.example.qlks.Utils;

import android.content.Context;
import android.content.SharedPreferences;

public class AppPreferences {
    private static final String PREFS_NAME = "MyFile";
    private static final String KEY_IS_FIRST_LAUNCH = "isFirstLaunch";
    private static final String KEY_USERNAME = "Username";
    private static final String KEY_PASSWORD = "Password";
    private static final String KEY_REMEMBER = "Remember";

    private SharedPreferences prefs;
    private SharedPreferences.Editor editor;

    public AppPreferences(Context context) {
        prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        editor = prefs.edit();
    }

    public boolean isFirstLaunch() {
        return prefs.getBoolean(KEY_IS_FIRST_LAUNCH, true);
    }

    public void setFirstLaunch(boolean value) {
        editor.putBoolean(KEY_IS_FIRST_LAUNCH, value);
        editor.apply();
    }

    public void remeberUser(Context context, String user, String pass, boolean status) {
        SharedPreferences preferences = context.getSharedPreferences(PREFS_NAME, context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(KEY_USERNAME, user);
        editor.putString(KEY_PASSWORD, pass);
        editor.putBoolean(KEY_REMEMBER, status);
        editor.apply();
    }

    public String getEmailUser(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(PREFS_NAME, context.MODE_PRIVATE);
        return preferences.getString(KEY_USERNAME, "");
    }

    public String getPasswordUser(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(PREFS_NAME, context.MODE_PRIVATE);
        return preferences.getString(KEY_PASSWORD, "");
    }

    public boolean getRemember(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(PREFS_NAME, context.MODE_PRIVATE);
        return preferences.getBoolean(KEY_REMEMBER, false);
    }
}
