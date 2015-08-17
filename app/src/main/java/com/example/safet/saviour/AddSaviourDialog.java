package com.example.safet.saviour;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.example.safet.R;
import com.example.safet.utils.Logger;
import com.example.safet.utils.PhoneUtil;
import com.example.safet.utils.PrefsManager;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.SaveCallback;

public final class AddSaviourDialog extends DialogFragment {
    public static final String TAG = AddSaviourDialog.class.getName();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_saviour_add, container, false);
        initViews(view);
        return view;
    }

    private void initViews(View rootView) {
        final EditText phone = (EditText) rootView.findViewById(R.id.saviour_add_phone);
        rootView.findViewById(R.id.saviour_add_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String phoneStr = phone.getText().toString().trim();

                // Show warning if phone is of invalid length.
                if (!PhoneUtil.isValid(phoneStr)) {
                    Logger.toast(getString(R.string.msg_phone_invalid), Toast.LENGTH_LONG);
                    return;
                }

                String userPhone = PrefsManager.fetchLoginCredentials()[0];
                ParseObject saviour = new ParseObject(Constants.RequestTables.TABLE_REQUEST);
                saviour.put(Constants.RequestKeys.COL_REQUESTED_BY, userPhone);
                saviour.put(Constants.RequestKeys.COL_REQUESTED_TO, phoneStr);
                saviour.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(ParseException exception) {
                        if (exception == null) {
                            dismiss();
                        } else {
                            Logger.toast("Error in adding saviour", Toast.LENGTH_SHORT);
                            exception.printStackTrace();
                        }
                    }
                });
            }
        });
    }
}