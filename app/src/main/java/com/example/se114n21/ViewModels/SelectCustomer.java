package com.example.se114n21.ViewModels;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;

import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.se114n21.Adapter.SelectCustomerAdapter;
import com.example.se114n21.Interface.SelectCustomerInterface;
import com.example.se114n21.Models.KhachHang;
import com.example.se114n21.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class SelectCustomer extends AppCompatActivity {
    private ImageButton btnBack;
    private List<KhachHang> mListKhachHang;
    private SelectCustomerAdapter mSelectCustomerAdapter;
    private RecyclerView rcv;
    private ProgressDialog progressDialog;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private SearchView searchView;
    private LinearLayout khachLe;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_customer);
        getSupportActionBar().hide();

        initUI();
        setData();
    }

    private void initUI() {

        btnBack = findViewById(R.id.btnBack_ThemKhachHang);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

//        SEARCH VIEW
        searchView = findViewById(R.id.sv_select_customer);
        searchView.setOnQueryTextListener(new androidx.appcompat.widget.SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                mSelectCustomerAdapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                mSelectCustomerAdapter.getFilter().filter(newText);
                return false;
            }
        });

//      DIALOG PROGRESS BAR
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Vui long doi mot chut");

//        RCV
        rcv = findViewById(R.id.rcv_select_customer);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        rcv.setLayoutManager(linearLayoutManager);

        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        rcv.addItemDecoration(itemDecoration);

        mListKhachHang = new ArrayList<>();

        mSelectCustomerAdapter = new SelectCustomerAdapter(mListKhachHang, this, new SelectCustomerInterface() {
            @Override
            public void onClick(KhachHang khachHang) {
                Intent intent = new Intent(SelectCustomer.this, ThemHoaDon.class);
                intent.putExtra("CUS_ID",khachHang.getMaKH());
                intent.putExtra("CUS_NAME",khachHang.getTen());
                intent.putExtra("CUS_PHONE",khachHang.getDienThoai());
                setResult(RESULT_OK, intent);
                finish();
            }
        });

        rcv.setAdapter(mSelectCustomerAdapter);

//        KHACH LE
        khachLe = findViewById(R.id.tv_khach_le);
        khachLe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SelectCustomer.this, ThemHoaDon.class);
                intent.putExtra("CUS_ID","Khách lẻ");
                intent.putExtra("CUS_NAME","--");
                intent.putExtra("CUS_PHONE","--");
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }

    private void setData() {
        progressDialog.show();

        DatabaseReference myRef = database.getReference("listKhachHang");

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    KhachHang khachHang = dataSnapshot.getValue(KhachHang.class);

                    mListKhachHang.add(khachHang);
                }

                mSelectCustomerAdapter.notifyDataSetChanged();

                progressDialog.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(SelectCustomer.this, "Có lỗi xảy ra. Vui lòng thử lại sau!", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });
    }
}