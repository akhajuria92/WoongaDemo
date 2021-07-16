package com.android.woonga.utils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

public class AppPreferences {
    private static AppPreferences appPreference;
    private SharedPreferences sharedPreferences;

    private AppPreferences(Context context) {
        sharedPreferences = context.getSharedPreferences(Constant.SHARED_PREFERENCE, Context.MODE_PRIVATE);
    }

    public static AppPreferences init(Context context) {
        if (appPreference == null) {
            appPreference = new AppPreferences(context);
        }
        return appPreference;
    }

    public void putString(String key, String value) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.commit();
    }

    public void putInt(String key, int value) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(key, value);
        editor.commit();
    }

    public void putBoolean(String key, boolean value) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(key, value);
        editor.commit();
    }

    public void putLong(String key, long value) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putLong(key, value);
        editor.commit();
    }

    public String getString(String key) {
        return sharedPreferences.getString(key, "");
    }

    public int getInt(String key) {
        return sharedPreferences.getInt(key, 0);
    }

    public boolean getBoolean(String key) {
        return sharedPreferences.getBoolean(key, false);
    }

    public long getLong(String key) {
        return sharedPreferences.getLong(key, 0);
    }

    public void clearEditor(Activity activity) {

        sharedPreferences.edit().clear().apply();
    }

    public void saveDetails(String key, Object object) {
        Gson gson = new Gson();
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, gson.toJson(object));
        editor.apply();
    }

    public <T> T getDetails(String key, Class<T> type) {
        Gson gson = new Gson();
        return gson.fromJson(sharedPreferences.getString(key, ""), type);
    }
}
