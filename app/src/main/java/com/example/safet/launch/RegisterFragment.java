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

import com.example.safet.utils.Logger;
import com.example.safet.R;
import com.example.safet.utils.PhoneUtil;

public final class RegisterFragment extends Fragment {
    public static final String TAG = RegisterFragment.class.getName();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_register, container, false);
        initViews(view);
        return view;
    }

    private void initViews(View rootView) {
        final EditText nameView = (EditText) rootView.findViewById(R.id.register_name);
        final EditText phoneView = (EditText) rootView.findViewById(R.id.register_phone);
        final EditText confirmView = (EditText) rootView.findViewById(R.id.register_confirm);
        final EditText passwordView = (EditText) rootView.findViewById(R.id.register_password);
        rootView.findViewById(R.id.register_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = nameView.getText().toString().trim();
                String phone = phoneView.getText().toString().trim();
                String password = passwordView.getText().toString();
                String confirm = confirmView.getText().toString();

                // Show warning if phone is of invalid length.
                if (!PhoneUtil.isValid(phone)) {
                    Logger.toast(getString(R.string.msg_phone_invalid), Toast.LENGTH_LONG);
                    return;
                }

                // Show warning if any credential is empty.
                if (TextUtils.isEmpty(name) || TextUtils.isEmpty(password)) {
                    Logger.toast(getString(R.string.msg_credentials_empty), Toast.LENGTH_LONG);
                    return;
                }

                // Show warning for passwords
                if (!password.equals(confirm)) {
                    Logger.toast("Passwords don't match", Toast.LENGTH_LONG);
                    return;
                }

                // TODO Perform login operation.
            }
        });
    }
}