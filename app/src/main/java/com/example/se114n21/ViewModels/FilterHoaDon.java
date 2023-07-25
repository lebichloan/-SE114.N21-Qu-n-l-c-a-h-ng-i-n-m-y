package com.example.se114n21.ViewModels;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.se114n21.R;

public class FilterHoaDon extends AppCompatActivity {
    CheckBox cb_khachle;
    CheckBox cb_dadk;
    CheckBox cb_tienmat;
    CheckBox cb_chuyenkhoan;
    CheckBox cb_quetthe;
    Button datlai;
    Button loc;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.filter_hoadon);
        InitUI();
        datlai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cb_khachle.setChecked(false);
                cb_dadk.setChecked(false);
                cb_chuyenkhoan.setChecked(false);
                cb_quetthe.setChecked(false);
                cb_tienmat.setChecked(false);
            }
        });
    }

    private void InitUI() {
        cb_khachle = findViewById(R.id.checkbox_khachle);
        cb_dadk = findViewById(R.id.checkbox_dadk);
        cb_tienmat = findViewById(R.id.checkbox_tienmat);
        cb_chuyenkhoan = findViewById(R.id.checkbox_chuyenkhoan);
        cb_quetthe = findViewById(R.id.checkbox_quetthe);
        datlai = findViewById(R.id.button_datlai);
        loc = findViewById(R.id.button_loc);
    }
}
