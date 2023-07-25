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

public class BaoCaoFragment extends Fragment {
    private Button doanhthu, loinhuan, kho;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_bao_cao, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initUI();
    }

    private void initUI() {
        doanhthu = getView().findViewById(R.id.doanhthu);
        loinhuan = getView().findViewById(R.id.loinhuan);
        kho = getView().findViewById(R.id.kho);

        doanhthu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), BaoCaoDoanhThu.class));
            }
        });

        loinhuan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), BaoCaoLoiNhuan.class));
            }
        });

        kho.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), BaoCaoKho.class));
            }
        });
    }
}