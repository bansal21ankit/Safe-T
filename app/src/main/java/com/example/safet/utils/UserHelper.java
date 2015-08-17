package com.example.safet.utils;

public class UserHelper {
    private static String mUserName;
    private static String mUserPassword;
    private static String mUserPhone;
    private static String mUserId;

    public static String getmUserName() {
        return mUserName;
    }

    public static void setmUserName(String mUserName) {
        UserHelper.mUserName = mUserName;
    }

    public static String getmUserPassword() {
        return mUserPassword;
    }

    public static void setmUserPassword(String mUserPassword) {
        UserHelper.mUserPassword = mUserPassword;
    }

    public static String getmUserPhone() {
        return mUserPhone;
    }

    public static void setmUserPhone(String mUserPhone) {
        UserHelper.mUserPhone = mUserPhone;
    }

    public static String getmUserId() {
        return mUserId;
    }

    public static void setmUserId(String mUserId) {
        UserHelper.mUserId = mUserId;
    }
}