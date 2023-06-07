package com.example.se114n21.ViewModels;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.se114n21.Models.KhachHang;
import com.example.se114n21.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ThemHoaDon extends AppCompatActivity {
    private static final int MY_REQUEST_CODE =10 ;
    Button addkhachhang;
    TextView txtname;
    TextView txtaddress;
    TextView txtsdt;

    KhachHang kh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_them_hoa_don);
        InitUI();
        addkhachhang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GetdataCustomer();
            }
        });
    }
    private void GetdataCustomer() {
        Intent intent = new Intent(ThemHoaDon.this,CustomerActivity.class);
        startActivityForResult(intent, MY_REQUEST_CODE);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (MY_REQUEST_CODE == requestCode && resultCode == Activity.RESULT_OK)
        {
            txtname.setText(data.getStringExtra("key_name"));
            txtaddress.setText(data.getStringExtra("key_address"));
            txtsdt.setText(data.getStringExtra("key_sdt"));
        }
    }


    private void InitUI() {
        addkhachhang = findViewById(R.id.addcustomerbutton);
        txtname = findViewById(R.id.txtHoTen);
        txtaddress = findViewById(R.id.txtDiaChi);
        txtsdt = findViewById(R.id.txtSDT);
    }
}