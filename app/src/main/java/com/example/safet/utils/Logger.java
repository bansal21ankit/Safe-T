package com.example.safet.utils;

import android.widget.Toast;

import com.example.safet.Application;

public final class Logger {
    private Logger() {
    }

    public static void toast(String message, int duration) {
        Toast.makeText(Application.getContext(), message, duration).show();
    }
}