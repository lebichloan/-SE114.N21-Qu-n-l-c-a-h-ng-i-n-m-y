package com.example.se114n21.ViewModels;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.se114n21.Models.NhanVien;
import com.example.se114n21.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class EditStaff extends AppCompatActivity {
    ImageButton butBack;
    EditText txtHoTen, txtEmail;
    TextView txtUserType;
    Button butEdit;
    NhanVien nhanVien;
    FirebaseAuth auth;
    FirebaseDatabase database;
    Spinner spn_type_staff;
    private String[] arr = new String[2];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_staff);

        initUI();
        getSupportActionBar().hide();

        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        arr[0]= getResources().getString(R.string.staff);
        arr[1]= getResources().getString(R.string.admin);

        initSpinner();
        //
        String uid ="Lay tham so truyen vao tu man hinh truoc";
        //
        setDataStaff(uid);

        butBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        butEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editStaff(uid);
            }
        });

        txtUserType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideTxtAndShowSpn();
            }
        });
    }

    private void setDataStaff(String uid) {
        DatabaseReference reference = database.getReference("NhanVien");
        reference.child(uid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    hideSpnAndShowTxt();
                    String hoten = snapshot.child("hoTen").getValue(String.class);
                    String email = snapshot.child("email").getValue(String.class);
                    String loaiNhanVien = snapshot.child("loaiNhanVien").getValue(String.class);
                    txtHoTen.setText(hoten);
                    txtEmail.setText(email);
                    txtEmail.setEnabled(false);
                    txtUserType.setText(loaiNhanVien);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void hideTxtAndShowSpn() {
        txtUserType.setVisibility(View.GONE);
        spn_type_staff.setVisibility(View.VISIBLE);
    }

    private void hideSpnAndShowTxt() {
        txtUserType.setVisibility(View.VISIBLE);
        spn_type_staff.setVisibility(View.GONE);
    }

    private NhanVien getDataStaff(String uid) {
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

    private void editStaff(String uid){
        nhanVien = getDataStaff(uid);
        database.getReference("NhanVien").child(nhanVien.getMaNV())
                .setValue(nhanVien).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(EditStaff.this, "Cap nhat thanh cong", Toast.LENGTH_SHORT).show();
//                            showCustomDialogSucess("Cập nhật thông tin thành công");
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(EditStaff.this, "Cap nhat khong thanh cong", Toast.LENGTH_SHORT).show();
//                        showCustomDialogFail("Có lỗi xảy ra. Vui lòng thử lại");
                    }
                });
    }




    private void initSpinner() {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(EditStaff.this, android.R.layout.simple_spinner_dropdown_item, arr);
        spn_type_staff.setAdapter(adapter);
    }

    private void initUI() {
        butBack = findViewById(R.id.butBack);
        txtHoTen = findViewById(R.id.txtHoTen);
        txtEmail = findViewById(R.id.txtEmail);
        txtUserType = findViewById(R.id.txtUserType);
        butEdit = findViewById(R.id.butEdit);
        spn_type_staff = findViewById(R.id.spn_type_staff);
    }
}