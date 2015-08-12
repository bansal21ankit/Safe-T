package com.example.safet.launch;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.safet.R;
import com.example.safet.home.HomeActivity;
import com.example.safet.utils.PrefsManager;

public final class LaunchActivity extends AppCompatActivity {
    private static final String TAG = LaunchActivity.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Show this only when not already logged in.
        if (!alreadyLoggedIn()) {
            setContentView(R.layout.activity_launch);
            initContainer(savedInstanceState);
        } else
            gotoHome();
    }

    private boolean alreadyLoggedIn() {
        String[] credentials = PrefsManager.fetchLoginCredentials();
        return credentials != null && credentials[0] != null && credentials[1] != null;
    }

    private void initContainer(Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.add(R.id.launch_container, new LaunchFragment(), LaunchFragment.TAG);
            transaction.commit();
        }
    }

    void replaceFragment(@NonNull Fragment fragment, @NonNull String tag) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.launch_container, fragment, tag);
        Log.d(TAG, "Replacing current fragment with: " + tag);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    void gotoHome() {
        startActivity(new Intent(this, HomeActivity.class));
        finish();
    }
}