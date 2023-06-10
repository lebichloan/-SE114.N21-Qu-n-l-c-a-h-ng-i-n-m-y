package com.example.se114n21.reports;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.se114n21.R;


public class Profit_fragment extends Fragment {

    TextView tvFund, tvCustomerDeft, tvSupplierDeft;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.profit_fragment, container, false);

        tvFund = view.findViewById(R.id.tvFund);
        tvCustomerDeft = view.findViewById(R.id.tvCustomerDebt);
        tvSupplierDeft = view.findViewById(R.id.tvSupplierDebt);

        tvFund.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                // Mở fragment mới
                fragmentTransaction.replace(R.id.content_frame, new profit_fund());
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        tvCustomerDeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                // Mở fragment mới
                fragmentTransaction.replace(R.id.content_frame, new profit_cus_deft());
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        tvSupplierDeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                // Mở fragment mới
                fragmentTransaction.replace(R.id.content_frame, new profit_sup_deft());
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });


        return view;
    }
}