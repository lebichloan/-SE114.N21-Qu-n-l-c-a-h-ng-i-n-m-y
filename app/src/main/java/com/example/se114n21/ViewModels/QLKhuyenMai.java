package com.example.se114n21.ViewModels;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
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
import android.widget.TextView;
import android.widget.Toast;

import com.example.se114n21.Adapter.KhuyenMaiAdapter;
import com.example.se114n21.Models.KhuyenMai;
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

    SearchView search_view;
    RecyclerView recyclerViewKhuyenMai;
    Button butAdd;
    DatabaseReference khuyenMaiRef;
    KhuyenMaiAdapter adapter;
    List<KhuyenMai> khuyenMaiList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qlkhuyen_mai);

        initUI();
        getData();

        butAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), AddSale.class));
            }
        });
    }

    private void getData() {
        khuyenMaiList = new ArrayList<>();
        recyclerViewKhuyenMai.setLayoutManager(new LinearLayoutManager(this));
//        adapter = new KhuyenMaiAdapter(this, khuyenMaiList);
        adapter = new KhuyenMaiAdapter(khuyenMaiList, new KhuyenMaiAdapter.IclickListener() {
            @Override
            public void OnClickUpdateitem(KhuyenMai khuyenMai) {

            }

            @Override
            public void OnClickDeleteitem(KhuyenMai khuyenMai) {
                showCustomDialogConfirm("Bạn có chắc chắn muốn xóa chương trình khuyến mãi đã chọn ?", khuyenMai);
            }

        });

        recyclerViewKhuyenMai.setAdapter(adapter);

        khuyenMaiRef = FirebaseDatabase.getInstance().getReference("KhuyenMai");
//        khuyenMaiRef.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                khuyenMaiList.clear();
//
//                for (DataSnapshot khuyenMaiSnapshot : snapshot.getChildren()){
//                    KhuyenMai khuyenMai = khuyenMaiSnapshot.getValue(KhuyenMai.class);
//                    khuyenMaiList.add(khuyenMai);
//                }
//
//                adapter.notifyDataSetChanged();
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//                Toast.makeText(getApplicationContext(), "Error" + error.getMessage(), Toast.LENGTH_SHORT).show();
//                Log.d("QLKhuyenMai", "Error: " + error.getMessage());            }
//        });

        khuyenMaiRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                KhuyenMai khuyenMai = snapshot.getValue(KhuyenMai.class);
                if (khuyenMai != null){
                    khuyenMaiList.add(khuyenMai);
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                KhuyenMai khuyenMai = snapshot.getValue(KhuyenMai.class);
                if (khuyenMai == null || khuyenMaiList == null || khuyenMaiList.isEmpty()){
                    return;
                }
                for (int i=0; i<khuyenMaiList.size(); i++){
                    if (khuyenMai.getMaKM() == khuyenMaiList.get(i).getMaKM()){
                        khuyenMaiList.set(i, khuyenMai);
                    }
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                KhuyenMai khuyenMai = snapshot.getValue(KhuyenMai.class);
                if (khuyenMai == null || khuyenMaiList == null || khuyenMaiList.isEmpty()){
                    return;
                }
                for (int i=0; i<khuyenMaiList.size(); i++){
                    if (khuyenMai.getMaKM() == khuyenMaiList.get(i).getMaKM()){
                        khuyenMaiList.remove(khuyenMaiList.get(i));
                        break;
                    }
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    }

    private void onClickDeleteData(KhuyenMai khuyenMai) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myref = database.getReference("KhuyenMai");

        myref.child(String.valueOf(khuyenMai.getMaKM())).removeValue(new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
//                Toast.makeText(getApplicationContext(), "Delete Successfull",Toast.LENGTH_SHORT).show();
                showCustomDialogSucess("Xóa chương trình khuyến mãi thành công");
            }
        });
    }

    private void showCustomDialogConfirm(String data, KhuyenMai khuyenMai){
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
                onClickDeleteData(khuyenMai);
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
    private void showCustomDialogSucess(String data){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogViewFail = inflater.inflate(R.layout.dialog_sucess, null);
        builder.setView(dialogViewFail);
        Dialog dialog = builder.create();

        TextView txtAlert = dialogViewFail.findViewById(R.id.txtContent);
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

    private void initUI() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Quản lý khuyến mãi");
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_back_white);

        search_view = findViewById(R.id.search_view);
        recyclerViewKhuyenMai = findViewById(R.id.recyclerViewKhuyenMai);
        butAdd = findViewById(R.id.but_add_sale);

//        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
//        recyclerViewKhuyenMai.setLayoutManager(linearLayoutManager);
//
//        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this,DividerItemDecoration.VERTICAL);
//        recyclerViewKhuyenMai.addItemDecoration(dividerItemDecoration);

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