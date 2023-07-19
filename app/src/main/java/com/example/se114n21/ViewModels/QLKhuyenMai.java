package com.example.se114n21.ViewModels;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.se114n21.Adapter.KhuyenMaiAdapter;
import com.example.se114n21.Models.KhuyenMai;
import com.example.se114n21.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class QLKhuyenMai extends AppCompatActivity {

    SearchView search_view;
    RecyclerView recyclerViewKhuyenMai;
    Button butAdd;
    DatabaseReference khuyenMaiRef;
    KhuyenMaiAdapter adapter;
    List<KhuyenMai> khuyenMaiList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qlkhuyen_mai);

        initUI();

        khuyenMaiList = new ArrayList<>();
        recyclerViewKhuyenMai.setLayoutManager(new LinearLayoutManager(this));
        adapter = new KhuyenMaiAdapter(this, khuyenMaiList);
        recyclerViewKhuyenMai.setAdapter(adapter);

        khuyenMaiRef = FirebaseDatabase.getInstance().getReference("KhuyenMai");
        khuyenMaiRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                khuyenMaiList.clear();

                for (DataSnapshot khuyenMaiSnapshot : snapshot.getChildren()){
                    KhuyenMai khuyenMai = khuyenMaiSnapshot.getValue(KhuyenMai.class);
                    khuyenMaiList.add(khuyenMai);
                }

                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getApplicationContext(), "Error" + error.getMessage(), Toast.LENGTH_SHORT).show();
                Log.d("QLKhuyenMai", "Error: " + error.getMessage());            }
        });

        butAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), AddSale.class));
            }
        });
    }

    private void initUI() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Quản lý khuyến mãi");
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_back_white);

        search_view = findViewById(R.id.search_view);
        recyclerViewKhuyenMai = findViewById(R.id.recyclerViewKhuyenMai);
        butAdd = findViewById(R.id.but_add_sale);

    }
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}