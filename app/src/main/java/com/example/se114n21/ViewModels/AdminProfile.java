package com.example.se114n21.ViewModels;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.example.se114n21.R;

public class AdminProfile extends AppCompatActivity {

    TextView txtHoTen, txtNgaySinh, txtGioiTinh, txtDiaChi, txtSDT;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_profile);

        initUI();
    }

    private void initUI() {
        txtHoTen = findViewById(R.id.txtHoTen);
        txtNgaySinh = findViewById(R.id.txtNgaySinh);
        txtGioiTinh = findViewById(R.id.txtGioiTinh);
        txtDiaChi = findViewById(R.id.txtDiaChi);
        txtSDT = findViewById(R.id.txtSDT);
    }
}