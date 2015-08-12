package com.example.safet.home;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.safet.Application;
import com.example.safet.R;

final class HomeAdapter extends FragmentPagerAdapter {
    public HomeAdapter(FragmentManager manager) {
        super(manager);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new HomeFragment();
            case 1:
            default:
                return new HomeFragment();
        }
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return Application.getContext().getString(R.string.home_contact_title);
            default:
                return Application.getContext().getString(R.string.home_contact_title);
        }
    }

    @Override
    public int getCount() {
        return 2;
    }
}