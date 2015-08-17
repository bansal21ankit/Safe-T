package com.example.safet.utils;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.example.safet.Application;

public class Utility {
    private static final String TAG = Utility.class.getSimpleName();

    /**
     * For hiding the soft keyboard
     *
     * @param iEdt : instance of Focused Field
     */
    public static void hideKeyBoard(EditText iEdt) {
        InputMethodManager inManager = (InputMethodManager) Application.getContext().
                getSystemService(Context.INPUT_METHOD_SERVICE);
        inManager.hideSoftInputFromWindow(iEdt.getWindowToken(), 0);
    }

    /**
     * For opening the soft keyboard
     *
     * @param iEdt : instance of Focused Field
     */
    public static void openKeyBoard(EditText iEdt) {
        InputMethodManager inputMethodManager = (InputMethodManager) Application.getContext().
                getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.toggleSoftInputFromWindow(iEdt.getWindowToken(), InputMethodManager.SHOW_FORCED, 0);
    }

    /**
     * @return true if network connection is available
     */
    public static boolean isNetworkAvailable() {

        ConnectivityManager cm = (ConnectivityManager) Application.getContext().getSystemService(
                Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = cm.getActiveNetworkInfo();

        if (info == null)
            return false;

        NetworkInfo.State network = info.getState();
        return (network == NetworkInfo.State.CONNECTED || network == NetworkInfo.State.CONNECTING);
    }

    /**
     * Called to create a type of animation
     *
     * @param iContext    : Context of calling activity
     * @param idAnimation : Animation Id i.e type of animation
     * @return : {@link Animation} instance
     */
    public static Animation createAnimation(Context iContext, int idAnimation) {
        Animation anim = AnimationUtils.loadAnimation(iContext, idAnimation);
        return anim;
    }

    /**
     * Called to show alert dialog
     *
     * @param iContext             : Context of the calling activity
     * @param iDialogTitle         : Alert Dialog Title Name
     * @param iMesssage            : Message of the alert dialog
     * @param iPositiveButtonLabel : Positive button Label
     * @param iNegativeButtonLabel : Negative button Label
     * @param iListener            : instance of dialog click listener, to perform the required function
     */
    public static void showAlertDialog(Context iContext, String iDialogTitle, String iMesssage,
                                       String iPositiveButtonLabel, String iNegativeButtonLabel, Dialog.OnClickListener iListener) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(iContext);
        dialog.setTitle(iDialogTitle);
        dialog.setMessage(iMesssage);
        dialog.setPositiveButton(iPositiveButtonLabel, iListener);
        dialog.setNegativeButton(iNegativeButtonLabel, iListener);
        dialog.setCancelable(false);

        try {
            dialog.show();
        } catch (Exception x) {
            Log.e(TAG, " Exception occur on dialog" + x);
        }
    }

    public static ProgressDialog showProgressDialog(Context iContext, String iMsg) {
        ProgressDialog iProgressBar = new ProgressDialog(iContext);
        iProgressBar.setMessage(iMsg);
        iProgressBar.setCancelable(false);
        iProgressBar.setCanceledOnTouchOutside(false);
        iProgressBar.show();
        return iProgressBar;
    }

    /**
     * Called to get the App Version
     *
     * @return : Version Name of the APP
     */
    public static String getAppVersion() {
        PackageInfo pInfo = null;
        try {
            pInfo = Application.getContext().getPackageManager().getPackageInfo
                    (Application.getContext().getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return pInfo.versionName;
    }

    /**
     * Called to get the Location Service Mode
     *
     * @return
     */
    @SuppressWarnings("deprecation")
    public static boolean isLocationEnabled() {
        int locationMode = 0;
        String locationProviders;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            try {
                locationMode = Settings.Secure.getInt(Application.getContext().
                        getContentResolver(), Settings.Secure.LOCATION_MODE);

            } catch (Settings.SettingNotFoundException e) {
                e.printStackTrace();
            }

            return locationMode != Settings.Secure.LOCATION_MODE_OFF;

        } else {
            locationProviders = Settings.Secure.getString(Application.getContext().
                    getContentResolver(), Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
            return !TextUtils.isEmpty(locationProviders);
        }
    }
}