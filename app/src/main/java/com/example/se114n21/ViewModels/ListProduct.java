package com.example.se114n21.ViewModels;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.se114n21.Adapter.ProductAdapter;
import com.example.se114n21.Interface.ProductInterface;
import com.example.se114n21.Models.SanPham;
import com.example.se114n21.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ListProduct extends AppCompatActivity {
    private RecyclerView rcvListProduct;
    private List<SanPham> mListSanPham;
    private ProductAdapter mProductAdapter;
    private ProgressDialog progressDialog;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private ActivityResultLauncher<Intent> launcher;
    private ActivityResultLauncher<Intent> launcher_add_product;
    private Button btnAddProduct;
    private SearchView searchView;
    private ImageButton btnBack;
    private String code = "";
    private TextView Title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_product);
        getSupportActionBar().hide();

        initUI();
        getListProduct();

        Intent intent = getIntent();

        if (intent.getStringExtra("code") != null) {
            code = intent.getStringExtra("code");
        }

        if (code.equals("pick") && code != null) {
            Title.setText("Chọn sản phẩm");
            btnAddProduct.setVisibility(View.GONE);
        }

    }

    private void initUI() {
        Title = findViewById(R.id.Title);


        btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
//        SEARCH VIEW
        searchView = findViewById(R.id.search_view_product);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                mProductAdapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                mProductAdapter.getFilter().filter(newText);
                return false;
            }
        });


//        BUTTON ADD
        btnAddProduct = findViewById(R.id.btn_add_product);
        btnAddProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ListProduct.this, AddProductActivity.class);
                launcher_add_product.launch(intent);
            }
        });

//        progressDialog
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Vui long doi mot chut");
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);

//        RCV

        rcvListProduct = findViewById(R.id.rcv_list_product);

        rcvListProduct.setItemViewCacheSize(10);
        rcvListProduct.setDrawingCacheEnabled(true);
        rcvListProduct.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        rcvListProduct.setLayoutManager(linearLayoutManager);

        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        rcvListProduct.addItemDecoration(itemDecoration);


        mListSanPham = new ArrayList<>();

        mProductAdapter = new ProductAdapter(mListSanPham, this, new ProductInterface() {
            @Override
            public void onClick(SanPham sanPham) {
                if (code.equals("pick")) {
                    Intent intent = new Intent(ListProduct.this, AddDonNhapHang.class);
                    intent.putExtra("SP_ID",sanPham.getMaSP());
                    intent.putExtra("SP_NAME",sanPham.getTenSP());
                    intent.putExtra("SP_SL",sanPham.getSoLuong());
                    setResult(RESULT_OK, intent);
                    finish();
                } else {
                    Intent intent = new Intent(ListProduct.this, DetailProduct.class);
                    intent.putExtra("ID", sanPham.getMaSP());
                    launcher.launch(intent);
                }
            }
        });

        rcvListProduct.setAdapter(mProductAdapter);

//        LAUNCHER
        launcher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == Activity.RESULT_OK) {
                            getListProduct();
                        }
                    }
                });

        launcher_add_product = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == Activity.RESULT_OK) {
                            getListProduct();
                        }
                    }
                });
    }

    private void getListProduct() {
        progressDialog.show();
        mListSanPham.clear();

        DatabaseReference myRef = database.getReference("listSanPham");

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    SanPham sanPham = dataSnapshot.getValue(SanPham.class);

                    mListSanPham.add(sanPham);
                }

                mProductAdapter.notifyDataSetChanged();

                progressDialog.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                progressDialog.dismiss();
                Toast.makeText(ListProduct.this, "Có lỗi xảy ra!", Toast.LENGTH_SHORT).show();
            }
        });
    }

}