package com.example.se114n21.ViewModels;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.se114n21.Adapter.AdapterChitietHD;
import com.example.se114n21.Models.ChiTietHoaDon;
import com.example.se114n21.Models.HoaDon;
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
    TextView sohd;
    TextView ngayhd;
    TextView tongtien;
    TextView makh;
    TextView chietkhau;
    TextView philapdat;
    TextView phivanchuyen;
    TextView sdt;
    TextView diachi;
    TextView ghichu;
    TextView manv;
    TextView thanhtien;
    TextView phuongthuc;
    TextView tongtienhang;
    Integer tongtiensanpham;
    String texttongtien;
    SanPham sp;
    String tensp;
    Number soluong;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference ref3 = database.getReference("test");
    ChiTietHoaDon chiTietHoaDon;
    List<ChiTietHoaDon> listchitiet;
    RecyclerView recyclerView;
    AdapterChitietHD adapterChitietHD;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hoadon_details);
        InitUI();
        listchitiet = new ArrayList<>();
        DatabaseReference ref1 = database.getReference("listHoaDon/"+getIntent().getStringExtra("detailsohd")+"/chiTietHD");
        DatabaseReference ref2 = database.getReference("listHoaDon");
        ref1.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                chiTietHoaDon = snapshot.getValue(ChiTietHoaDon.class);
                if (chiTietHoaDon != null)
                {
                    listchitiet.add(chiTietHoaDon);
                    adapterChitietHD.notifyDataSetChanged();
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this,DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);

        adapterChitietHD = new AdapterChitietHD(listchitiet)
        {
        };
        recyclerView.setAdapter(adapterChitietHD);
        sohd.setText(getIntent().getStringExtra("detailsohd"));
        makh.setText(getIntent().getStringExtra("detailmakh"));
        ngayhd.setText(getIntent().getStringExtra("detailngayhd"));
        manv.setText(getIntent().getStringExtra("detailmanv"));
        chietkhau.setText(getIntent().getStringExtra("detailchietkhau"));
        tongtien.setText(getIntent().getStringExtra("detailtonggiatri"));
        phivanchuyen.setText(getIntent().getStringExtra("detailphivanchuyen"));
        philapdat.setText(getIntent().getStringExtra("detailphilapdat"));
        diachi.setText(getIntent().getStringExtra("detaildiachi"));
        ghichu.setText(getIntent().getStringExtra("detailghichu"));
        sdt.setText(getIntent().getStringExtra("detailsdt"));
        thanhtien.setText(getIntent().getStringExtra("detailtonggiatri"));
        phuongthuc.setText(getIntent().getStringExtra("detailphuongthuc"));
        tongtienhang.setText(getIntent().getStringExtra("detailtongtienhang"));
    }

    private void InitUI() {
        sohd = findViewById(R.id.detail_mahd);
        ngayhd = findViewById(R.id.detail_ngayhd);
        makh = findViewById(R.id.detail_makh);
        chietkhau = findViewById(R.id.detail_chietkhau);
        philapdat = findViewById(R.id.detail_philapdat);
        phivanchuyen = findViewById(R.id.detail_phivanchuyen);
        sdt = findViewById(R.id.detail_sdt);
        diachi = findViewById(R.id.detail_diachilapdat);
        ghichu = findViewById(R.id.detail_ghichu);
        manv = findViewById(R.id.detail_manv);
        tongtien = findViewById(R.id.detail_tongtienphaitra);
        thanhtien = findViewById(R.id.detail_thanhtien);
        phuongthuc = findViewById(R.id.detail_phuongthuc);
        tongtienhang = findViewById(R.id.detail_tongtienhang);
        recyclerView = findViewById(R.id.recycle_detai_chitiethd);
    }
}
