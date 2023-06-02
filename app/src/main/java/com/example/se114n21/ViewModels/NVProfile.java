package com.example.se114n21.ViewModels;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.example.se114n21.R;

public class NVProfile extends AppCompatActivity {

    TextView txtMaNV, txtHoTen, txtNgaySinh, txtGioiTinh, txtDiaChi, txtSDT, txtNgayVaoLam, txtLuongTheoGio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nvprofile);

        initUI();
    }

    private void initUI() {
        txtMaNV = findViewById(R.id.txtMaNV);
        txtHoTen = findViewById(R.id.txtHoTen);
        txtNgaySinh = findViewById(R.id.txtNgaySinh);
        txtGioiTinh = findViewById(R.id.txtGioiTinh);
        txtDiaChi = findViewById(R.id.txtDiaChi);
        txtSDT = findViewById(R.id.txtSDT);
        txtNgayVaoLam = findViewById(R.id.txtNgayVaoLam);
        txtLuongTheoGio = findViewById(R.id.txtLuongTheoGio);
    }
}