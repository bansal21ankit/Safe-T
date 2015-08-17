package com.example.safet.home;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.provider.SyncStateContract;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.safet.Application;
import com.example.safet.R;
import com.example.safet.custom.SlidingTabLayout;
import com.example.safet.launch.LaunchActivity;
import com.example.safet.saviour.Constants;
import com.example.safet.saviour.SaviourDetail;
import com.example.safet.utils.LocReceiver;
import com.example.safet.utils.LocationClientUtil;
import com.example.safet.utils.Logger;
import com.example.safet.utils.PrefsManager;
import com.example.safet.utils.UserHelper;
import com.example.safet.utils.Utility;
import com.google.android.gms.location.LocationRequest;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.SaveCallback;

import java.util.ArrayList;
import java.util.List;

public final class HomeActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = HomeActivity.class.getName();
    private DrawerLayout mDrawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        initToolbarAndTabs();
        initDrawerToggle();
        initDrawerAction();
    }

    private void initToolbarAndTabs() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.home_toolbar);
        setSupportActionBar(toolbar);

        ViewPager pager = (ViewPager) findViewById(R.id.home_pager);
        pager.setAdapter(new HomeAdapter(getSupportFragmentManager()));

        SlidingTabLayout slidingTab = (SlidingTabLayout) findViewById(R.id.home_sliding_tab);
        slidingTab.setDistributeEvenly(true);
        slidingTab.setViewPager(pager);
    }

    /**
     * This will initialize the ActionBar and Navigation Drawer. Displays an icon on ActionBar to
     * open or close drawer also attach a DrawerToggle with Drawer so that opening and closing
     * actions can animate the action bar icon.
     */
    private void initDrawerToggle() {
        // Attach DrawerToggle with DrawerLayout
        mDrawerLayout = (DrawerLayout) findViewById(R.id.home_drawer_layout);
        ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.drawer_open, R.string.drawer_close);
        mDrawerLayout.setDrawerListener(drawerToggle);
        drawerToggle.syncState();

        // Show HOME button in ActionBar.
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeButtonEnabled(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // Show/Hide the drawer based on current state.
                if (mDrawerLayout.isDrawerVisible(GravityCompat.START))
                    mDrawerLayout.closeDrawer(GravityCompat.START);
                else
                    mDrawerLayout.openDrawer(GravityCompat.START);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void initDrawerAction() {
        findViewById(R.id.home_trigger).setOnClickListener(this);
        findViewById(R.id.home_setting).setOnClickListener(this);
        findViewById(R.id.home_logout).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.home_trigger:
                //Start sending location to server to inform saviours about the emergency
                getCurrentLocation();
                sendIncidentReportToServer();
                break;

            case R.id.home_setting:
                break;

            case R.id.home_logout:
                PrefsManager.clearLoginCredentials();
                Logger.toast(getString(R.string.msg_logout_success), Toast.LENGTH_SHORT);
                gotoLaunch();
                break;
        }
    }

    private void gotoLaunch() {
        startActivity(new Intent(this, LaunchActivity.class));
        finish();
    }

    private ListView mListView;
    private TextView mTextNoSaviour;
    private ArrayList<SaviourDetail> mSaviourDetail = new ArrayList<>();
    private LocReceiver mLocationReceiver = null;
    private LocalBroadcastManager mBroadCastManager = null;
    private LocationClientUtil mLocUtil = null;

    /**
     * Called to fetch user's current location coordinates
     */
    private void getCurrentLocation() {
        mBroadCastManager = LocalBroadcastManager
                .getInstance(Application.getContext());
        mLocUtil = LocationClientUtil.getLocationUtils();
        mLocationReceiver = new LocReceiver();
        IntentFilter locFilter = new IntentFilter(Constants.LOCATION_ACTION);
        mBroadCastManager.registerReceiver(mLocationReceiver, locFilter);
        LocationRequest req = new LocationRequest();
        req.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocUtil.initAndConnect(true, req);
    }

    private void sendIncidentReportToServer() {

//        ParseObject parseObject = new ParseObject(Constants.RequestTables.TABLE_USERS);
        final String phone = UserHelper.getmUserPhone();
        final ProgressDialog dialog = Utility.showProgressDialog(this, Constants.UserInfoMessage.PLEASE_WAIT);
        ParseQuery<ParseObject> query = ParseQuery.getQuery(Constants.RequestTables.TABLE_USERS);
        query.whereEqualTo(Constants.RequestKeys.COL_PHONE, phone);
        query.whereEqualTo(Constants.RequestKeys.COL_PHONE, Constants.INCIDENT_STATUS_ACTIVE);
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> list, ParseException e) {
                dialog.dismiss();
                if (e != null) {
                    Utility.showAlertDialog(HomeActivity.this, Constants.UserInfoMessage.INFO, Constants.UserErrorMessage.SOMETHING_WRONG, Constants.UserInfoMessage.OK, Constants.UserInfoMessage.CANCEL, null);
                    e.printStackTrace();
                } else {
                    /*
                    * No Incident is active currently, then start a new one
                     */
                    if (list.size() == 0) {

                        if (Utility.isLocationEnabled())
                            startOrUpdateIncident(true, null);
                        else {
                            Logger.logD(TAG, "LOCATION SERVICE IS DISABLED");
                            //If location is not enabled
//                            Utility.showAlertDialog(getActivity(), Constants.UserInfoMessage.INFO, Constants.UserInfoMessage.LOGIN_AUTHENTICATION_FAILED, );
                        }
                    } else {
                        if (list.size() == 1) {
                            startOrUpdateIncident(false, list.get(0));
                        }
                    }
                }
            }
        });

    }

    private void startOrUpdateIncident(boolean isNew, ParseObject obj) {

        String phone = UserHelper.getmUserPhone();
        String startLocation = String.valueOf(LocReceiver.getmCuurentLocList()[0]) + ", " + String.valueOf(LocReceiver.getmCuurentLocList()[1]);
        String lastLocation = String.valueOf(LocReceiver.getmCuurentLocList()[0]) + ", " + String.valueOf(LocReceiver.getmCuurentLocList()[1]);
        String status = Constants.INCIDENT_STATUS_ACTIVE;
        String startTime;
        if (!isNew) {
            phone = (String) obj.get(Constants.RequestKeys.COL_PHONE);
            startLocation = (String) obj.get(Constants.RequestKeys.COL_START_LOCATION);
            startTime = (String) obj.get(Constants.RequestKeys.COL_START_TIME);
            lastLocation = String.valueOf(LocReceiver.getmCuurentLocList()[0]) + ", " + String.valueOf(LocReceiver.getmCuurentLocList()[1]);
        }

        if (obj == null)
            obj = new ParseObject(Constants.RequestTables.TABLE_INCIDENT);
        obj.put(Constants.RequestKeys.COL_PHONE, PrefsManager.fetchLoginCredentials()[0]);
        obj.put(Constants.RequestKeys.COL_START_LOCATION, startLocation);
        obj.put(Constants.RequestKeys.COL_LAST_LOCATION, lastLocation);
        obj.put(Constants.RequestKeys.COL_INCIDENT_STATUS, status);
        obj.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {

                if (e != null) {

                    Log.d(TAG, "Location Updating Error..");
                    e.printStackTrace();
                } else
                    Log.d(TAG, "Location Updating..");
            }
        });

    }
}