package com.example.se114n21.ViewModels;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
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

import com.example.se114n21.Models.NhanVien;
import com.example.se114n21.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Login extends AppCompatActivity {
    EditText txtEmail, txtPassword;
    ImageButton eyeButton;
    Button butLogin;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    TextView textViewForgotPassword;
    ProgressDialog progressDialog;

//    SHARED PREF
    SharedPreferences sharedPreferences;
    private static final String SHARED_PREF_NAME = "mypre";
    private static final String KEY_ID = "id";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();

            sharedPreferences = getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);

        initUI();

        butLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginToApp();
            }
        });

        textViewForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Login.this, ForgotPassword.class));
                finish();
            }
        });

        txtPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() > 0) {
                    eyeButton.setVisibility(View.VISIBLE);
                } else {
                    eyeButton.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        eyeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (txtPassword.getInputType() == 129) {
                    txtPassword.setInputType(InputType.TYPE_CLASS_TEXT);
                    eyeButton.setImageDrawable(getDrawable(R.drawable.eye_blind));
                } else {
                    txtPassword.setInputType(129);
                    eyeButton.setImageDrawable(getDrawable(R.drawable.eye));
                }
            }
        });

    }

    private void LoginToApp() {
        if (checkForm() == true) {
            progressDialog = ProgressDialog.show(this,"Đang đăng nhập", "Vui lòng đợi...",false,false);

            DatabaseReference myRef = database.getReference("Users");
            myRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                   String email = txtEmail.getText().toString().trim();
                   String password = txtPassword.getText().toString().trim();
                   
                   Integer dem = 0;
                   boolean check = false;

                   for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        NhanVien nhanVien = dataSnapshot.getValue(NhanVien.class);

                        dem++;
                        
                        if (nhanVien.getEmail().equals(email) && nhanVien.getPassword().equals(password)) {
                            progressDialog.dismiss();
                            check = true;
                            if (nhanVien.isTrangThai() == false) {
                                Toast.makeText(Login.this, "Tài khoản đã bị vô hiệu hóa!", Toast.LENGTH_SHORT).show();
                            } else {

                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putString(KEY_ID, nhanVien.getMaND());
                                editor.apply();

                                if (nhanVien.getLoaiNhanVien().equals("Admin")) {
                                    startActivity(new Intent(Login.this, BottomNavigation.class));
                                    finish();
                                } else {
                                    startActivity(new Intent(Login.this, BottomNavigationNhanVien.class));
                                    finish();
                                }
                            }
                            break;
                        }
                   }
                   
                   if (dem == snapshot.getChildrenCount() && check == false) {
                       progressDialog.dismiss();
                       Toast.makeText(Login.this, "Email hoặc mật khẩu không đúng!", Toast.LENGTH_SHORT).show();
                   }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    progressDialog.dismiss();
                    Toast.makeText(Login.this, "Có lỗi xảy ra!", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private boolean checkForm() {
        boolean isValid = true;

        if (txtEmail.getText().toString().trim().equals("")) {
            txtEmail.setError("Nội dung bắt buộc");
            txtEmail.requestFocus();
            isValid = false;
        }

        if (txtPassword.getText().toString().trim().equals("")) {
            txtPassword.setError("Nội dung bắt buộc");
            txtPassword.requestFocus();
            isValid = false;
        }

        return isValid;
    }

    private void initUI() {
        txtEmail = findViewById(R.id.txtEmail);
        txtPassword = findViewById(R.id.txtPassword);
        eyeButton = findViewById(R.id.eyeButton);
        butLogin = findViewById(R.id.butLogin);
        textViewForgotPassword = findViewById((R.id.textViewForgotPassword));
    }
}