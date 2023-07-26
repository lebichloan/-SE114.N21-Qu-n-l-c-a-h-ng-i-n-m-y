package com.example.se114n21.ViewModels;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.se114n21.Adapter.KhuyenMaiAdapter;
import com.example.se114n21.Adapter.ListStaffAdapter;
import com.example.se114n21.Interface.KhuyenMaiInterface;
import com.example.se114n21.Interface.StaffInterface;
import com.example.se114n21.Models.HoaDon;
import com.example.se114n21.Models.KhuyenMai;
import com.example.se114n21.Models.NhanVien;
import com.example.se114n21.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class QLKhuyenMai extends AppCompatActivity {
    private ImageButton btnBack;
    private RecyclerView recyclerView;
    private List<KhuyenMai> mListKhuyenMai;
    private KhuyenMaiAdapter mKhuyenMaiAdapter;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private ProgressDialog progressDialog;
    private Button btnAdd;
    private ActivityResultLauncher<Intent> updateKhuyenMaiLauncher;
    private SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qlkhuyen_mai);
        getSupportActionBar().hide();

        initUI();
        getListKM();

        updateKhuyenMaiLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == Activity.RESULT_OK) {
                        }
                    }
                });
    }

    private void getListKM() {
        progressDialog = ProgressDialog.show(QLKhuyenMai.this,"Đang tải", "Vui lòng đợi...",false,false);

        DatabaseReference myRef = database.getReference("KhuyenMai");

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (mListKhuyenMai != null) {
                    mListKhuyenMai.clear();
                }

                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    KhuyenMai khuyenMai = dataSnapshot.getValue(KhuyenMai.class);

                    mListKhuyenMai.add(khuyenMai);
                }

                mKhuyenMaiAdapter.notifyDataSetChanged();

                progressDialog.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                progressDialog.dismiss();
                Toast.makeText(QLKhuyenMai.this, "Có lỗi xảy ra!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initUI() {
        btnAdd = findViewById(R.id.but_add_sale);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(QLKhuyenMai.this, AddSale.class));
            }
        });


        btnBack = findViewById(R.id.btnBack_QuanLyKhuyenMai);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

//        RCV
        recyclerView = findViewById(R.id.rcv_QuanLyKhuyenMai);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);

        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(itemDecoration);

        mListKhuyenMai = new ArrayList<>();

        mKhuyenMaiAdapter = new KhuyenMaiAdapter(mListKhuyenMai, new KhuyenMaiInterface() {
            @Override
            public void onClick(KhuyenMai khuyenMai, String code) {
                if (code.equals("edit")) {
                    Intent intent = new Intent(QLKhuyenMai.this, EditSale.class);
                    intent.putExtra("MaKM", khuyenMai.getMaKM());
                    updateKhuyenMaiLauncher.launch(intent);
                } else {
                    deleteKM(khuyenMai);
                }
            }
        });

        recyclerView.setAdapter(mKhuyenMaiAdapter);

        searchView = findViewById(R.id.searchView_QuanLyKhuyenMai);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                mKhuyenMaiAdapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                mKhuyenMaiAdapter.getFilter().filter(newText);
                return false;
            }
        });
    }

    private void deleteKM(KhuyenMai khuyenMai) {
        new AlertDialog.Builder(QLKhuyenMai.this)
                .setTitle("Xóa")
                .setMessage("Bạn có chắc chắn xóa khuyến mãi này không?")
                .setPositiveButton("Xóa", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        progressDialog = ProgressDialog.show(QLKhuyenMai.this,"Đang tải", "Vui lòng đợi...",false,false);

                        String path = "KhuyenMai/" + khuyenMai.getMaKM();

                        DatabaseReference myRef = database.getReference(path);

                        myRef.removeValue(new DatabaseReference.CompletionListener() {
                            @Override
                            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                                if (error == null) {
                                    Toast.makeText(QLKhuyenMai.this, "Xóa khuyến mãi thành công!", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(QLKhuyenMai.this, "Có lỗi xảy ra!", Toast.LENGTH_SHORT).show();
                                }
                                progressDialog.dismiss();
                            }
                        });
                    }
                })
                .setNegativeButton("Hủy", null)
                .show();
    }
}