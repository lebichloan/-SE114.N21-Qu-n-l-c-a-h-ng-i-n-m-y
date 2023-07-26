package com.example.se114n21.ViewModels;

import android.content.Intent;
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
    String getkhachle;
    String getdadk;
    String getchuyenkhoan;
    String getquetthe;
    String gettienmat;
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
        loc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FilterHoaDon.this, QLHoaDon.class);
                if (cb_khachle.isChecked()){
                    getkhachle = cb_khachle.getText().toString();
                }
                if (cb_dadk.isChecked())
                {
                    getdadk = cb_khachle.getText().toString();
                }
                if (cb_chuyenkhoan.isChecked()){
                    getchuyenkhoan = cb_chuyenkhoan.getText().toString();
                }
                if (cb_quetthe.isChecked())
                {
                    getquetthe = cb_quetthe.getText().toString();
                }
                if (cb_tienmat.isChecked())
                {
                    gettienmat = cb_tienmat.getText().toString();
                }
                intent.putExtra("status_khachle",getkhachle);
                intent.putExtra("status_dadk",getdadk);
                intent.putExtra("status_chuyenkhoan",getchuyenkhoan);
                intent.putExtra("status_quetthe",getquetthe);
                intent.putExtra("status_tienmat",gettienmat);
                startActivity(intent);
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
