package com.example.se114n21.ViewModels;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.example.se114n21.Models.KhachHang;
import com.example.se114n21.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;

public class admin_QLKhachHang extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_qlkhach_hang);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationAdminView);
        bottomNavigationView.setSelectedItemId(R.id.bottom_admin_khachHang);

        bottomNavigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()){
                case R.id.bottom_admin_khachHang:
                    return true;
                case R.id.bottom_admin_sanPham:
                    startActivity(new Intent(getApplicationContext(), admin_QLSanPham.class));
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                    finish();
                    return true;
                case R.id.bottom_admin_hoaDon:
                    startActivity(new Intent(getApplicationContext(), admin_QLHoaDon.class));
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                    finish();
                    return true;
                case R.id.bottom_admin_nhanVien:
                    startActivity(new Intent(getApplicationContext(), admin_QLNhanVien.class));
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                    finish();
                    return true;
                case R.id.bottom_admin_account:
                    startActivity(new Intent(getApplicationContext(), AdminMain.class));
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                    finish();
            }
            return false;
        });

    }
}