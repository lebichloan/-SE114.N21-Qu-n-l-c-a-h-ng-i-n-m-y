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
import com.google.android.material.button.MaterialButtonToggleGroup;

public class DonHangFragment extends Fragment {
    Button btnDanhSachDonHang, btnDonLuuTam, btnKhuyenMai;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_don_hang, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initUI();
    }

    private void initUI() {
        btnDanhSachDonHang = (Button) getView().findViewById(R.id.btn_danhsachdonhang);
        btnDanhSachDonHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), QLHoaDon.class));
            }
        });

        btnDonLuuTam = (Button) getView().findViewById(R.id.btn_donluutam);
        btnDonLuuTam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        btnKhuyenMai = (Button) getView().findViewById(R.id.btn_khuyenmai);
        btnKhuyenMai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), QLKhuyenMai.class));
            }
        });
    }
}