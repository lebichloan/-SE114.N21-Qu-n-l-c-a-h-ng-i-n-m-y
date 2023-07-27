package com.example.se114n21.ViewModels;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.se114n21.Models.NhanVien;
import com.example.se114n21.R;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import de.hdodenhof.circleimageview.CircleImageView;

public class UpdateStaff extends AppCompatActivity {
    private ImageButton btnBack, btnSave;
    private ProgressDialog progressDialog;
    private NhanVien nhanVien = new NhanVien();
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private CircleImageView avt;
    private TextView role, id, name, phone, email;

    //    AUTO COMPLETE
    private String[] items = {"Đang làm việc","Đã nghỉ việc"};
    private AutoCompleteTextView autoCompleteTextView;
    private ArrayAdapter<String> adapterItems;
    private TextInputLayout layoutState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_staff);
        getSupportActionBar().hide();

        initUI();

        Intent i = getIntent();
        String MaND = i.getStringExtra("MaND");

        getData(MaND);
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

    private void getData(String MaND) {
        progressDialog.show();
        DatabaseReference myRef = database.getReference("Users/" + MaND);

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                nhanVien = snapshot.getValue(NhanVien.class);
                setData(nhanVien);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(UpdateStaff.this, "Có lỗi xảy ra!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setData(NhanVien nhanVien) {
        Glide.with(this)
                .load(nhanVien.getLinkAvt()) // image url
                .placeholder(R.drawable.blank_img) // any placeholder to load at start
                .error(R.drawable.blank_img)  // any image in case of error
                .into(avt);

        if (nhanVien.getMaNV() != null) {
            role.setText("Nhân viên");
            id.setText(nhanVien.getMaNV());
        } else {
            role.setText("Admin");
            id.setText("--");
        }

        name.setText(nhanVien.getHoTen());
        phone.setText(nhanVien.getSDT());
        email.setText(nhanVien.getEmail());

        if (nhanVien.isTrangThai() == false) {
            autoCompleteTextView.setText(adapterItems.getItem(1), false);
        } else {
            autoCompleteTextView.setText(adapterItems.getItem(0), false);
        }

        progressDialog.dismiss();
    }
    private void initUI() {
//        AVT
        avt = findViewById(R.id.avtStaff_UpdateStaff);

//        TEXTVIEW
        role = findViewById(R.id.tvRole_UpdateStaff);
        id = findViewById(R.id.tvId_UpdateStaff);
        name = findViewById(R.id.tvName_UpdateStaff);
        phone = findViewById(R.id.tvPhone_UpdateStaff);
        email = findViewById(R.id.tvEmail_UpdateStaff);

//        PROGRESS DIALOG
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait!");
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);

//        BACK BUTTON
        btnBack = findViewById(R.id.btnBack_UpdateStaff);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

//        SAVE BUTTON
        btnSave = findViewById(R.id.btnSave_UpdateStaff);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateStaff();
            }
        });

//        AUTO COMPLETE TEXT VIEW
        autoCompleteTextView = findViewById(R.id.tvState_UpdateStaff);
        adapterItems = new ArrayAdapter<String>(this, R.layout.role_dropdown_item, items);
        autoCompleteTextView.setAdapter(adapterItems);

        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String item = adapterView.getItemAtPosition(i).toString();
            }
        });
    }

    private void updateStaff() {
        progressDialog.show();
        
        boolean TrangThai = false;
        if (autoCompleteTextView.getText().toString().trim().equals("Đang làm việc")) {
            TrangThai = true;
        }

        DatabaseReference myRef = database.getReference("Users/" + nhanVien.getMaND() + "/trangThai");
        
        myRef.setValue(TrangThai, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                if (error == null) {
                    Toast.makeText(UpdateStaff.this, "Cập nhật người dùng thành công!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(UpdateStaff.this, "Có lỗi xảy ra!", Toast.LENGTH_SHORT).show();
                }

                Intent intent = new Intent(UpdateStaff.this, DetailStaff.class);
                setResult(RESULT_OK, intent);
                onBackPressed();
            }
        });
    }
}