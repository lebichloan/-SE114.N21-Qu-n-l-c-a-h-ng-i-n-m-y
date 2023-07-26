package com.example.se114n21.ViewModels;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.se114n21.Models.NhanVien;
import com.example.se114n21.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class CheckLogin extends AppCompatActivity {
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    SharedPreferences sharedPreferences;
    private static final String SHARED_PREF_NAME = "mypre";
    private static final String KEY_ID = "id";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_login);
        getSupportActionBar().hide();

        sharedPreferences = getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);

        String keyid = sharedPreferences.getString(KEY_ID, null);

        if (keyid != null) {
            checkUser(keyid);
        } else {
            startActivity(new Intent(CheckLogin.this, Login.class));
            finish();
        }
    }

    private void checkUser(String MaND) {
        DatabaseReference myRef = database.getReference("Users/" + MaND);

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                NhanVien nhanVien = snapshot.getValue(NhanVien.class);

                if (nhanVien.isTrangThai() == true) {
                    if (nhanVien.getLoaiNhanVien().equals("Admin")) {
                        startActivity(new Intent(CheckLogin.this, BottomNavigation.class));
                        finish();
                    } else {
                        startActivity(new Intent(CheckLogin.this, BottomNavigationNhanVien.class));
                        finish();
                    }
                } else {
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.clear();
                    editor.commit();

                    startActivity(new Intent(CheckLogin.this, Login.class));
                    finish();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(CheckLogin.this, "Có lỗi xảy ra!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}