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
import android.widget.TextView;
import android.widget.Toast;

import com.example.se114n21.Models.KhuyenMai;
import com.example.se114n21.Models.NhanVien;
import com.example.se114n21.R;
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

public class EditSale extends AppCompatActivity {
    private ImageButton btnBack;
    private EditText ten, mota, batdau, ketthuc, dontoithieu, phantram, giamtoida;
    private ProgressDialog progressDialog;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private KhuyenMai khuyenMai = new KhuyenMai();
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
    private Calendar cal1 = Calendar.getInstance();
    private Calendar cal2 = Calendar.getInstance();
    private Button btnUpdate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_sale);
        getSupportActionBar().hide();

        initUI();

        Intent i = getIntent();
        String MaKM = i.getStringExtra("MaKM");

        getData(MaKM);
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

    private void getData(String MaKM) {
        progressDialog = ProgressDialog.show(EditSale.this,"Đang tải", "Vui lòng đợi...",false,false);
        DatabaseReference myRef = database.getReference("KhuyenMai/" + MaKM);

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                khuyenMai = snapshot.getValue(KhuyenMai.class);
                setData(khuyenMai);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                progressDialog.dismiss();
//                Toast.makeText(EditSale.this, "Có lỗi xảy ra!", Toast.LENGTH_SHORT).show();
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

    private void setData(KhuyenMai khuyenMai) {
        ten.setText(khuyenMai.getTenKM());
        mota.setText(khuyenMai.getMoTa());
        batdau.setText(khuyenMai.getNgayBD());
        ketthuc.setText(khuyenMai.getNgayKT());
        dontoithieu.setText(String.valueOf(khuyenMai.getDonToiThieu()));
        phantram.setText(String.valueOf((int) khuyenMai.getKhuyenMai()));
        giamtoida.setText(String.valueOf(khuyenMai.getGiamToiDa()));

        try {
            cal1.setTime(simpleDateFormat.parse(khuyenMai.getNgayBD()));
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        try {
            cal2.setTime(simpleDateFormat.parse(khuyenMai.getNgayKT()));
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        progressDialog.dismiss();
    }


    private void initUI() {
        btnBack = findViewById(R.id.btnBack_CapNhatKhuyenMai);
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
                DatePickerDialog datePickerDialog = new DatePickerDialog(EditSale.this, new DatePickerDialog.OnDateSetListener() {
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
                DatePickerDialog datePickerDialog = new DatePickerDialog(EditSale.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        cal2.set(i,i1,i2);
                        ketthuc.setText(simpleDateFormat.format(cal2.getTime()));
                    }
                }, cal2.get(Calendar.YEAR), cal2.get(Calendar.MONTH), cal2.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.show();
            }
        });

        btnUpdate = findViewById(R.id.butSaveSale);
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateKM();
            }
        });
    }

    private void updateKM() {
        if (checkForm() == true) {
            if (batdau.getText().toString().trim().compareTo(ketthuc.getText().toString().trim()) <= 0) {
                progressDialog = ProgressDialog.show(EditSale.this,"Đang tải", "Vui lòng đợi...",false,false);

                Map<String, Object> map = new HashMap<>();

                map.put("tenKM",ten.getText().toString().trim());
                map.put("moTa",mota.getText().toString().trim());
                map.put("ngayBD",batdau.getText().toString().trim());
                map.put("ngayKT",ketthuc.getText().toString().trim());
                map.put("donToiThieu", Integer.parseInt(dontoithieu.getText().toString().trim()));
                map.put("giamToiDa", Integer.parseInt(giamtoida.getText().toString().trim()));
                map.put("khuyenMai", Double.parseDouble(phantram.getText().toString().trim()));


                DatabaseReference myRef = database.getReference("KhuyenMai/" + khuyenMai.getMaKM());

                myRef.updateChildren(map, new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                        progressDialog.dismiss();
//                        Toast.makeText(EditSale.this, "Cập nhật thông tin khuyến mãi thành công!", Toast.LENGTH_SHORT).show();

                        showCustomDialogSucess("Cập nhật thông tin khuyến mãi thành công");
                        onBackPressed();
                    }
                });
            } else {
//                Toast.makeText(this, "Thời gian bắt đầu không thể trễ hơn thời gian kết thúc chương trình!", Toast.LENGTH_SHORT).show()
                showCustomDialogFail("Thời gian bắt đầu không thể lớn hơn thời gian kết thúc chương trình");
            }
        }
    }

    private boolean checkForm() {
        boolean isValid = true;

        if (ten.getText().toString().trim().equals("")) {
            ten.setError("Nội dung bắt buộc");
            ten.requestFocus();
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