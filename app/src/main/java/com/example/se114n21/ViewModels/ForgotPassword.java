package com.example.se114n21.ViewModels;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
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

import com.example.se114n21.Models.JavaMailAPI;
import com.example.se114n21.Models.NhanVien;
import com.example.se114n21.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ForgotPassword extends AppCompatActivity {
    ImageButton butBack;
    EditText txtForgotPasswordEmail;
    Button butNext;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        getSupportActionBar().hide();


        butBack = findViewById(R.id.butBack);
        txtForgotPasswordEmail = findViewById(R.id.txtForgotPasswordEmail);
        butNext = findViewById(R.id.butNext);

        butBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        butNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkEmail();
            }
        });
    }

    boolean isChange = false;
    private void checkEmail() {
        progressDialog = ProgressDialog.show(this,"Đang xác thực email", "Vui lòng đợi...",false,false);

        DatabaseReference myRef = database.getReference("Users");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (isChange == false) {
                    String email = txtForgotPasswordEmail.getText().toString().trim();

                    Integer dem = 0;
                    boolean check = false;

                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        NhanVien nhanVien = dataSnapshot.getValue(NhanVien.class);

                        dem++;

                        if (nhanVien.getEmail().equals(email)) {
                            progressDialog.dismiss();
                            check = true;
                            isChange = true;
                            setPassword(nhanVien);

                            break;
                        }
                    }

                    if (dem == snapshot.getChildrenCount() && check == false) {
                        progressDialog.dismiss();
                        Toast.makeText(ForgotPassword.this, "Email không tồn tại trong hệ thống!", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                progressDialog.dismiss();
                Toast.makeText(ForgotPassword.this, "Có lỗi xảy ra!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setPassword(NhanVien nhanVien) {
        double randomDouble = Math.random();
        randomDouble = randomDouble * 1000000 + 1;
        int randomInt = (int) randomDouble;

        DatabaseReference myRef = database.getReference("Users/" + nhanVien.getMaND() + "/password");
        myRef.setValue(String.valueOf(randomInt), new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                JavaMailAPI javaMailAPI = new JavaMailAPI(ForgotPassword.this,
                        nhanVien.getEmail(),
                        "Cấp lại mật khẩu -  Cửa Hàng Điện Máy",
                        "Mật khẩu mới của bạn là: " + String.valueOf(randomInt));
                javaMailAPI.execute();

                startActivity(new Intent(ForgotPassword.this, SendEmailSucess.class));
                finish();
            }
        });
    }
}