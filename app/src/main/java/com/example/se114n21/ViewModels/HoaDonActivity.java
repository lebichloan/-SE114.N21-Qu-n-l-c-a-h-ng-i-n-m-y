package com.example.se114n21.ViewModels;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.example.se114n21.R;

public class HoaDonActivity extends AppCompatActivity {
    ImageButton addbutton;
    RelativeLayout hdthanhtoan;
    RelativeLayout hdluutam;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hoadon);
        InitUI();
        addbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HoaDonActivity.this, ThemHoaDon.class);
                startActivity(intent);
            }
        });
        hdthanhtoan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HoaDonActivity.this, QLHoaDon.class);
                startActivity(intent);
            }
        });
    }

    private void InitUI() {
        addbutton = findViewById(R.id.themmoihoadon);
        hdthanhtoan = findViewById(R.id.hd_dathanhtoan);
        hdluutam = findViewById(R.id.hd_luutam);
    }
}
