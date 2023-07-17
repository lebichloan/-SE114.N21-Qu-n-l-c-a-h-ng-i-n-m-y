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
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
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
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

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
                addStaffAuth();
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

    private void addStaffAuth() {
        if (checkForm() == true) {
            progressDialog.show();

            String email = txtEmail.getText().toString().trim();
            String password = "123456789";

            mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        NhanVien nhanVien = new NhanVien();

                        nhanVien.setHoTen(txtHoTen.getText().toString().trim());
                        nhanVien.setEmail(email);
                        if (txtUserType.getText().toString().trim().equals("Admin")) {
                            nhanVien.setLoaiNhanVien("admin");
                        } else {
                            nhanVien.setLoaiNhanVien("staff");
                        }

                        if (nhanVien.getLoaiNhanVien().equals("staff")) {

                        } else {
                            addStaffRealtimeDB(nhanVien, mAuth.getUid());
                        }

                    } else {
                        progressDialog.dismiss();
                        Toast.makeText(AddStaff.this, "Có lỗi xảy ra!", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    private void addStaffRealtimeDB(NhanVien nhanVien, String Uid) {
        DatabaseReference myRef = database.getReference("Users");
        
        myRef.child(Uid).setValue(nhanVien, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                if (error == null) {
                    progressDialog.dismiss();
                    Toast.makeText(AddStaff.this, "Thêm người dùng thành công!", Toast.LENGTH_SHORT).show();
                } else {
                    progressDialog.dismiss();
                    Toast.makeText(AddStaff.this, "Có lỗi xảy ra!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void setStaffId() {
        DatabaseReference myRef = database.getReference("maxNhanVien");

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
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