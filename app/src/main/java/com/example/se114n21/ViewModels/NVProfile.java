package com.example.se114n21.ViewModels;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.se114n21.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class NVProfile extends AppCompatActivity {

    EditText txtMaNV, txtHoTen, txtNgaySinh, txtGioiTinh, txtDiaChi, txtSDT, txtNgayVaoLam, txtLuongTheoGio;
    String manv, hoTen, ngaySinh, gioiTinh, diaChi, sdt, ngayVaoLam, luongTheoGio, email;
    Button butSave;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nvprofile);

        initUI();
        reference = FirebaseDatabase.getInstance().getReference("NhanVien");
        showData();

        butSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isHoTenChanged() || isNgaySinhChanged() || isGioiTinhChanged() || isDiaChiChanged() || isSDTChanged() || isNgayVaoLamChanged() || isLuongTheoGio()) {
                    Toast.makeText(NVProfile.this, "Saved", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(NVProfile.this, "No changed to save", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private boolean isLuongTheoGio() {
        if (! luongTheoGio.equals(txtLuongTheoGio.getText().toString())){
            reference.child(email).child("LuongTheoGio").setValue(txtLuongTheoGio.getText().toString());
            luongTheoGio = txtLuongTheoGio.getText().toString();
            return true;
        } else {
            return false;
        }
    }

    private boolean isNgayVaoLamChanged() {
        if (! ngayVaoLam.equals(txtNgayVaoLam.getText().toString())){
            reference.child(email).child("NgayVaoLam").setValue(txtNgayVaoLam.getText().toString());
            ngayVaoLam = txtNgayVaoLam.getText().toString();
            return true;
        } else {
            return false;
        }
    }

    private boolean isSDTChanged() {
        if (! sdt.equals(txtSDT.getText().toString())){
            reference.child(email).child("SDT").setValue(txtSDT.getText().toString());
            sdt = txtSDT.getText().toString();
            return true;
        } else {
            return false;
        }
    }

    private boolean isDiaChiChanged() {
        if (! diaChi.equals(txtDiaChi.getText().toString())){
            reference.child(email).child("DiaChi").setValue(txtDiaChi.getText().toString());
            diaChi = txtDiaChi.getText().toString();
            return true;
        } else {
            return false;
        }
    }

    private boolean isGioiTinhChanged() {
        if (! gioiTinh.equals(txtGioiTinh.getText().toString())){
            reference.child(email).child("GioiTinh").setValue(txtGioiTinh.getText().toString());
            gioiTinh = txtGioiTinh.getText().toString();
            return true;
        } else {
            return false;
        }
    }

    private boolean isNgaySinhChanged() {
        if (! ngaySinh.equals(txtNgaySinh.getText().toString())){
            reference.child(email).child("NgaySinh").setValue(txtNgaySinh.getText().toString());
            ngaySinh = txtNgaySinh.getText().toString();
            return true;
        } else {
            return false;
        }
    }

    private boolean isHoTenChanged() {
        if (! hoTen.equals(txtHoTen.getText().toString())){
            reference.child(email).child("HoTen").setValue(txtHoTen.getText().toString());
            hoTen = txtHoTen.getText().toString();
            return true;
        } else {
            return false;
        }
    }

    public void showData() {
        Intent intent = getIntent();

        manv = intent.getStringExtra("MaNV");
        hoTen = intent.getStringExtra("HoTen");
        ngaySinh = intent.getStringExtra("NgaySinh");
        gioiTinh = intent.getStringExtra("GioiTinh");
        diaChi = intent.getStringExtra("DiaChi");
        sdt = intent.getStringExtra("SDT");
        ngayVaoLam = intent.getStringExtra("NgayVaoLam");
        luongTheoGio = intent.getStringExtra("LuongTheoGio");

        txtMaNV.setText(manv);
        txtHoTen.setText(hoTen);
        txtNgaySinh.setText(ngaySinh);
        txtGioiTinh.setText(gioiTinh);
        txtDiaChi.setText(diaChi);
        txtSDT.setText(sdt);
        txtNgayVaoLam.setText(ngayVaoLam);
        txtLuongTheoGio.setText(luongTheoGio);
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
        butSave = findViewById(R.id.butSave);
    }
}