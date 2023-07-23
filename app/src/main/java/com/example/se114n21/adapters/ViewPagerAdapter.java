package com.example.se114n21.adapters;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.se114n21.reports.Profit_fragment;
import com.example.se114n21.reports.Repo_fragment;
import com.example.se114n21.reports.Sell_fragment;

public class ViewPagerAdapter extends FragmentPagerAdapter {
    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position)
        {
            case 0:
                return new Sell_fragment();

            case 1:
                return new Repo_fragment();

            case 2:
                return new Profit_fragment();

            default:
                return new Sell_fragment();
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
