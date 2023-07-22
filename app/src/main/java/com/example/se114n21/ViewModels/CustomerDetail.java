package com.example.se114n21.ViewModels;

import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.se114n21.R;

public class CustomerDetail extends AppCompatActivity {
    TextView tenkh;
    TextView sdt;
    TextView email;
    TextView makh;
    TextView loaikh;
    TextView diachikh;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.customer_detail);

        InitUI();
        tenkh.setText(getIntent().getStringExtra("detail_tenkhachhang"));
        sdt.setText(getIntent().getStringExtra("detail_sdtkhachhang"));
        email.setText(getIntent().getStringExtra("detail_emailkhachhang"));
        makh.setText(getIntent().getStringExtra("detail_makhachhang"));
        loaikh.setText(getIntent().getStringExtra("detail_loaikhachhang"));
        diachikh.setText(getIntent().getStringExtra("detail_diachikhachhang"));
    }

    private void InitUI() {
        tenkh = findViewById(R.id.detail_tenkhachhang);
        sdt = findViewById(R.id.detail_sdtkh);
        email = findViewById(R.id.detail_emailkh);
        makh = findViewById(R.id.detail_mkh);
        loaikh = findViewById(R.id.detail_loaikhachhang);
        diachikh = findViewById(R.id.detail_diachikh);
    }
}
