package com.example.safet.utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.example.safet.Application;

public class LocReceiver extends BroadcastReceiver {
    public static final String TAG = LocReceiver.class.getSimpleName();

    private static double[] mCuurentLocList = new double[2];
    private static boolean mIsLocationFetched;

    public static boolean isLocationFetched() {
        return mIsLocationFetched;
    }

    public static double[] getmCuurentLocList() {

        //Test coordinates
        mCuurentLocList[0] = 28.6129724;
        mCuurentLocList[1] = 77.3540013;
        Toast.makeText(Application.getContext(), "TEST COORDINATES OF LOC RECIEVER" + mCuurentLocList[0] + mCuurentLocList[1], Toast.LENGTH_SHORT).show();
        return mCuurentLocList;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        final String latitude_ = "latitude";
        final String logitude_ = "longitude";

        Logger.logE(TAG, "----LocReciver :: " + intent.getDoubleExtra(latitude_, 0.0)
                + "---" + intent.getDoubleExtra(logitude_, 0.0));

        double latitude = intent.getDoubleExtra(latitude_, 0.0);
        double longitude = intent.getDoubleExtra(logitude_, 0.0);

        if (latitude == 0.0 || longitude == 0.0) {
            Logger.logE(TAG, "--LAT--" + latitude + "--LONG--" + longitude);
            mIsLocationFetched = false;
        }
            /*
			 * If current location coordinates found
			 */
        else {
            mIsLocationFetched = true;
            Logger.logE(TAG, "--LAT--" + latitude + "--LON--" + longitude);
            mCuurentLocList[0] = latitude;
            mCuurentLocList[1] = longitude;
        }
    }

}
