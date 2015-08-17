package com.example.safet;

import android.content.Context;

import com.parse.Parse;

public final class Application extends android.app.Application {
    private static Context mContext;

    public static Context getContext() {
        return mContext;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
        initParse();
    }

    private void initParse() {
        Parse.initialize(this, "9bz6TDOBoQtuPX3t8qUgOwusI5qpXeeVpuGBGkWc", "K3T24k2WjxfDkN042YXD1vsvfXk9YYoYuBNrqzNi");
    }
}