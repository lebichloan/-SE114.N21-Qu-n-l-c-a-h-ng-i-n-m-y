package com.example.se114n21.reports;

import com.google.android.material.tabs.TabLayout;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.se114n21.R;

public class report_activity extends Fragment {
    TextView tvSale;
    private TabLayout tabLayoutRp;
    private ViewPager viewPager;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_report, container, false);

        tabLayoutRp = view.findViewById(R.id.tabLayoutRp);
        viewPager = view.findViewById(R.id.viewPager);

        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getChildFragmentManager());
        viewPager.setAdapter(viewPagerAdapter);

        tabLayoutRp.setupWithViewPager(viewPager);

        return view;
    }
}