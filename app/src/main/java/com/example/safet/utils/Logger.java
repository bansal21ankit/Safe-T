package com.example.safet.utils;

import android.util.Log;
import android.widget.Toast;

import com.example.safet.Application;

public final class Logger {
    private Logger() {
    }

    public static void toast(String message, int duration) {
        Toast.makeText(Application.getContext(), message, duration).show();
    }

    private static boolean sIsAllowDebug = true;

    /**
     * Making the Toast in the application
     *
     * @param iMsg string which has to be toasted .
     */
    public static void showToast(String iMsg) {
        Toast.makeText(Application.getContext(), iMsg, Toast.LENGTH_SHORT).show();
    }

    /**
     * Checking whether the application is in debug mode.
     *
     * @return True if the application is in debug mode else false
     */
    public static boolean isAllowDebug() {
        return sIsAllowDebug;
    }

    /**
     * Called to show Log of verbose
     *
     * @param iTag     : TAG of class name
     * @param iMessage : Message for display
     */
    public static void logV(String iTag, String iMessage) {
        // If application is in debug mode
        if (isAllowDebug())
            Log.v(iTag, iMessage);
    }

    /**
     * Called to show Log of errors
     *
     * @param iTag     : TAG of class name
     * @param iMessage : Message for display
     */
    public static void logE(String iTag, String iMessage) {
        // If application is in debug mode
        if (isAllowDebug())
            Log.e(iTag, iMessage);
    }

    /**
     * Called to show Log of debug
     *
     * @param iTag     : TAG of class name
     * @param iMessage : Message for display
     */
    public static void logD(String iTag, String iMessage) {
        // if application is in debug mode
        if (isAllowDebug())
            Log.d(iTag, iMessage);
    }

    /**
     * Called to show Log of warnings
     *
     * @param iTag     : TAG of class name
     * @param iMessage : Message for display
     */
    public static void logW(String iTag, String iMessage) {
        // if application is in debug mode
        if (isAllowDebug())
            Log.w(iTag, iMessage);
    }

    /**
     * Called to show Log of info
     *
     * @param iTag     : TAG of class name
     * @param iMessage : Message for display
     */

    public static void logI(String iTag, String iMessage) {
        // If application is in debug mode
        if (isAllowDebug())
            Log.i(iTag, iMessage);
    }
}