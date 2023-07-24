package com.example.se114n21.ViewModels;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.se114n21.Adapter.AdapterHoaDon;
import com.example.se114n21.Adapter.HoaDonAdapter;
import com.example.se114n21.Adapter.ListStaffAdapter;
import com.example.se114n21.Interface.HoaDonInterface;
import com.example.se114n21.Interface.StaffInterface;
import com.example.se114n21.Models.HoaDon;
import com.example.se114n21.Models.NhanVien;
import com.example.se114n21.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class QLHoaDon extends AppCompatActivity {
    private ImageButton btnBack;
    private ProgressDialog progressDialog;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private Button btnAddHoaDon;
    private SearchView searchView;
//    RCV
    private RecyclerView recyclerView;
    private List<HoaDon> mListHoaDon;
    private HoaDonAdapter mHoaDonAdapter;
    private ActivityResultLauncher<Intent> CTHDLauncher;
    private ActivityResultLauncher<Intent> ThemHoaDonLauncher;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qlhoa_don);
        getSupportActionBar().hide();

        initUI();
        getListHoaDon();

        CTHDLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == Activity.RESULT_OK) {
                        }
                    }
                });

        ThemHoaDonLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == Activity.RESULT_OK) {
                        }
                    }
                });
    }

    private void getListHoaDon() {
        progressDialog = ProgressDialog.show(QLHoaDon.this,"Đang tải", "Vui lòng đợi...",false,false);

        DatabaseReference myRef = database.getReference("listHoaDon");

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (mListHoaDon != null) {
                    mListHoaDon.clear();
                }

                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    HoaDon hoaDon = dataSnapshot.getValue(HoaDon.class);

                    mListHoaDon.add(hoaDon);
                }

                mHoaDonAdapter.notifyDataSetChanged();

                progressDialog.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                progressDialog.dismiss();
                Toast.makeText(QLHoaDon.this, "Có lỗi xảy ra!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initUI() {
        btnBack = findViewById(R.id.btnBack_QuanLyHoaDon);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        btnAddHoaDon = findViewById(R.id.addHoadonbut);
        btnAddHoaDon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(QLHoaDon.this, ThemHoaDon.class);
                ThemHoaDonLauncher.launch(intent);
            }
        });

//        RCV
        recyclerView = findViewById(R.id.recycleview_hoadon);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);

        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(itemDecoration);

        mListHoaDon = new ArrayList<>();

        mHoaDonAdapter= new HoaDonAdapter(mListHoaDon, new HoaDonInterface() {
            @Override
            public void onClick(HoaDon hoaDon) {
                Intent intent = new Intent(QLHoaDon.this, HoaDonDetail.class);
                intent.putExtra("MaHD", hoaDon.getMaHD());
                CTHDLauncher.launch(intent);
            }
        });

        recyclerView.setAdapter(mHoaDonAdapter);

//        SEARCH VIEW
        searchView = findViewById(R.id.search_hd);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                mHoaDonAdapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                mHoaDonAdapter.getFilter().filter(newText);
                return false;
            }
        });
    }
}