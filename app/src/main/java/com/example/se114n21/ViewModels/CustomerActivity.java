package com.example.se114n21.ViewModels;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.se114n21.Adapter.CustomerApdater;
import com.example.se114n21.Adapter.KhuyenMaiAdapter;
import com.example.se114n21.Interface.CustomerInterface;
import com.example.se114n21.Interface.KhuyenMaiInterface;
import com.example.se114n21.Models.KhachHang;
import com.example.se114n21.Models.KhuyenMai;
import com.example.se114n21.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class CustomerActivity extends AppCompatActivity {
    private ImageButton btnBack;
    private SearchView searchView;
    private RecyclerView recyclerView;
    private List<KhachHang> mListKhachHang;
    private CustomerApdater mCustomerApdater;
    private ProgressDialog progressDialog;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private Button btnAddCus;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.customer);
        getSupportActionBar().hide();

        initUI();
        getListKhachHang();
    }

    private void getListKhachHang() {
        progressDialog = ProgressDialog.show(CustomerActivity.this,"Đang tải", "Vui lòng đợi...",false,false);

        DatabaseReference myRef = database.getReference("listKhachHang");

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (mListKhachHang != null) {
                    mListKhachHang.clear();
                }

                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    KhachHang khachHang = dataSnapshot.getValue(KhachHang.class);

                    mListKhachHang.add(khachHang);
                }

                mCustomerApdater.notifyDataSetChanged();

                progressDialog.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                progressDialog.dismiss();
                Toast.makeText(CustomerActivity.this, "Có lỗi xảy ra!", Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void initUI() {
        btnBack = findViewById(R.id.btnBack_QuanLyKhachHang);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        //        RCV
        recyclerView = findViewById(R.id.recycleview_customer);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);

        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(itemDecoration);

        mListKhachHang = new ArrayList<>();

        mCustomerApdater = new CustomerApdater(mListKhachHang, new CustomerInterface() {
            @Override
            public void onClick(KhachHang khachHang) {
                Intent intent = new Intent(CustomerActivity.this, CustomerDetail.class);
                intent.putExtra("MaKH", khachHang.getMaKH());
                startActivity(intent);
            }
        });

        recyclerView.setAdapter(mCustomerApdater);



        searchView = findViewById(R.id.search_bar);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                mCustomerApdater.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                mCustomerApdater.getFilter().filter(newText);
                return false;
            }
        });

        btnAddCus = findViewById(R.id.new_customer);
        btnAddCus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(CustomerActivity.this, AddCustomerActivity.class));
            }
        });
    }
}
