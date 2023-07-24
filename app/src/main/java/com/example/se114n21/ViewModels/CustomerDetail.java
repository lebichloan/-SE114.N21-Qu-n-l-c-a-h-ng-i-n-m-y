package com.example.se114n21.ViewModels;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.se114n21.Models.KhachHang;
import com.example.se114n21.Models.NhanVien;
import com.example.se114n21.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class CustomerDetail extends AppCompatActivity {
    private ImageButton btnBack, btnEdit;
    private TextView id, name, phone, email, address;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private ProgressDialog progressDialog;
    private KhachHang khachHang = new KhachHang();
    private Button btnDelete;
    private ActivityResultLauncher<Intent> updateCustomerLauncher;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.customer_detail);
        getSupportActionBar().hide();

        initUI();

        Intent intent = getIntent();
        String MaKH = intent.getStringExtra("MaKH");

        getData(MaKH);

        updateCustomerLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == Activity.RESULT_OK) {
                            getData(MaKH);
                        }
                    }
                });
    }

    private void getData(String MaKH) {
        progressDialog = ProgressDialog.show(CustomerDetail.this,"Đang tải", "Vui lòng đợi...",false,false);
        DatabaseReference myRef = database.getReference("listKhachHang/" + MaKH);

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                khachHang = snapshot.getValue(KhachHang.class);
                setData(khachHang);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                progressDialog.dismiss();
                Toast.makeText(CustomerDetail.this, "Có lỗi xảy ra!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setData(KhachHang khachHang) {
        id.setText(khachHang.getMaKH());
        name.setText(khachHang.getTen());
        phone.setText(khachHang.getDienThoai());
        email.setText(khachHang.getEmail());
        address.setText(khachHang.getDiaChi());

        progressDialog.dismiss();
    }

    private void initUI() {
        btnBack = findViewById(R.id.btnBack_ChiTietKhachHang);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        id = findViewById(R.id.id);
        name = findViewById(R.id.name);
        phone = findViewById(R.id.phone);
        email = findViewById(R.id.email);
        address = findViewById(R.id.address);

        btnDelete = findViewById(R.id.btnDelete);
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteKH();
            }
        });

        btnEdit = findViewById(R.id.btnEdit);
        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CustomerDetail.this, UpdateCustomer.class);
                intent.putExtra("MaKH", khachHang.getMaKH());
                intent.putExtra("TenKH", khachHang.getTen());
                intent.putExtra("SoDienThoaiKH", khachHang.getDienThoai());
                intent.putExtra("EmailKH", khachHang.getEmail());
                intent.putExtra("DiaChiKH", khachHang.getDiaChi());
                updateCustomerLauncher.launch(intent);
            }
        });
    }

    private void deleteKH() {
        new AlertDialog.Builder(CustomerDetail.this)
                .setTitle("Xóa")
                .setMessage("Bạn có chắc chắn xóa khách hàng này không?")
                .setPositiveButton("Xóa", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        progressDialog.show();

                        String path = "listKhachHang/" + khachHang.getMaKH();

                        DatabaseReference myRef = database.getReference(path);

                        myRef.removeValue(new DatabaseReference.CompletionListener() {
                            @Override
                            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                                if (error == null) {
                                    Toast.makeText(CustomerDetail.this, "Xóa khách hàng thành công!", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(CustomerDetail.this, "Có lỗi xảy ra!", Toast.LENGTH_SHORT).show();
                                }
                                progressDialog.dismiss();

                                onBackPressed();
                            }
                        });
                    }
                })
                .setNegativeButton("Hủy", null)
                .show();
    }
}
