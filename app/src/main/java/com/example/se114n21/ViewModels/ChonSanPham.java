package com.example.se114n21.ViewModels;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import androidx.appcompat.widget.SearchView;


import android.widget.ImageButton;

import android.widget.TextView;

import android.widget.Toast;

import com.example.se114n21.Adapter.SelectProductAdapter;
import com.example.se114n21.Models.SanPham;
import com.example.se114n21.Models.SelectProduct;
import com.example.se114n21.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ChonSanPham extends AppCompatActivity {
    private ImageButton btnBack;
    private Button btnClear, btnDone;
    private ProgressDialog progressDialog;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private SearchView searchView;

//    RCV
    private RecyclerView recyclerView;
    private List<SelectProduct> mListSelectProduct;
    private SelectProductAdapter mSelectProductAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chon_san_pham);
        getSupportActionBar().hide();

        initUI();
        getData();
    }

    private void initUI() {
        btnBack = findViewById(R.id.btnBack_ChonSanPham);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
//        SEARCH VIEW
        searchView = findViewById(R.id.search_view_select_product);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                mSelectProductAdapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                mSelectProductAdapter.getFilter().filter(newText);
                return false;
            }
        });


//        progressDialog
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Vui long doi mot chut");
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);

//        BUTTON
        btnClear = findViewById(R.id.btn_clear_selected);
        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<SelectProduct> list = new ArrayList<>();
                list.addAll(mSelectProductAdapter.getAll());

                if (list.size() > 0) {
                    for (int i=0; i< list.size(); i++) {
                        if (list.get(i).isSeleted()) {
                            list.get(i).setSeleted(false);

                            mListSelectProduct.set(i, list.get(i));
                            mSelectProductAdapter.notifyItemChanged(i);
                        }
                    }
                }
            }
        });

        btnDone = findViewById(R.id.btn_done_select_product);
        btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<SanPham> list = new ArrayList<>();
                list.addAll(mSelectProductAdapter.getProductSelected());

                if (list.size() > 0) {
                    StringBuilder stringBuilder = new StringBuilder();

                    ArrayList<String> listId = new ArrayList<String>();


                    for (int i=0; i < list.size(); i++) {
                        stringBuilder.append(list.get(i).getMaSP());
                        stringBuilder.append("\n");
                        listId.add(list.get(i).getMaSP());
                    }

                    Intent intent = new Intent(ChonSanPham.this, ThemHoaDon.class);
                    intent.putStringArrayListExtra("listId", (ArrayList<String>) listId);
                    setResult(RESULT_OK, intent);
                    finish();

                } else {
//                    Toast.makeText(ChonSanPham.this, "No selection", Toast.LENGTH_SHORT).show();
                    showCustomDialogFail("Vui lòng chọn sản phẩm để tiếp tục");
                }
            }
        });

//        RVC
        recyclerView = findViewById(R.id.rcv_chon_san_pham);

        recyclerView.setItemViewCacheSize(10);
        recyclerView.setDrawingCacheEnabled(true);
        recyclerView.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);

        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(itemDecoration);

        mListSelectProduct = new ArrayList<>();

        mSelectProductAdapter = new SelectProductAdapter(this, mListSelectProduct);

        recyclerView.setAdapter(mSelectProductAdapter);
    }

    private void getData() {
        progressDialog.show();
        mListSelectProduct.clear();

        DatabaseReference myRef = database.getReference("listSanPham");

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    SanPham sanPham = dataSnapshot.getValue(SanPham.class);

                    if (sanPham.getSoLuong() > 0) {
                        SelectProduct selectProduct = new SelectProduct(sanPham,false);
                        mListSelectProduct.add(selectProduct);
                    }
                }

                mSelectProductAdapter.notifyDataSetChanged();

                progressDialog.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void showCustomDialogConfirm(String data){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
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
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogViewFail = inflater.inflate(R.layout.dialog_fail, null);
        builder.setView(dialogViewFail);
        Dialog dialog = builder.create();

        TextView txtAlert = dialogViewFail.findViewById(R.id.txtAlert);
        txtAlert.setText(data);

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
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
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

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


}