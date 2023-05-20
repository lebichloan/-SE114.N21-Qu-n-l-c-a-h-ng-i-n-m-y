package com.example.se114n21.reports;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class ViewPagerAdapter extends FragmentPagerAdapter {
    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position)
        {
            case 0:
                return new sale_fragment();

            case 1:
                return new Repo_fragment();

            case 2:
                return new Profit_fragment();

            default:
                return new sale_fragment();
        }
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        String title = "";
        switch (position)
        {
            case 0:
                title = "BÁN HÀNG";
                break;

            case 1:
                title = "KHO";
                break;

            case 2:
                title = "TÀI CHÍNH";
                break;
        }
        return title;
    }
}
