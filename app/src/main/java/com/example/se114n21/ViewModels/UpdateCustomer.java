package com.example.se114n21.ViewModels;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.se114n21.R;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class UpdateCustomer extends AppCompatActivity {
    private ImageButton btnBack;
    private Button btnSave;
    private TextView name, phone, address, email;
    private String MaKH = "";
    private ProgressDialog progressDialog;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_customer);
        getSupportActionBar().hide();

        initUI();

        Intent intent = getIntent();

        setData(intent);
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

    private void setData(Intent intent) {
        MaKH = intent.getStringExtra("MaKH");
        String tenkh = intent.getStringExtra("TenKH");
        String dienthoaikh = intent.getStringExtra("SoDienThoaiKH");
        String emailkh = intent.getStringExtra("EmailKH");
        String diachikh = intent.getStringExtra("DiaChiKH");

        name.setText(tenkh);
        phone.setText(dienthoaikh);
        address.setText(diachikh);
        email.setText(emailkh);
    }

    private void initUI() {
        btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        name = findViewById(R.id.name);
        phone = findViewById(R.id.phone);
        address = findViewById(R.id.address);
        email = findViewById(R.id.email);

        btnSave = findViewById(R.id.btnSave);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateCustomer();
            }
        });
    }

    private void updateCustomer() {
        if (checkForm() == true) {
            progressDialog = ProgressDialog.show(UpdateCustomer.this,"Đang tải", "Vui lòng đợi...",false,false);

            Map<String, Object> map = new HashMap<>();

            map.put("ten",name.getText().toString().trim());
            map.put("diaChi",address.getText().toString().trim());
            map.put("email",email.getText().toString().trim());
            map.put("dienThoai",phone.getText().toString().trim());



            DatabaseReference myRef = database.getReference("listKhachHang/" + MaKH);

            myRef.updateChildren(map, new DatabaseReference.CompletionListener() {
                @Override
                public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                    progressDialog.dismiss();
                    Toast.makeText(UpdateCustomer.this, "Cập nhật thông tin khuyến mãi thành công!", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(UpdateCustomer.this, CustomerDetail.class);
                    setResult(RESULT_OK, intent);
                    finish();
                }
            });
        }
    }

    private boolean checkForm() {
        boolean isValid = true;

        if (name.getText().toString().trim().equals("")) {
            name.setError("Nội dung bắt buộc");
            name.requestFocus();
            isValid = false;
        }

        if (phone.getText().toString().trim().equals("")) {
            phone.setError("Nội dung bắt buộc");
            phone.requestFocus();
            isValid = false;
        }

        return isValid;
    }
}