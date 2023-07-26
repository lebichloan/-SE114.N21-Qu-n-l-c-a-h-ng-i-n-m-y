package com.example.se114n21.ViewModels;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.se114n21.Adapter.ProductTypeAdapter;
import com.example.se114n21.Interface.ProductTypeInterface;
import com.example.se114n21.Models.IdGenerator;
import com.example.se114n21.Models.LoaiSanPham;
import com.example.se114n21.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ListProductType extends AppCompatActivity {
    private ImageButton btnBack, btnAdd;
    private RecyclerView rcv_List_Product_Type;
    private ProductTypeAdapter mProductTypeAdapter;
    private List<LoaiSanPham> mListLoaiSanPham;
    private SearchView searchView;
    private Integer maxId = 0;
    private ProgressDialog progressDialog;
    private ProductTypeInterface mProductTypeInterface;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
//  passing code
    String code = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_product_type);
        getSupportActionBar().hide();

        initUI();

        getCode();

        getListProductType();
    }

    private void getCode() {
        Intent i = getIntent();
        code = i.getStringExtra("code");
    }

    private void initUI() {
        //        SEARCH VIEW
        searchView = findViewById(R.id.searchview);
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

        btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        btnAdd = findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openAddDialogAdd(Gravity.CENTER);
            }
        });



//      DIALOG PROGRESS BAR
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Đang tải. . .");


