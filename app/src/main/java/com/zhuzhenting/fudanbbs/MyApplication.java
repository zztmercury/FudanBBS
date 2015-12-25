package com.zhuzhenting.fudanbbs;

import android.app.Application;

import com.zhuzhenting.fudanbbs.util.Support;

import java.util.Set;

/**
 * Created by zzt on 15-12-11.
 */
public class MyApplication extends Application {
    private static final String APP_NAME = "com.zhuzhenting.fudanbbs";
    private static final String KEY_ACCOUNT = "account";
    private static final String KEY_PASSWORD = "password";

    @Override
    public void onCreate() {
        super.onCreate();
        Support.getInstance().setApp(this);
    }

    public void cacheStringParam(String key, String value) {
        getSharedPreferences(APP_NAME, MODE_PRIVATE).edit().putString(key, value).commit();
    }

    public void cacheIntParam(String key, int value) {
        getSharedPreferences(APP_NAME, MODE_PRIVATE).edit().putInt(key, value).commit();
    }

    public void cacheBooleanParam(String key, boolean value) {
        getSharedPreferences(APP_NAME, MODE_PRIVATE).edit().putBoolean(key, value).commit();
    }

    public void cacheFloatParam(String key, float value) {
        getSharedPreferences(APP_NAME, MODE_PRIVATE).edit().putFloat(key, value).commit();
    }

    public void cacheStringSetParam(String key, Set<String> value) {
        getSharedPreferences(APP_NAME, MODE_PRIVATE).edit().putStringSet(key, value).commit();
    }

    public void cacheLongParam(String key, long value) {
        getSharedPreferences(APP_NAME, MODE_PRIVATE).edit().putLong(key, value).commit();
    }

    public String getCachedStringParam(String key) {
        return getSharedPreferences(APP_NAME, MODE_PRIVATE).getString(key, null);
    }

    public int getCachedIntParam(String key) {
        return getSharedPreferences(APP_NAME, MODE_PRIVATE).getInt(key, 0);
    }

    public long getCachedLongParam(String key) {
        return getSharedPreferences(APP_NAME, MODE_PRIVATE).getLong(key, 0);
    }

    public boolean getCachedBooleanParam(String key) {
        return getSharedPreferences(APP_NAME, MODE_PRIVATE).getBoolean(key, false);
    }

    public float getCachedFloatParam(String key) {
        return getSharedPreferences(APP_NAME, MODE_PRIVATE).getFloat(key, 0);
    }

    public Set<String> getCachedStringSetParam(String key) {
        return getSharedPreferences(APP_NAME, MODE_PRIVATE).getStringSet(key, null);
    }

    public void cacheAccount(String account) {
        cacheStringParam(KEY_ACCOUNT, account);
    }

    public String getCachedAccount() {
        return getCachedStringParam(KEY_ACCOUNT);
    }

    public void cachePassword(String password) {
        cacheStringParam(KEY_PASSWORD, password);
    }

    public String getCachedPassword() {
        return getCachedStringParam(KEY_PASSWORD);
    }

}
