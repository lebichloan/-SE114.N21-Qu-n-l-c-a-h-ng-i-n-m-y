package com.example.se114n21.reports;

import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.se114n21.R;


public class Sell_fragment extends Fragment {

    TextView tvSale, tvProfit, tvOrder;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.sell_fragment, container, false);

        tvSale = view.findViewById(R.id.tvSale);
        tvProfit = view.findViewById(R.id.tvProfit);
        tvOrder = view.findViewById((R.id.tvOrder));

        tvSale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                // Mở fragment mới
                fragmentTransaction.replace(R.id.content_frame, new sell_sale());
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        tvProfit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                // Mở fragment mới
                fragmentTransaction.replace(R.id.content_frame, new sell_profit());
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        tvOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                // Mở fragment mới
                fragmentTransaction.replace(R.id.content_frame, new sell_order());
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
        return view;
    }
}