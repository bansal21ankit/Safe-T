package com.example.safet.launch;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.example.safet.R;
import com.example.safet.utils.Logger;
import com.example.safet.utils.PhoneUtil;
import com.example.safet.utils.PrefsManager;

public final class LoginFragment extends Fragment {
    public static final String TAG = LoginFragment.class.getName();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        initViews(view);
        return view;
    }

    private void initViews(View rootView) {
        final EditText phoneView = (EditText) rootView.findViewById(R.id.login_phone);
        final EditText passwordView = (EditText) rootView.findViewById(R.id.login_password);

        // TODO Remove these dummies
        phoneView.setText("1234567890");
        passwordView.setText("password");

        rootView.findViewById(R.id.login_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String phone = phoneView.getText().toString().trim();
                String password = passwordView.getText().toString();

                // Show warning if phone is of invalid length.
                if (!PhoneUtil.isValid(phone)) {
                    Logger.toast(getString(R.string.msg_phone_invalid), Toast.LENGTH_LONG);
                    return;
                }

                // Show warning if password is empty.
                if (TextUtils.isEmpty(password)) {
                    Logger.toast(getString(R.string.msg_password_empty), Toast.LENGTH_LONG);
                    return;
                }

                // TODO Perform login operation, and remove this direct call.
                PrefsManager.saveLoginCredentials(phone, password);
                ((LaunchActivity) getActivity()).gotoHome();
            }
        });
    }
}