package com.example.safet.launch;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.safet.R;

public final class LaunchFragment extends Fragment implements View.OnClickListener {
    public static final String TAG = LaunchFragment.class.getName();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_launch, container, false);
        view.findViewById(R.id.launch_register).setOnClickListener(this);
        view.findViewById(R.id.launch_login).setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View view) {
        if (!(getActivity() instanceof LaunchActivity))
            // Cannot send callbacks.
            return;

        // Get LaunchActivity instance from parent activity.
        LaunchActivity activity = (LaunchActivity) getActivity();

        // Replace fragment.
        switch (view.getId()) {
            case R.id.launch_login:
                activity.replaceFragment(new LoginFragment(), LoginFragment.TAG);
                break;
            case R.id.launch_register:
                activity.replaceFragment(new RegisterFragment(), RegisterFragment.TAG);
                break;
        }
    }
}