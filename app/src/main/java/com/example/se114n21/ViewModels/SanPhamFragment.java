package com.example.se114n21.ViewModels;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.se114n21.R;

public class SanPhamFragment extends Fragment {
    private Button btnListProduct, btnNhapHang;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_san_pham, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initUI();
    }

    private void initUI() {
        btnListProduct = (Button) getView().findViewById(R.id.btn_list_product);
        btnNhapHang = (Button) getView().findViewById(R.id.btn_nhaphang);

        btnListProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), ListProduct.class);
                startActivity(intent);
            }
        });

        btnNhapHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), ListDonNhapHang.class));
            }
        });
    }
}