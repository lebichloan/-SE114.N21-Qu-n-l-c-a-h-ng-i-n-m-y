package com.example.se114n21.ViewModels;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.se114n21.Adapter.AdapterChitietHD;
import com.example.se114n21.Adapter.CTHDAdapter;
import com.example.se114n21.Adapter.HoaDonAdapter;
import com.example.se114n21.Interface.HoaDonInterface;
import com.example.se114n21.Models.ChiTietHoaDon;
import com.example.se114n21.Models.HoaDon;
import com.example.se114n21.Models.NhanVien;
import com.example.se114n21.Models.SanPham;
import com.example.se114n21.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class HoaDonDetail extends AppCompatActivity {
    private ImageButton btnBack;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private RecyclerView recyclerView;
    private List<ChiTietHoaDon> mListCTHD;
    private CTHDAdapter mCthdAdapter;
    private ProgressDialog progressDialog;
    private TextView mahoadon, nhanvien, thoigian, khachhang, sodienthoai, diachinhanhang, sodienthoaigiaohang,
                    tongtienhang, phivanchuyen, philapdat, chietkhau, khuyenmai, tongthanhtoan, phuongthucthanhtoan, ghichu;
    private String CODE = "";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hoadon_details);
        getSupportActionBar().hide();

        initUI();

        Intent i = getIntent();
        String MaHD = i.getStringExtra("MaHD");

        getData(MaHD);
    }

    private void  getData(String MaHD) {
        progressDialog = ProgressDialog.show(HoaDonDetail.this,"Đang tải", "Vui lòng đợi...",false,false);
        DatabaseReference myRef = database.getReference("listHoaDon/" + MaHD);

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                HoaDon hoaDon = snapshot.getValue(HoaDon.class);
                setData(hoaDon);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                progressDialog.dismiss();
                Toast.makeText(HoaDonDetail.this, "Có lỗi xảy ra!", Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void setData(HoaDon hoaDon) {
        mahoadon.setText(hoaDon.getMaHD());
        nhanvien.setText(hoaDon.getMaNV());
        thoigian.setText(hoaDon.getNgayHD());
        khachhang.setText(hoaDon.getTenKH());
        sodienthoai.setText(hoaDon.getSoDienThoaiKH());
        diachinhanhang.setText(hoaDon.getDiaCHiNhanHang());
        sodienthoaigiaohang.setText(hoaDon.getDienThoaiNhanHang());
        tongtienhang.setText(String.valueOf(hoaDon.getTongTienHang()));
        phivanchuyen.setText(String.valueOf(hoaDon.getPhiVanChuyen()));
        philapdat.setText(String.valueOf(hoaDon.getPhiLapDat()));
        chietkhau.setText(String.valueOf(hoaDon.getChietKhau()));
        khuyenmai.setText(String.valueOf(hoaDon.getKhuyenMai()));
        tongthanhtoan.setText(String.valueOf(hoaDon.getTongTienPhaiTra()));
        phuongthucthanhtoan.setText(hoaDon.getPhuongThucThanhToan());
        ghichu.setText(hoaDon.getGhiChu());

        mListCTHD.addAll(hoaDon.getChiTietHD());
        mCthdAdapter.notifyDataSetChanged();

        progressDialog.dismiss();
    }

    private void initUI() {
        btnBack= findViewById(R.id.btnBack_ChiTietHoaDon);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

//        RCV
        recyclerView = findViewById(R.id.rcv_CTHD);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);

        mListCTHD = new ArrayList<>();

        mCthdAdapter= new CTHDAdapter(mListCTHD);

        recyclerView.setAdapter(mCthdAdapter);

//        TEXTVIEW
        mahoadon = findViewById(R.id.MaHD_CTHD);
        nhanvien = findViewById(R.id.MaNV_CTHD);
        thoigian = findViewById(R.id.ThoiGian_CTHD);
        khachhang = findViewById(R.id.TenKH_CTHD);
        sodienthoai = findViewById(R.id.SoDienThoaiKH_CTHD);
        diachinhanhang = findViewById(R.id.DiaChiNhanHang_CTHD);
        sodienthoaigiaohang = findViewById(R.id.SoDienThoaiNhanHang_CTHD);
        tongtienhang = findViewById(R.id.TongTienHang_CTHD);
        phivanchuyen = findViewById(R.id.PhiVanChuyen_CTHD);
        philapdat = findViewById(R.id.PhiLapDat_CTHD);
        chietkhau = findViewById(R.id.ChietKhau_CTHD);
        khuyenmai = findViewById(R.id.KhuyenMai_CTHD);
        tongthanhtoan = findViewById(R.id.TongThanhToan_CTHD);
        phuongthucthanhtoan = findViewById(R.id.PhuongThucThanhToan_CTHD);
        ghichu = findViewById(R.id.GhiChu_CTHD);
    }
}
