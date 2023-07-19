package com.example.se114n21.ViewModels;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.se114n21.Models.Account;
import com.example.se114n21.Models.NhanVien;
import com.example.se114n21.R;
import com.example.se114n21.utils.GlideUtils;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class AddStaff extends AppCompatActivity {

    ImageButton butBack;
    EditText txtHoTen, txtEmail;
    Button butAdd;
    NhanVien nhanVien;
    FirebaseAuth auth;
    FirebaseDatabase database;
    DatabaseReference reference;
    Spinner spn_type_staff;
    private String[] arr = new String[2];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_staff);

        initUI();
        getSupportActionBar().hide();

        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        arr[0]= getResources().getString(R.string.staff);
        arr[1]= getResources().getString(R.string.admin);

        initSpinner();
//        setData(nhanVien);

        butBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        butAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addStaff();
            }
        });
    }

    private void addStaff() {
        if (isNameEmpty()) {
            Toast.makeText(AddStaff.this, "Vui long nhap vao ho ten", Toast.LENGTH_SHORT).show();
            txtHoTen.setError("Email can not be empty");
            txtHoTen.requestFocus();
        }
        if (isEmailEmpty()) {
            Toast.makeText(AddStaff.this, "Vui long nhap vao email", Toast.LENGTH_SHORT).show();
            txtEmail.setError("Password can not be empty");
            txtEmail.requestFocus();
        } else {
            String email = txtEmail.getText().toString().trim();
            String password = "123456789";
            auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        String userId = auth.getUid();
                        String hoTen = txtHoTen.getText().toString().trim();
                        String email = txtEmail.getText().toString().trim();
                        String type = "admin";
                        if (spn_type_staff.getSelectedItem().toString().equals(getResources().getString(R.string.admin))) {
                            type = "admin";
                        }  else {
                            type = "staff";
                        }
                        nhanVien = new NhanVien(userId, hoTen, email, type);
                        auth.sendPasswordResetEmail(email)
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            Toast.makeText(AddStaff.this, "Da gui email doi mat khau toi email vua dang ky", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });

                        reference = database.getReference("NhanVien");
                        reference.child(userId).setValue(
                                nhanVien
                        );
                        Toast.makeText(AddStaff.this, "Them nguoi dung thanh cong", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(AddStaff.this, "Add staff failed" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

    }

    private NhanVien getNhanVien(){
        NhanVien nhanVien = new NhanVien();
        nhanVien.setHoTen(txtHoTen.getText().toString());
        nhanVien.setEmail(txtEmail.getText().toString());
        String type = "admin";
        if (spn_type_staff.getSelectedItem().toString().equals(getResources().getString(R.string.admin))) {
            type = "admin";
        }  else {
            type = "staff";
        }
        nhanVien.setLoaiNhanVien(type);
        return nhanVien;
    }
    private boolean isNameEmpty(){
        return txtHoTen.getText().toString().isEmpty();
    }

    private boolean isEmailEmpty(){
        return txtEmail.getText().toString().isEmpty();
    }

    private void setData(NhanVien nhanVien) {
        if (nhanVien != null) {
            txtHoTen.setText(nhanVien.getHoTen());
            txtEmail.setText(nhanVien.getEmail());
            txtEmail.setEnabled(false);
        }
    }

    private void initSpinner() {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(AddStaff.this, android.R.layout.simple_spinner_dropdown_item, arr);
        spn_type_staff.setAdapter(adapter);
    }

    private void initUI() {
        butBack = findViewById(R.id.butBack);
        txtHoTen = findViewById(R.id.txtHoTen);
        txtEmail = findViewById(R.id.txtEmail);
        butAdd = findViewById(R.id.butAdd);
        spn_type_staff = findViewById(R.id.spn_type_staff);
    }
}