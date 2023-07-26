package com.example.se114n21.ViewModels;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.se114n21.Models.HoaDon;
import com.example.se114n21.Models.IdGenerator;
import com.example.se114n21.Models.KhuyenMai;
import com.example.se114n21.Models.NhanVien;
import com.example.se114n21.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class AddSale extends AppCompatActivity {
    private ImageButton btnBack;
    private EditText ten, mota, batdau, ketthuc, dontoithieu, phantram, giamtoida;
    private ProgressDialog progressDialog;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
    private Calendar cal1 = Calendar.getInstance();
    private Calendar cal2 = Calendar.getInstance();
    private Button btnAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_sale);
        getSupportActionBar().hide();

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
        btnBack = findViewById(R.id.btnBack_ThemKhuyenMai);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        ten = findViewById(R.id.txtTenCT);
        mota = findViewById(R.id.txtMoTa);
        batdau = findViewById(R.id.txtNgayBD);
        ketthuc = findViewById(R.id.txtNgayKT);
        dontoithieu = findViewById(R.id.txtDonToiThieu);
        phantram = findViewById(R.id.txtKhuyenMai);
        giamtoida = findViewById(R.id.txtGiamToiDa);

        batdau.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(AddSale.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        cal1.set(i,i1,i2);
                        batdau.setText(simpleDateFormat.format(cal1.getTime()));
                    }
                }, cal1.get(Calendar.YEAR), cal1.get(Calendar.MONTH), cal1.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.show();
            }
        });

        ketthuc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(AddSale.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        cal2.set(i,i1,i2);
                        ketthuc.setText(simpleDateFormat.format(cal2.getTime()));
                    }
                }, cal2.get(Calendar.YEAR), cal2.get(Calendar.MONTH), cal2.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.show();
            }
        });

        btnAdd = findViewById(R.id.butAddSale);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addKM();
            }
        });
    }

    private void addKM() {
        if (checkForm() == true) {
            if (batdau.getText().toString().trim().compareTo(ketthuc.getText().toString().trim()) <= 0) {
                progressDialog = ProgressDialog.show(AddSale.this,"Đang tải", "Vui lòng đợi...",false,false);
                KhuyenMai khuyenMai = new KhuyenMai();
                setId(khuyenMai);
            } else {
                Toast.makeText(this, "Thời gian bắt đầu không thể trễ hơn thời gian kết thúc chương trình!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    boolean save = false;
    private void setId(KhuyenMai khuyenMai) {
        DatabaseReference myRef = database.getReference("maxKhuyenMai");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (save == false) {
                    String MaKM = snapshot.getValue(String.class);
                    Integer maxId = Integer.parseInt(MaKM.substring(3));

                    khuyenMai.setMaKM(createID(maxId));
                    
                    prepareKM(khuyenMai);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(AddSale.this, "Có lỗi xảy ra!", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });
    }

    private String createID(Integer maxId) {
        IdGenerator generator = new IdGenerator();
        generator.init("KM", "", maxId, "%04d");

        return generator.generate();
    }

    private void prepareKM(KhuyenMai khuyenMai) {
        khuyenMai.setTenKM(ten.getText().toString().trim());
        khuyenMai.setMoTa(mota.getText().toString().trim());
        khuyenMai.setNgayBD(batdau.getText().toString().trim());
        khuyenMai.setNgayKT(ketthuc.getText().toString().trim());

        khuyenMai.setKhuyenMai(Double.parseDouble(phantram.getText().toString().trim()));
        khuyenMai.setDonToiThieu(Integer.parseInt(dontoithieu.getText().toString().trim()));
        khuyenMai.setGiamToiDa(Integer.parseInt(giamtoida.getText().toString().trim()));

        addToBD(khuyenMai);
    }
    
    private void addToBD(KhuyenMai khuyenMai) {
        DatabaseReference myRef = database.getReference("KhuyenMai");
        myRef.child(khuyenMai.getMaKM()).setValue(khuyenMai, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                if (error == null) {
                    progressDialog.dismiss();
                    save= true;
                    updateId(khuyenMai);
                    Toast.makeText(AddSale.this, "Thêm khuyến mãi thành công!", Toast.LENGTH_SHORT).show();
                    onBackPressed();
                } else {
                    Toast.makeText(AddSale.this, "Có lỗi xảy ra!", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }
            }
        });
    }

    private void updateId(KhuyenMai khuyenMai) {
        DatabaseReference myRef = database.getReference("maxKhuyenMai");

        myRef.setValue(khuyenMai.getMaKM());
    }

    private boolean checkForm() {
        boolean isValid = true;

        if (ten.getText().toString().trim().equals("")) {
            ten.setError("Nội dung bắt buộc");
            ten.requestFocus();
            isValid = false;
        }

        if (batdau.getText().toString().trim().equals("")) {
            batdau.setError("Nội dung bắt buộc");
            batdau.requestFocus();
            isValid = false;
        }

        if (ketthuc.getText().toString().trim().equals("")) {
            ketthuc.setError("Nội dung bắt buộc");
            ketthuc.requestFocus();
            isValid = false;
        }

        if (dontoithieu.getText().toString().trim().equals("")) {
            dontoithieu.setError("Nội dung bắt buộc");
            dontoithieu.requestFocus();
            isValid = false;
        }

        if (phantram.getText().toString().trim().equals("")) {
            phantram.setError("Nội dung bắt buộc");
            phantram.requestFocus();
            isValid = false;
        }

        if (giamtoida.getText().toString().trim().equals("")) {
            giamtoida.setError("Nội dung bắt buộc");
            giamtoida.requestFocus();
            isValid = false;
        }

        return isValid;
    }
}