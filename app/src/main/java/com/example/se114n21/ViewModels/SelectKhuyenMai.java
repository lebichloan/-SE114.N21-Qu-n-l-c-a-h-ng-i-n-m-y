package com.example.se114n21.ViewModels;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.app.ProgressDialog;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import androidx.appcompat.widget.SearchView;

import android.widget.TextView;
import android.widget.Toast;


import com.example.se114n21.Adapter.SelectKhuyenMaiAdapter;
import com.example.se114n21.Interface.SelectKhuyenMaiInterface;
import com.example.se114n21.Models.KhuyenMai;
import com.example.se114n21.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


public class SelectKhuyenMai extends AppCompatActivity {
    private ImageButton btnBack;
    private List<KhuyenMai> mListKhuyenMai;
    private SelectKhuyenMaiAdapter mSelectKhuyenMaiAdapter;
    private RecyclerView recyclerView;
    private ProgressDialog progressDialog;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private SearchView searchView;
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
    private Calendar cal = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_khuyen_mai);
        getSupportActionBar().hide();

        initUI();
        setData();
    }

    private void setData() {
        progressDialog = ProgressDialog.show(SelectKhuyenMai.this,"Đang tải", "Vui lòng đợi...",false,false);

        DatabaseReference myRef = database.getReference("KhuyenMai");

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    KhuyenMai khuyenMai = dataSnapshot.getValue(KhuyenMai.class);

                    String today = simpleDateFormat.format(new Date().getTime());
                    if (khuyenMai.getNgayKT().toString().compareTo(today) > 0) {
                        mListKhuyenMai.add(khuyenMai);
                    }
                }

                mSelectKhuyenMaiAdapter.notifyDataSetChanged();

                progressDialog.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(SelectKhuyenMai.this, "Có lỗi xảy ra!", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });
    }

    private void initUI() {
        btnBack = findViewById(R.id.btnBack_SelectKhuyenMai);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

//        RCV
        recyclerView = findViewById(R.id.rcv_SelectKhuyenMai);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);

        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(itemDecoration);

        mListKhuyenMai = new ArrayList<>();

        mSelectKhuyenMaiAdapter = new SelectKhuyenMaiAdapter(mListKhuyenMai, new SelectKhuyenMaiInterface() {
            @Override
            public void onClick(KhuyenMai khuyenMai) {
                Intent intent = new Intent(SelectKhuyenMai.this, ThemHoaDon.class);
                intent.putExtra("KM_TEN",khuyenMai.getTenKM());
                intent.putExtra("KM_PHANTRAM",khuyenMai.getKhuyenMai());
                intent.putExtra("KM_GIAMTOIDA",khuyenMai.getGiamToiDa());
                intent.putExtra("KM_DONTOITHIEU",khuyenMai.getDonToiThieu());
                setResult(RESULT_OK, intent);
                finish();
            }
        });

        recyclerView.setAdapter(mSelectKhuyenMaiAdapter);

//        SV
        searchView = findViewById(R.id.sv_SelectKhuyenMai);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                mSelectKhuyenMaiAdapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                mSelectKhuyenMaiAdapter.getFilter().filter(newText);
                return false;
            }
        });


    }
}