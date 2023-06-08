package com.example.se114n21.ViewModels;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.se114n21.Models.KhachHang;
import com.example.se114n21.Models.LoaiSanPham;
import com.example.se114n21.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ThemHoaDon extends AppCompatActivity {
    private static final int MY_REQUEST_CODE =10 ;
    Button addkhachhang;
    TextView txtname;
    TextView txtaddress;
    TextView txtsdt;

    KhachHang kh;

    private Button btnSelectProduct;
    private RecyclerView recyclerView;


    private ActivityResultLauncher<Intent> launcher;

    private List<String> mListID = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_them_hoa_don);
        InitUI();
        addkhachhang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GetdataCustomer();
            }
        });
    }
    private void GetdataCustomer() {
        Intent intent = new Intent(ThemHoaDon.this,CustomerActivity.class);
        startActivityForResult(intent, MY_REQUEST_CODE);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (MY_REQUEST_CODE == requestCode && resultCode == Activity.RESULT_OK)
        {
            txtname.setText(data.getStringExtra("key_name"));
            txtaddress.setText(data.getStringExtra("key_address"));
            txtsdt.setText(data.getStringExtra("key_sdt"));
        }
    }


    private void InitUI() {
//        Action bar
        androidx.appcompat.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Thêm hóa đơn");
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.baseline_arrow_back_ios_24);


//
        addkhachhang = findViewById(R.id.addcustomerbutton);
        txtname = findViewById(R.id.txtHoTen);
        txtaddress = findViewById(R.id.txtDiaChi);
        txtsdt = findViewById(R.id.txtSDT);


//      LAUNCHER
        launcher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == Activity.RESULT_OK) {
                            Intent intent = result.getData();

                            mListID  = intent.getStringArrayListExtra("listId");
//                            Tra ve danh sach ID san pham

                        }
                    }
                });


//        BUTTON SELECT
        btnSelectProduct = findViewById(R.id.btn_select_product);
        btnSelectProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ThemHoaDon.this, ChonSanPham.class);
                launcher.launch(intent);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId())
        {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}