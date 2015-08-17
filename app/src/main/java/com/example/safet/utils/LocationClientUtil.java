package com.example.safet.utils;

import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.example.safet.Application;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

/**
 * Simple utility class to fetch the location details periodically using Google Play Service's GoogleApiClient api.
 * This is done as a singleton to provide global access to the app regarding it's current location
 */
public class LocationClientUtil {
    private static final String TAG = LocationClientUtil.class.getSimpleName();

    private static LocationClientUtil sLocationUtils = null;
    private GoogleApiClient mLocationClient = null;
    private LocConnectionCallbacks mLocationCallbacks = null;
    private LocConnectionFailedListener mLocConnectionFailedListener = null;
    private static final String LOCATION_ACTION = "location_action";
    private LocListener mLocListener = null;
    private LocationRequest mLocationRequest = null;
    private Location mLastLocation = null;

    private LocationClientUtil() {
    }

    /**
     * Method providing singleton instance of this class used throughout the app.
     *
     * @return LocationClient instance
     */
    public static LocationClientUtil getLocationUtils() {
        if (sLocationUtils == null)
            sLocationUtils = new LocationClientUtil();

        return sLocationUtils;
    }

    public Location getmLastLocation() {
        return mLastLocation;
    }

    /**
     * Method for initializing Location Client and connecting to fetch locations
     *
     * @param iConnectBool boolean deciding whether to call connect automatically as soon as initialization is done
     *                     or to do it manually
     * @param iLocRequest  reference to LocationRequest instance that contains information to be used by
     *                     LocationClient while fetching location
     */
    public void initAndConnect(boolean iConnectBool, LocationRequest iLocRequest) {
        mLocationRequest = iLocRequest;
        mLocationCallbacks = new LocConnectionCallbacks();
        mLocConnectionFailedListener = new LocConnectionFailedListener();

        mLocationClient = new GoogleApiClient.Builder(Application.getContext())
                .addApi(LocationServices.API)
                .addConnectionCallbacks(mLocationCallbacks)
                .addOnConnectionFailedListener(mLocConnectionFailedListener)
                .build();
        if (iConnectBool)
            mLocationClient.connect();
    }

    /**
     * Method providing manual connect facility when only initialization is done. Call to initAndConnect is
     * prerequisite.
     *
     * @throws NullPointerException if on call mLocationClient is null
     */
    public void connect() throws NullPointerException, AlreadyConnectedException {
        if (!(mLocationClient.isConnected()))
            mLocationClient.connect();
        else
            throw new AlreadyConnectedException();
    }

    /**
     * Method providing disconnection and other clean up facility
     *
     * @throws NullPointerException if on call mLocationClient is null
     */
    public void disconnect() throws NullPointerException {
        if (mLocationClient != null) {
            mLocationClient.disconnect();

            if (mLocationClient.isConnectionCallbacksRegistered(mLocationCallbacks))
                mLocationClient.unregisterConnectionCallbacks(mLocationCallbacks);

            if (mLocationClient.isConnectionFailedListenerRegistered(mLocConnectionFailedListener))
                mLocationClient.unregisterConnectionFailedListener(mLocConnectionFailedListener);
        }
        mLocationClient = null;
    }

    /**
     * Class implementing ConnectionCallbacks and providing functionality to register for location updates as well as
     * removal on disconnection
     */
    private class LocConnectionCallbacks implements GoogleApiClient.ConnectionCallbacks {

        @Override
        public void onConnected(Bundle arg0) {
            mLocListener = new LocListener();
            LocationServices.FusedLocationApi.requestLocationUpdates(mLocationClient, mLocationRequest, mLocListener);
        }

        @Override
        public void onConnectionSuspended(int arg0) {
            LocationServices.FusedLocationApi.removeLocationUpdates(mLocationClient, mLocListener);
        }
    }

    /**
     * Class implementing OnConnectionFailedListener providing details about connection's failure
     */
    private class LocConnectionFailedListener implements GoogleApiClient.OnConnectionFailedListener {
        @Override
        public void onConnectionFailed(ConnectionResult iResult) {
            // TODO Action when Location Connection fails
        }
    }

    /**
     * Class implementing LocationListener that provides continuous updates about location changes
     */
    private class LocListener implements LocationListener {
        @Override
        public void onLocationChanged(Location iLocation) {

            Log.d(TAG, "Location changed : " + iLocation);
            mLastLocation = iLocation;
            sendLocalLocationBroadcast(iLocation);
        }
    }

    /**
     * This method is simply use to send location broadcast to the listeners locally
     *
     * @param iLocation
     */
    private void sendLocalLocationBroadcast(Location iLocation) {
        LocalBroadcastManager broadCastManager = LocalBroadcastManager.getInstance(Application.getContext());
        Intent intent = new Intent(LOCATION_ACTION);
        intent.putExtra("latitude", iLocation.getLatitude());
        intent.putExtra("longitude", iLocation.getLongitude());
        broadCastManager.sendBroadcast(intent);
    }

    /**
     * Exception that will be thrown if connect is called on pre-connected LocationClient
     */
    public class AlreadyConnectedException extends Exception {
        private static final long serialVersionUID = 1L;
        private static final String ERRORMESSAGE = "LocationClient is already connected.";

        @Override
        public String getMessage() {
            return ERRORMESSAGE;
        }
    }
}