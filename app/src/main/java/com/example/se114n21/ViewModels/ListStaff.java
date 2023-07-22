package com.example.se114n21.ViewModels;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.se114n21.Adapter.ListStaffAdapter;
import com.example.se114n21.Interface.StaffInterface;
import com.example.se114n21.Models.NhanVien;
import com.example.se114n21.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import androidx.appcompat.widget.SearchView;

import java.util.ArrayList;
import java.util.List;

public class ListStaff extends AppCompatActivity {
    private ImageButton butBack;
    private FloatingActionButton btnAdd;
    private ProgressDialog progressDialog;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private SearchView searchView;

//    RCV
    private RecyclerView recyclerView;
    private List<NhanVien> mListNhanVien;
    private ListStaffAdapter mListStaffAdapter;

//    LAUNCHER
    private ActivityResultLauncher<Intent> addStaffLauncher;
    private ActivityResultLauncher<Intent> detailStaffLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_staff);
        getSupportActionBar().hide();

        initUI();

        butBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        getListStaff();

        addStaffLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == Activity.RESULT_OK) {
                            getListStaff();
                        }
                    }
                });

        detailStaffLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == Activity.RESULT_OK) {
                            getListStaff();
                        }
                    }
                });
    }

    private void getListStaff() {
        progressDialog.show();

        DatabaseReference myRef = database.getReference("Users");

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (mListNhanVien != null) {
                    mListNhanVien.clear();
                }

                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    NhanVien nhanVien = dataSnapshot.getValue(NhanVien.class);

                    mListNhanVien.add(nhanVien);
                }

                mListStaffAdapter.notifyDataSetChanged();

                progressDialog.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(ListStaff.this, "Có lỗi xảy ra!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initUI() {
//        PROGRESS DIALOG
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait!");
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);

//        BACK BUTTON
        butBack = findViewById(R.id.btnBack);

//        ADD STAFF BUTTON
        btnAdd = findViewById(R.id.btn_add_staff);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ListStaff.this, AddStaff.class);
                addStaffLauncher.launch(intent);
            }
        });

//        RCV
        recyclerView = findViewById(R.id.rcv_list_staff);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);

        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(itemDecoration);

        mListNhanVien = new ArrayList<>();

        mListStaffAdapter = new ListStaffAdapter(mListNhanVien, new StaffInterface() {
            @Override
            public void onClick(NhanVien nhanVien) {
                Intent intent = new Intent(ListStaff.this, DetailStaff.class);
                intent.putExtra("MaND", nhanVien.getMaND());
                detailStaffLauncher.launch(intent);
            }
        });

        recyclerView.setAdapter(mListStaffAdapter);

//        SEARCH VIEW
        searchView = findViewById(R.id.sv_staff);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                mListStaffAdapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                mListStaffAdapter.getFilter().filter(newText);
                return false;
            }
        });
    }
}