package com.example.safet.home;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.safet.R;

public final class HomeFragment extends Fragment implements View.OnClickListener {
    public static final String TAG = HomeFragment.class.getName();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        initActionButtons(view);
        return view;
    }

    private void initActionButtons(View rootView) {
        rootView.findViewById(R.id.home_add_saviour).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.home_add_saviour:
                // TODO Open screen to add a trusted contact.
                break;
        }
    }
}