package com.example.safet.home;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.safet.R;
import com.example.safet.launch.LaunchActivity;
import com.example.safet.utils.Logger;
import com.example.safet.utils.PrefsManager;

public final class HomeActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = HomeActivity.class.getName();
    private DrawerLayout mDrawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        initDrawerToggleAnimation();
        initDrawerActions();
        initContent();
    }

    /**
     * This will initialize the ActionBar and Navigation Drawer. Displays an icon on ActionBar to
     * open or close drawer also attach a DrawerToggle with Drawer so that opening and closing
     * actions can animate the action bar icon.
     */
    private void initDrawerToggleAnimation() {
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

    private void initDrawerActions() {
        findViewById(R.id.home_setting).setOnClickListener(this);
        findViewById(R.id.home_logout).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
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

    private void initContent() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.home_content, new HomeFragment(), HomeFragment.TAG);
        transaction.commit();
    }
}