//      RCV
        rcv_List_Product_Type = findViewById(R.id.rcv_list_product_type);
        rcv_List_Product_Type.setItemViewCacheSize(20);
        rcv_List_Product_Type.setDrawingCacheEnabled(true);
        rcv_List_Product_Type.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        rcv_List_Product_Type.setLayoutManager(linearLayoutManager);


        mListLoaiSanPham = new ArrayList<>();
        mProductTypeAdapter = new ProductTypeAdapter(mListLoaiSanPham, new ProductTypeInterface() {
            @Override
            public void onClick(LoaiSanPham loaiSanPham, String code) {

                switch (code) {
                    case "edit":
                        openAddDialogUpdate(Gravity.CENTER, loaiSanPham);
                        break;
                    case "delete":
                        deleteProductType(loaiSanPham);
                        break;
                    case "pick":
                        if (code != "") {
                            Intent intent = new Intent(ListProductType.this, AddProductActivity.class);
                            intent.putExtra("LSP_ID",loaiSanPham.getMaLSP());
                            intent.putExtra("LSP_NAME",loaiSanPham.getTenLSP());
                            setResult(RESULT_OK, intent);
                            finish();
                        }
                        break;
                }

            }
        });

        rcv_List_Product_Type.setAdapter(mProductTypeAdapter);
    }

    private void deleteProductType(LoaiSanPham loaiSanPham) {
        new AlertDialog.Builder(ListProductType.this)
            .setTitle("Xóa loại sản phẩm?")
            .setMessage("Bạn có chắc chắc muốn xóa loại sản phẩm này không?")
            .setPositiveButton("Xóa", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    progressDialog.show();

                    String path = "listLoaiSanPham/" + loaiSanPham.getMaLSP();

                    DatabaseReference myRef = database.getReference(path);

                    myRef.removeValue(new DatabaseReference.CompletionListener() {
                        @Override
                        public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                            progressDialog.dismiss();
//                            Toast.makeText(ListProductType.this, "delete thanh cong", Toast.LENGTH_SHORT).show();
                            showCustomDialogSucess("Xóa loại sản phẩm thành công");
                        }
                    });
                }
            })
            .setNegativeButton("Cancel", null)
            .show();
    }

    private void showCustomDialogConfirm(String data){
        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_confirm, null);
        builder.setView(dialogView);
        Dialog dialog = builder.create();
        TextView txtContent = dialogView.findViewById(R.id.txtContent);
        txtContent.setText(data);
        Button butOK = dialogView.findViewById(R.id.butOK);
        butOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        Button butCancel = dialogView.findViewById(R.id.butCancel);
        butCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        Window dialogWindow = dialog.getWindow();
        if (dialogWindow != null) {
            WindowManager.LayoutParams layoutParams = dialogWindow.getAttributes();
            layoutParams.gravity = Gravity.TOP;
            layoutParams.y = (int) getResources().getDimension(R.dimen.dialog_margin_top);
            dialogWindow.setAttributes(layoutParams);
        }
        dialog.show();

    }

    private void showCustomDialogFail(String data){
        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogViewFail = inflater.inflate(R.layout.dialog_fail, null);
        builder.setView(dialogViewFail);
        Dialog dialog = builder.create();

        TextView txtAlert = dialogViewFail.findViewById(R.id.txtAlert);
        txtAlert.setText(data);
        Button butOK = dialogViewFail.findViewById(R.id.butOK);
        butOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        Window dialogWindow = dialog.getWindow();
        if (dialogWindow != null) {
            WindowManager.LayoutParams layoutParams = dialogWindow.getAttributes();
            layoutParams.gravity = Gravity.TOP;
            layoutParams.y = (int) getResources().getDimension(R.dimen.dialog_margin_top);
            dialogWindow.setAttributes(layoutParams);
        }
        dialog.show();
    }

    private void showCustomDialogSucess(String data){
        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogViewFail = inflater.inflate(R.layout.dialog_sucess, null);
        builder.setView(dialogViewFail);
        Dialog dialog = builder.create();

        TextView txtContent = dialogViewFail.findViewById(R.id.txtContent);
        txtContent.setText(data);

        Window dialogWindow = dialog.getWindow();
        if (dialogWindow != null) {
            WindowManager.LayoutParams layoutParams = dialogWindow.getAttributes();
            layoutParams.gravity = Gravity.TOP;
            layoutParams.y = (int) getResources().getDimension(R.dimen.dialog_margin_top);
            dialogWindow.setAttributes(layoutParams);
        }
        dialog.show();
    }

    private void openAddDialogUpdate(int gravity, LoaiSanPham loaiSanPham) {
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

        tvDialogTitle.setText("Cập nhật loại sản phẩm");
        editDialogFill.setText(loaiSanPham.getTenLSP());
        btnCancel.setText("Hủy");
        btnOk.setText("Cập nhật");

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

//                UPDATE
                String path = "listLoaiSanPham/" + loaiSanPham.getMaLSP();
                DatabaseReference myRef = database.getReference(path);

                Map<String, Object> map = new HashMap<>();
                map.put("tenLSP",editDialogFill.getText().toString().trim());

                myRef.updateChildren(map, new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                        progressDialog.dismiss();
//                        Toast.makeText(ListProductType.this, "update thanh cong", Toast.LENGTH_SHORT).show();
                        showCustomDialogSucess("Cập nhật thông tin loại sản phẩm thành công");
                    }
                });


            }
        });

        dialog.show();
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
//                Toast.makeText(ListProductType.this, "Get list product type failed", Toast.LENGTH_SHORT).show();
                showCustomDialogFail("Có lỗi xảy ra khi hiển thị danh sách sản phẩm");
            }
        });
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.main_menu, menu);
//
//        SearchManager searchManager = (SearchManager)   getSystemService(Context.SEARCH_SERVICE);
//        searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
//        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
//        searchView.setMaxWidth(Integer.MAX_VALUE);
//
//        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String query) {
//                mProductTypeAdapter.getFilter().filter(query);
//                return false;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String newText) {
//                mProductTypeAdapter.getFilter().filter(newText);
//                return false;
//            }
//        });
//
//        return true;
//    }

//    @Override
//    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
//        switch (item.getItemId())
//        {
//            case R.id.action_add:
//                openAddDialogAdd(Gravity.CENTER);
//                break;
//        }
//        return super.onOptionsItemSelected(item);
//    }

    private void openAddDialogAdd(int gravity) {
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
//                Toast.makeText(ListProductType.this, "Them thanh cong", Toast.LENGTH_SHORT).show();
                showCustomDialogSucess("Thêm loại sản phẩm thành công");
            }
        });
    }

}