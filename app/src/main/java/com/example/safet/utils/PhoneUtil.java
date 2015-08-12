package com.example.safet.utils;

import android.text.TextUtils;

import com.example.safet.Application;
import com.example.safet.R;

public final class PhoneUtil {
    /**
     * @param phone Phone number to validate.
     * @return true if phone number isn't null, empty, have 10 digits and first digits is not 0, false otherwise.
     */
    public static boolean isValid(String phone) {
        int validLength = Application.getContext().getResources().getInteger(R.integer.phone_length);
        return !TextUtils.isEmpty(phone) && phone.length() == validLength && phone.charAt(0) != '0';
    }
}