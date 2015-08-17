package com.example.safet.home;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.safet.Application;
import com.example.safet.saviour.SaviourFragment;

final class HomeAdapter extends FragmentPagerAdapter {
    private final String[] mPageTitles;
    private final Fragment[] mPages;

    public HomeAdapter(FragmentManager manager) {
        super(manager);

        // Initialize the page list.
        mPages = new Fragment[]{new SaviourFragment(), new IncidentFragment()};

        // Initialize the title list.
        mPageTitles = new String[mPages.length];
        Context context = Application.getContext();
        mPageTitles[0] = context.getString(SaviourFragment.TITLE_RES);
        mPageTitles[1] = context.getString(IncidentFragment.TITLE_RES);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mPageTitles[position];
    }

    @Override
    public Fragment getItem(int position) {
        return mPages[position];
    }

    @Override
    public int getCount() {
        return mPages.length;
    }
}