package com.example.se114n21.ViewModels;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.se114n21.Adapter.BaoCaoAdapter;
import com.example.se114n21.Adapter.TonKhoAdapter;
import com.example.se114n21.Interface.BaoCaoInterface;
import com.example.se114n21.Interface.TonKhoInterface;
import com.example.se114n21.Models.HoaDon;
import com.example.se114n21.Models.ItemBaoCao;
import com.example.se114n21.Models.SanPham;
import com.example.se114n21.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class BaoCaoKho extends AppCompatActivity {
    private ImageButton btnBack;
    private TextView giatritonkho, soluong;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();

    private RecyclerView recyclerView;
    private List<SanPham> mListSanPham;
    private TonKhoAdapter mTonKhoAdapter;
    private ProgressDialog progressDialog;
    private SearchView searchView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bao_cao_kho);
        getSupportActionBar().hide();

        initUI();
        getListProduct();
    }
    private void getListProduct() {
        progressDialog = ProgressDialog.show(BaoCaoKho.this,"Đang tải", "Vui lòng đợi...",false,false);


        DatabaseReference myRef = database.getReference("listSanPham");

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (mListSanPham != null) {
                    mListSanPham.clear();
                }

                Integer sl = 0;
                Integer tong = 0;
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    SanPham sanPham = dataSnapshot.getValue(SanPham.class);
                    sl = sl + sanPham.getSoLuong();
                    tong = tong + sanPham.getSoLuong() * sanPham.getGiaNhap();

                    mListSanPham.add(sanPham);
                }

                mTonKhoAdapter.notifyDataSetChanged();

                giatritonkho.setText(tong.toString());
                soluong.setText(sl.toString());

                progressDialog.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                progressDialog.dismiss();
                Toast.makeText(BaoCaoKho.this, "Có lỗi xảy ra!", Toast.LENGTH_SHORT).show();
            }
        });
    }




    private void initUI() {
        btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        giatritonkho = findViewById(R.id.giatritonkho);
        soluong = findViewById(R.id.soluong);

        recyclerView = findViewById(R.id.rcv);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);

        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(itemDecoration);

        mListSanPham = new ArrayList<>();

        mTonKhoAdapter = new TonKhoAdapter(mListSanPham, new TonKhoInterface() {
            @Override
            public void onClick(SanPham sanPham) {
                Intent intent = new Intent(BaoCaoKho.this, DetailProduct.class);
                intent.putExtra("ID", sanPham.getMaSP());
                startActivity(intent);
            }
        });

        recyclerView.setAdapter(mTonKhoAdapter);

        //        SEARCH VIEW
        searchView = findViewById(R.id.searchview);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                mTonKhoAdapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                mTonKhoAdapter.getFilter().filter(newText);
                return false;
            }
        });
    }
}