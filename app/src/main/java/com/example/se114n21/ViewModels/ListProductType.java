package com.example.se114n21.ViewModels;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.se114n21.Adapter.ProductTypeAdapter;
import com.example.se114n21.Models.IdGenerator;
import com.example.se114n21.Models.LoaiSanPham;
import com.example.se114n21.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.net.Inet4Address;
import java.util.ArrayList;
import java.util.List;

public class ListProductType extends AppCompatActivity {
    private RecyclerView rcv_List_Product_Type;
    private ProductTypeAdapter mProductTypeAdapter;
    private List<LoaiSanPham> mListLoaiSanPham;
    private SearchView searchView;
    private Integer maxId = 0;
    private ProgressDialog progressDialog;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_product_type);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        initUI();

        getListProductType();
    }

    private void initUI() {

//      DIALOG PROGRESS BAR
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Vui long doi mot chut");

//      ACTION BAR
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Danh mục sản phẩm");

        actionBar.setHomeAsUpIndicator(R.drawable.baseline_arrow_back_ios_24);

//      RCV
        rcv_List_Product_Type = findViewById(R.id.rcv_list_product_type);
        rcv_List_Product_Type.setHasFixedSize(true);
        rcv_List_Product_Type.setItemViewCacheSize(20);
        rcv_List_Product_Type.setDrawingCacheEnabled(true);
        rcv_List_Product_Type.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        rcv_List_Product_Type.setLayoutManager(linearLayoutManager);

        mListLoaiSanPham = new ArrayList<>();
        mProductTypeAdapter = new ProductTypeAdapter(mListLoaiSanPham);

        rcv_List_Product_Type.setAdapter(mProductTypeAdapter);
    }

    private void getListProductType() {
        progressDialog.show();

        DatabaseReference myRef = database.getReference("listLoaiSanPham");

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (mListLoaiSanPham != null) {
                    mListLoaiSanPham.clear();
                    maxId = 0;
                }

                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    LoaiSanPham loaiSanPham = dataSnapshot.getValue(LoaiSanPham.class);

                    String id = loaiSanPham.getMaLSP();

                    if (id != null) {
                        maxId =  Integer.parseInt(id.substring(3));
                    }

                    mListLoaiSanPham.add(loaiSanPham);
                }

                mProductTypeAdapter.notifyDataSetChanged();

                progressDialog.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(ListProductType.this, "Get list product type failed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);

        SearchManager searchManager = (SearchManager)   getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setMaxWidth(Integer.MAX_VALUE);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                mProductTypeAdapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                mProductTypeAdapter.getFilter().filter(newText);
                return false;
            }
        });

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.action_add:
                openAddDialog(Gravity.CENTER);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void openAddDialog(int gravity) {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.custom_dialog);

        Window window = dialog.getWindow();

        if (window == null) {
            return;
        }

        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        WindowManager.LayoutParams windowAttributes = window.getAttributes();
        windowAttributes.gravity = gravity;
        window.setAttributes(windowAttributes);

//        nhan ra ngoai thi tat dialog
//        dialog.setCancelable(true);

        TextView tvDialogTitle = dialog.findViewById(R.id.tv_dialog_title);
        EditText editDialogFill = dialog.findViewById(R.id.edit_dialog_fill);
        Button btnCancel = dialog.findViewById(R.id.btn_dialog_cancel);
        Button btnOk = dialog.findViewById(R.id.btn_dialog_ok);

        tvDialogTitle.setText("Thêm loại sản phẩm");
        btnCancel.setText("Hủy");
        btnOk.setText("Lưu");

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();

                progressDialog.show();

                IdGenerator generator = new IdGenerator();
                generator.init("LSP", "", maxId, "%04d");

                String id = generator.generate();

                LoaiSanPham loaiSanPham = new LoaiSanPham(id, editDialogFill.getText().toString().trim());


                addProductType(loaiSanPham);
            }
        });

        dialog.show();
    }


    private void addProductType(LoaiSanPham loaiSanPham) {
        DatabaseReference myRef = database.getReference("listLoaiSanPham");

        myRef.child(loaiSanPham.getMaLSP()).setValue(loaiSanPham, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                progressDialog.dismiss();
                Toast.makeText(ListProductType.this, "Them thanh cong", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (!searchView.isIconified()) {
            searchView.setIconified(true);
            return;
        }
        super.onBackPressed();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }
}