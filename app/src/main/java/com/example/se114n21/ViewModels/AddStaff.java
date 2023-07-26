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

import com.example.se114n21.Models.IdGenerator;
import com.example.se114n21.Models.JavaMailAPI;
import com.example.se114n21.Models.NhanVien;
import com.example.se114n21.R;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AddStaff extends AppCompatActivity {
    ImageButton butBack;
    EditText txtHoTen, txtEmail, txtUserType;
    Button butAdd;

//    FIREBASE
    FirebaseAuth mAuth;
    FirebaseDatabase database = FirebaseDatabase.getInstance();

//    AUTO COMPLETE

    private String[] items = {"Admin","Nhân viên"};
    private AutoCompleteTextView autoCompleteTextView;
    private ArrayAdapter<String> adapterItems;

//    LAYOUT
    private TextInputLayout layoutHoten, layoutEmail, layoutUserType;

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_staff);

        mAuth = FirebaseAuth.getInstance();

        initUI();
        getSupportActionBar().hide();

        butBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        butAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createUser();
            }
        });
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

    boolean save = false;
    private void createUser() {
        if (checkForm() == true) {
            progressDialog.show();

            DatabaseReference myRef = database.getReference("maxNguoiDung");
            myRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (save == false) {
                        String maND = snapshot.getValue(String.class);
                        Integer maxId = Integer.parseInt(maND.substring(3));

                        double randomDouble = Math.random();
                        randomDouble = randomDouble * 1000000 + 1;
                        int randomInt = (int) randomDouble;

                        NhanVien nhanVien = new NhanVien();

                        nhanVien.setMaND(createIDNguoiDung(maxId));
                        nhanVien.setHoTen(txtHoTen.getText().toString().trim());
                        nhanVien.setEmail(txtEmail.getText().toString().trim());
                        nhanVien.setPassword(String.valueOf(randomInt));
                        nhanVien.setTrangThai(true);


                        if (txtUserType.getText().toString().equals("Admin")) {
                            nhanVien.setLoaiNhanVien("Admin");
                            addUser(nhanVien);
                        } else {
                            nhanVien.setLoaiNhanVien("Nhân viên");
                            setMaNV(nhanVien);
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
//                    Toast.makeText(AddStaff.this, "Có lỗi xảy ra!", Toast.LENGTH_SHORT).show();
                    showCustomDialogFail("Có lỗi xảy ra. Vui lòng thử lại");
                    progressDialog.dismiss();
                }
            });
        }
    }

    boolean save2 = false;
    private void setMaNV(NhanVien nhanVien) {
        DatabaseReference myRef = database.getReference("maxNhanVien");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (save2 == false) {
                    String maNV = snapshot.getValue(String.class);
                    Integer maxId = Integer.parseInt(maNV.substring(3));

                    nhanVien.setMaNV(createIDNhanVien(maxId));
                    addUser(nhanVien);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
//                Toast.makeText(AddStaff.this, "Có lỗi xảy ra!", Toast.LENGTH_SHORT).show();
                showCustomDialogFail("Có lỗi xảy ra. Vui lòng thử lại");
                progressDialog.dismiss();
            }
        });
    }

    private void addUser(NhanVien nhanVien) {
        DatabaseReference myRef = database.getReference("Users");
        myRef.child(nhanVien.getMaND()).setValue(nhanVien, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                if (error == null) {
                    sendPassword(nhanVien);
                } else {
//                    Toast.makeText(AddStaff.this, "Có lỗi xảy ra!", Toast.LENGTH_SHORT).show();
                    showCustomDialogFail("Có lỗi xảy ra. Vui lòng thử lại");
                    progressDialog.dismiss();
                }
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

    private void sendPassword(NhanVien nhanVien) {
        JavaMailAPI javaMailAPI = new JavaMailAPI(this,
                nhanVien.getEmail(),
                "Cấp lại mật khẩu -  Cửa Hàng Điện Máy",
                "Mật khẩu mới của bạn là: " + nhanVien.getPassword());
        javaMailAPI.execute();

        save = true;
        updateIDNguoiDung(nhanVien);

        if (nhanVien.getLoaiNhanVien().equals("Nhân viên")) {
            save2 = true;
            updateIDNhanVien(nhanVien);
        }

        progressDialog.dismiss();
//        Toast.makeText(AddStaff.this, "Thêm người dùng thành công!", Toast.LENGTH_SHORT).show();
        showCustomDialogSucess("Thêm nguời dùng thành công");
    }

    private String createIDNhanVien(Integer maxId) {
        IdGenerator generator = new IdGenerator();
        generator.init("NV", "", maxId, "%04d");

        return generator.generate();
    }

    private String createIDNguoiDung(Integer maxId) {
        IdGenerator generator = new IdGenerator();
        generator.init("ND", "", maxId, "%04d");

        return generator.generate();
    }

    private void updateIDNguoiDung(NhanVien nhanVien) {
        DatabaseReference myRef = database.getReference("maxNguoiDung");

        myRef.setValue(nhanVien.getMaND());

        Intent intent = new Intent(AddStaff.this, ListStaff.class);
        setResult(RESULT_OK, intent);
        finish();
    }

    private void updateIDNhanVien(NhanVien nhanVien) {
        DatabaseReference myRef = database.getReference("maxNhanVien");

        myRef.setValue(nhanVien.getMaNV());
    }


    private boolean checkForm() {
        boolean isValid = true;

        if (txtHoTen.getText().toString().trim().equals("")) {
            txtHoTen.setError("Nội dung bắt buộc");
            txtHoTen.requestFocus();
            isValid = false;
        }

        if (txtEmail.getText().toString().trim().equals("")) {
            txtEmail.setError("Nội dung bắt buộc");
            txtEmail.requestFocus();
            isValid = false;
        }

        if (txtUserType.getText().toString().trim().equals("")) {
            txtUserType.setError("Nội dung bắt buộc");
            txtUserType.requestFocus();
            isValid = false;
        }

       return isValid;
    }

    private void initUI() {
//        PROGRESS DIALOG
        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setMessage("Please wait!");
        
//        AUTO COMPLETE TEXT VIEW
        autoCompleteTextView = findViewById(R.id.user_type);
        adapterItems = new ArrayAdapter<String>(this, R.layout.role_dropdown_item, items);
        autoCompleteTextView.setAdapter(adapterItems);

        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String item = adapterView.getItemAtPosition(i).toString();
                txtUserType.setError(null);
            }
        });

        butBack = findViewById(R.id.butBack);
        butAdd = findViewById(R.id.butAdd);

        txtEmail = findViewById(R.id.txtEmail);
        txtHoTen = findViewById(R.id.txtHoTen);
        txtUserType = findViewById(R.id.user_type);

        layoutEmail = findViewById(R.id.layout_txtEmail);
        layoutHoten = findViewById(R.id.layout_txtHoTen);
        layoutUserType = findViewById(R.id.layout_user_type);
    }
}