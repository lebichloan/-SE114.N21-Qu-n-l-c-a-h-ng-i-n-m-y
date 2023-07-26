package com.example.se114n21.ViewModels;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.se114n21.Models.DonNhapHang;
import com.example.se114n21.Models.IdGenerator;
import com.example.se114n21.Models.NhanVien;
import com.example.se114n21.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class AddDonNhapHang extends AppCompatActivity {
    private ImageButton btnBack;
    private ActivityResultLauncher<Intent> selectProductLauncher;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private EditText sanpham, soluong;
    private String ID, NAME;
    private Integer SL;
    private Button btnAdd;
    private ProgressDialog progressDialog;
    SharedPreferences sharedPreferences;
    private static final String SHARED_PREF_NAME = "mypre";
    private static final String KEY_ID = "id";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_don_nhap_hang);
        getSupportActionBar().hide();

        sharedPreferences = getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);

        initUI();
    }

    private void initUI() {
        btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        sanpham = findViewById(R.id.sanpham);
        sanpham.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddDonNhapHang.this, ListProduct.class);
                intent.putExtra("code","pick");
                selectProductLauncher.launch(intent);
            }
        });
        
        soluong = findViewById(R.id.soluong);

        selectProductLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == Activity.RESULT_OK) {
                            Intent intent = result.getData();
                            ID = intent.getStringExtra("SP_ID");
                            NAME = intent.getStringExtra("SP_NAME");
                            SL = intent.getIntExtra("SP_SL", 0);

                            sanpham.setText(ID + " - " + NAME);
                        }
                    }
                });
        
        btnAdd = findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addDNH();
            }
        });
    }
    
    private void addDNH() {
        if (checkForm() == true) {
            if (Integer.parseInt(soluong.getText().toString().trim()) <= 0) {
//                Toast.makeText(this, "Số lượng sản phẩm phải lớn hơn 0", Toast.LENGTH_SHORT).show();
                showCustomDialogFail("Số lượng sản phẩm phải lớn hơn 0");
            } else {
                getMaxId();
            }
        }
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

    boolean save = false;
    private void getMaxId() {
        progressDialog = ProgressDialog.show(AddDonNhapHang.this,"Đang tải", "Vui lòng đợi...",false,false);

        DatabaseReference myRef = database.getReference("maxDonNhapHang");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (save == false) {
                    String MaDNH = snapshot.getValue(String.class);
                    Integer maxId = Integer.parseInt(MaDNH.substring(3));

                    DonNhapHang donNhapHang = new DonNhapHang();
                    donNhapHang.setMaDNH(createID(maxId));

                    donNhapHang.setMaSP(ID);
                    donNhapHang.setTenSP(NAME);
                    donNhapHang.setSoLuongNhap(Integer.parseInt(soluong.getText().toString().trim()));
                    donNhapHang.setSoLuongTraHang(0);

                    Date currentTime = Calendar.getInstance().getTime();
                    SimpleDateFormat sdf = new SimpleDateFormat("HH:mm dd/MM/yyyy");
                    donNhapHang.setNgayDNH(sdf.format(currentTime));

                    setMaNV(donNhapHang);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
//                Toast.makeText(AddDonNhapHang.this, "Có lỗi xảy ra!", Toast.LENGTH_SHORT).show();
                showCustomDialogFail("Có lỗi xảy ra. Vui lòng thử lại sau");
                progressDialog.dismiss();
            }
        });
    }

    private void setMaNV(DonNhapHang donNhapHang) {
        String MaND = sharedPreferences.getString(KEY_ID, null);
        DatabaseReference myRef = database.getReference("Users/" + MaND);
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                NhanVien nhanVien = snapshot.getValue(NhanVien.class);
                if (nhanVien.getLoaiNhanVien().equals("Admin")) {
                    donNhapHang.setMaNV("Admin - " + nhanVien.getHoTen());
                } else {
                    donNhapHang.setMaNV(nhanVien.getMaNV() + " - " + nhanVien.getHoTen());
                }

                addToDB(donNhapHang);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                progressDialog.dismiss();
//                Toast.makeText(AddDonNhapHang.this, "Có lỗi xảy ra!", Toast.LENGTH_SHORT).show();
                showCustomDialogFail("Có lỗi xảy ra. Vui lòng thử lại sau");
            }
        });
    }

    private void addToDB(DonNhapHang donNhapHang) {
        DatabaseReference myRef = database.getReference("DonNhapHang");
        myRef.child(donNhapHang.getMaDNH()).setValue(donNhapHang, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                save = true;

                updateSP(donNhapHang);

            }
        });
    }

    private void updateSP(DonNhapHang donNhapHang) {
        DatabaseReference myRef = database.getReference("listSanPham/" + donNhapHang.getMaSP() + "/soLuong");

        Integer tong = SL + donNhapHang.getSoLuongNhap();

        myRef.setValue(tong, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                if (error == null) {
                    updateMaxId(donNhapHang);
                } else {
                    progressDialog.dismiss();
//                    Toast.makeText(AddDonNhapHang.this, "Có lỗi xảy ra!", Toast.LENGTH_SHORT).show();
                    showCustomDialogFail("Có lỗi xảy ra. Vui lòng thử lại sau");
                }
            }
        });
    }

    private void updateMaxId(DonNhapHang donNhapHang) {
        DatabaseReference myRef = database.getReference("maxDonNhapHang");

        myRef.setValue(donNhapHang.getMaDNH(), new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                progressDialog.dismiss();
//                Toast.makeText(AddDonNhapHang.this, "Tạo đơn nhập thành công!", Toast.LENGTH_SHORT).show();
                showCustomDialogSucess("Thêm đơn nhập hàng thành công");

                onBackPressed();
            }
        });
    }

    private String createID(Integer maxId) {
        IdGenerator generator = new IdGenerator();
        generator.init("DNH", "", maxId, "%04d");

        return generator.generate();
    }

    private boolean checkForm() {
        boolean isValid = true;

        if (sanpham.getText().toString().trim().equals("")) {
            sanpham.setError("Nội dung bắt buộc");
            sanpham.requestFocus();
            isValid = false;
        }

        if (soluong.getText().toString().trim().equals("")) {
            soluong.setError("Nội dung bắt buộc");
            soluong.requestFocus();
            isValid = false;
        }
        
        return isValid;
    }
}