package com.example.safet.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.safet.Application;

public final class PrefsManager {
    private static final String PREF_NAME = "SafeT.prefs";
    private static final String KEY_PHONE = "PhoneNumber";
    private static final String KEY_PASSWORD = "Password";
    private static final String KEY_TRIGGER = "isTriggered";
    private static SharedPreferences mPrefs;

    /**
     * Check if preference file is not yet accessible, create an instance to perform read/write
     * operations in it.
     */
    private static void assertPrefFile() {
        if (mPrefs == null)
            mPrefs = Application.getContext().getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    /**
     * Create an entry of login credentials in preference file for future use, should be done only
     * on successful login.
     */
    public static void saveLoginCredentials(String phone, String password) {
        assertPrefFile();
        SharedPreferences.Editor editor = mPrefs.edit();
        editor.putString(KEY_PASSWORD, password);
        editor.putString(KEY_PHONE, phone);
        editor.apply();
    }

    /**
     * Removes the preference entries for login credentials.
     */
    public static void clearLoginCredentials() {
        saveLoginCredentials(null, null);
    }

    /**
     * @return Array of strings in this format : {phone, password}.
     */
    public static String[] fetchLoginCredentials() {
        assertPrefFile();
        String phone = mPrefs.getString(KEY_PHONE, null);
        String password = mPrefs.getString(KEY_PASSWORD, null);
        return new String[]{phone, password};
    }

    /**
     * @param isTriggered true if trigger is activated via widget to send panic events, false otherwise.
     */
    public static void setIsTriggered(boolean isTriggered) {
        assertPrefFile();
        SharedPreferences.Editor editor = mPrefs.edit();
        editor.putBoolean(KEY_TRIGGER, isTriggered);
        editor.apply();
    }

    /**
     * @return true if trigger is active and sending panic events, false otherwise.
     */
    public static boolean isTriggered() {
        return mPrefs.getBoolean(KEY_TRIGGER, false);
    }
}