package com.example.se114n21.ViewModels;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
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

import com.example.se114n21.Adapter.HoaDonAdapter;
import com.example.se114n21.Interface.HoaDonInterface;
import com.example.se114n21.Models.HoaDon;
import com.example.se114n21.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DoanhThuDetail extends AppCompatActivity {
    private ImageButton btnBack;
    private ProgressDialog progressDialog;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private RecyclerView recyclerView;
    private List<HoaDon> mListHoaDon;
    private HoaDonAdapter mHoaDonAdapter;
    private ActivityResultLauncher<Intent> CTHDLauncher;
    private String NGAYHD, CODE = "";
    private TextView title, doanhthu, name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doanh_thu_detail);
        getSupportActionBar().hide();

        Intent intent = getIntent();
        NGAYHD = intent.getStringExtra("NGAYHD");

        if (intent.getStringExtra("CODE") != null) {
            CODE = intent.getStringExtra("CODE");
        }

        
        initUI();

        if (CODE.equals("loinhuan")) {
            name.setText("LỢI NHUẬN");
        }

        title.setText(NGAYHD);

        getListHoaDon();

        CTHDLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == Activity.RESULT_OK) {
                        }
                    }
                });
    }

    private void getListHoaDon() {
        progressDialog = ProgressDialog.show(DoanhThuDetail.this,"Đang tải", "Vui lòng đợi...",false,false);

        DatabaseReference myRef = database.getReference("listHoaDon");

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (mListHoaDon != null) {
                    mListHoaDon.clear();
                }

                Integer tong = 0;

                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    HoaDon hoaDon = dataSnapshot.getValue(HoaDon.class);
                    Date ngayHD = null;
                    try {
                        ngayHD = new SimpleDateFormat("HH:mm dd/MM/yyyy").parse(hoaDon.getNgayHD());
                        String SngayHD = new SimpleDateFormat("dd/MM/yyyy").format(ngayHD);
                        if (SngayHD.equals(NGAYHD)) {
                            if (CODE.equals("loinhuan")) {
                                tong = tong + hoaDon.getTongTienPhaiTra() - hoaDon.getTienVon();
                            } else {
                                tong = tong + hoaDon.getTongTienPhaiTra();
                            }
                            mListHoaDon.add(hoaDon);
                        }
                    } catch (ParseException e) {
                        throw new RuntimeException(e);
                    }
                }

                mHoaDonAdapter.notifyDataSetChanged();

                doanhthu.setText(tong.toString());

                progressDialog.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                progressDialog.dismiss();
//                Toast.makeText(DoanhThuDetail.this, "Có lỗi xảy ra!", Toast.LENGTH_SHORT).show();
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
        name = findViewById(R.id.name);
        title = findViewById(R.id.Title);
        doanhthu = findViewById(R.id.doanhthu);

        btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        //        RCV
        recyclerView = findViewById(R.id.rcv);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);

        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(itemDecoration);

        mListHoaDon = new ArrayList<>();

        mHoaDonAdapter= new HoaDonAdapter(mListHoaDon, new HoaDonInterface() {
            @Override
            public void onClick(HoaDon hoaDon) {
                Intent intent = new Intent(DoanhThuDetail.this, HoaDonDetail.class);
                intent.putExtra("MaHD", hoaDon.getMaHD());
                CTHDLauncher.launch(intent);
            }
        }, CODE);

        recyclerView.setAdapter(mHoaDonAdapter);
    }
}