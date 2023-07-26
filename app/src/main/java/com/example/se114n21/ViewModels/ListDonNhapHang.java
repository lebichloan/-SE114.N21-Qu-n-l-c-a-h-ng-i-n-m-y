package com.example.se114n21.ViewModels;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.se114n21.Adapter.DNHAdapter;
import com.example.se114n21.Adapter.ListStaffAdapter;
import com.example.se114n21.Interface.DNHInterface;
import com.example.se114n21.Interface.StaffInterface;
import com.example.se114n21.Models.DonNhapHang;
import com.example.se114n21.Models.NhanVien;
import com.example.se114n21.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ListDonNhapHang extends AppCompatActivity {
    private ImageButton btnBack;
    private FloatingActionButton btnAdd;
    private ProgressDialog progressDialog;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private SearchView searchView;

    private RecyclerView recyclerView;
    private List<DonNhapHang> mListDNH;
    private DNHAdapter mDNHAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_don_nhap_hang);
        getSupportActionBar().hide();

        initUI();
        getListDNH();
    }

    private void getListDNH() {
        progressDialog = ProgressDialog.show(ListDonNhapHang.this,"Đang tải", "Vui lòng đợi...",false,false);

        DatabaseReference myRef = database.getReference("DonNhapHang");

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (mListDNH != null) {
                    mListDNH.clear();
                }

                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    DonNhapHang donNhapHang = dataSnapshot.getValue(DonNhapHang.class);

                    mListDNH.add(donNhapHang);
                }

                mDNHAdapter.notifyDataSetChanged();

                progressDialog.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                progressDialog.dismiss();
//                Toast.makeText(ListDonNhapHang.this, "Có lỗi xảy ra!", Toast.LENGTH_SHORT).show();
                showCustomDialogFail("Có lỗi xảy ra. Vui lòng thử lại sau");
            }
        });
    }

    private void showCustomDialogFail(String data){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogViewFail = inflater.inflate(R.layout.dialog_fail, null);
        builder.setView(dialogViewFail);
        Dialog dialog = builder.create();

        TextView txtAlert = dialogViewFail.findViewById(R.id.txtAlert);
        txtAlert.setText(data);
//        Button butOK = dialogViewFail.findViewById(R.id.butOK);
//        butOK.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                dialog.dismiss();
//            }
//        });

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

    private void initUI() {
        btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        btnAdd = findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ListDonNhapHang.this, AddDonNhapHang.class));
            }
        });

//        RCV
        recyclerView = findViewById(R.id.rcv);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);

        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(itemDecoration);

        mListDNH = new ArrayList<>();

        mDNHAdapter = new DNHAdapter(mListDNH, new DNHInterface() {
            @Override
            public void onClick(DonNhapHang donNhapHang) {
                Intent intent = new Intent(ListDonNhapHang.this, DonNhapHangDetail.class);
                intent.putExtra("MaDNH", donNhapHang.getMaDNH());
                startActivity(intent);
            }
        });

        recyclerView.setAdapter(mDNHAdapter);



        searchView = findViewById(R.id.searchview);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                mDNHAdapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                mDNHAdapter.getFilter().filter(newText);
                return false;
            }
        });
    }
}