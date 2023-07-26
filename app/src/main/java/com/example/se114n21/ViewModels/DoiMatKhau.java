package com.example.se114n21.ViewModels;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.se114n21.Models.NhanVien;
import com.example.se114n21.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DoiMatKhau extends AppCompatActivity {
    ImageButton btnBack;
    Button btnConfirm;
    EditText currrentPass, newPass, confirmPass;

    SharedPreferences sharedPreferences;
    private static final String SHARED_PREF_NAME = "mypre";
    private static final String KEY_ID = "id";
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doi_mat_khau);
        getSupportActionBar().hide();

        sharedPreferences = getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);

        initUI();
    }


    //    CLEAR FOCUS ON EDIT TEXT
    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (v instanceof EditText) {
                Rect outRect = new Rect();
                v.getGlobalVisibleRect(outRect);
                if (!outRect.contains((int)event.getRawX(), (int)event.getRawY())) {
                    v.clearFocus();
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
        }
        return super.dispatchTouchEvent(event);
    }


    private void initUI() {
        currrentPass = findViewById(R.id.txt_matkhauhientai);
        newPass = findViewById(R.id.txt_matkhaumoi);
        confirmPass = findViewById(R.id.txt_xacnhanmatkhaumoi);

        btnBack = findViewById(R.id.btnBack_DoiMatKhau);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        btnConfirm = findViewById(R.id.btnConfirmNewPass);
        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changePass();
            }
        });
    }

    private void changePass() {
        if (checkForm() == true) {
            progressDialog = ProgressDialog.show(DoiMatKhau.this,"Đang xác thực", "Vui lòng đợi...",false,false);

            String keyid = sharedPreferences.getString(KEY_ID, null);

            checkCurrentPass(keyid);
        }
    }

    private void checkCurrentPass(String MaND) {
        DatabaseReference myRef = database.getReference("Users/" + MaND);

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                NhanVien nhanVien = snapshot.getValue(NhanVien.class);
                if (nhanVien.getPassword().equals(currrentPass.getText().toString().trim())) {
                    if (newPass.getText().toString().trim().equals(confirmPass.getText().toString().trim())) {
                        changePassOnDB(nhanVien);
                    } else {
                        progressDialog.dismiss();
                        Toast.makeText(DoiMatKhau.this, "Xác nhận mật khẩu mới không trùng khớp!", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    progressDialog.dismiss();
                    Toast.makeText(DoiMatKhau.this, "Mật khẩu hiện tại không chính xác!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                progressDialog.dismiss();
                Toast.makeText(DoiMatKhau.this, "Có lỗi xảy ra!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void changePassOnDB(NhanVien nhanVien) {
        DatabaseReference myRef = database.getReference("Users/" + nhanVien.getMaND() + "/password");

        myRef.setValue(newPass.getText().toString().trim(), new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                progressDialog.dismiss();

                if (error == null) {
                    Toast.makeText(DoiMatKhau.this, "Đổi mật khẩu thành công!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(DoiMatKhau.this, "Có lỗi xảy ra!", Toast.LENGTH_SHORT).show();
                }

                onBackPressed();
                finish();
            }
        });
    }

    private boolean checkForm() {
        boolean isValid = true;

        if (currrentPass.getText().toString().trim().equals("")) {
            currrentPass.setError("Nội dung bắt buộc");
            currrentPass.requestFocus();
            isValid = false;
        }

        if (newPass.getText().toString().trim().equals("")) {
            newPass.setError("Nội dung bắt buộc");
            newPass.requestFocus();
            isValid = false;
        }

        if (confirmPass.getText().toString().trim().equals("")) {
            confirmPass.setError("Nội dung bắt buộc");
            confirmPass.requestFocus();
            isValid = false;
        }

        return isValid;
    }
}