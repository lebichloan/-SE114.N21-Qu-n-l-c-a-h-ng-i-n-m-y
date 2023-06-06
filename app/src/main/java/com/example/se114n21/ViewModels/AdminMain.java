package com.example.se114n21.ViewModels;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.se114n21.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class AdminMain extends AppCompatActivity {

    ImageView avata;
    Uri uri;
    TextView txtProfile, txtEmail, txtPassword;
    ImageButton butProfileNext, butEmailNext, butPasswordNext;
    ImageButton butBack;
    Button butLogout;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_main);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationAdminView);
        bottomNavigationView.setSelectedItemId(R.id.bottom_account);

        bottomNavigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()){
                case R.id.bottom_admin_khachHang:
                    startActivity(new Intent(getApplicationContext(), admin_QLKhachHang.class));
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                    finish();
                    return true;
                case R.id.bottom_admin_sanPham:
                    startActivity(new Intent(getApplicationContext(), admin_QLSanPham.class));
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                    finish();
                    return true;
                case R.id.bottom_admin_hoaDon:
                    startActivity(new Intent(getApplicationContext(), admin_QLHoaDon.class));
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                    finish();
                    return true;
                case R.id.bottom_admin_nhanVien:
                    startActivity(new Intent(getApplicationContext(), admin_QLNhanVien.class));
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                    finish();
                    return true;
                case R.id.bottom_admin_account:
//                    startActivity(new Intent(getApplicationContext(), Account.class));
//                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
//                    finish();
                    return true;
            }
            return false;
        });

        auth = FirebaseAuth.getInstance();
        initUI();
        getUserProfile();

        butBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == Activity.RESULT_OK) {
                            Intent data = result.getData();
                            uri = data.getData();
                            avata.setImageURI(uri);
                        } else {
                            Toast.makeText(AdminMain.this, "No Image Selected", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
        );

        avata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent photoPicker = new Intent(Intent.ACTION_PICK);
                photoPicker.setType("image/*");
                activityResultLauncher.launch(photoPicker);
            }
        });

        butProfileNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseUser firebaseUser = auth.getCurrentUser();
                boolean emailVerified = false;
                if (firebaseUser != null ) {
                    emailVerified = firebaseUser.isEmailVerified();
                }
                if (emailVerified) {
                    startActivity(new Intent(AdminMain.this, AdminProfile.class));
                }  else {
                    startActivity(new Intent(AdminMain.this, NVProfile.class));
                }
            }
        });
        butEmailNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminMain.this, ChangeEmail.class));
            }
        });
        butPasswordNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminMain.this, ChangePassword.class));
            }
        });
        butLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                auth.signOut();
                startActivity(new Intent(AdminMain.this, Login.class));
            }
        });

    }

    private void getUserProfile() {
        FirebaseUser firebaseUser = auth.getCurrentUser();
        if (firebaseUser != null ) {
            String name = firebaseUser.getDisplayName();
            String email = firebaseUser.getEmail();
            Uri photoUrl = firebaseUser.getPhotoUrl();
            String uid = firebaseUser.getUid();
            boolean emailVerified = firebaseUser.isEmailVerified();

            txtProfile.setText(name);
            txtEmail.setText(email);
        }
    }

    //    private void getAccountData() {
//        String userId = auth.getUid();
//        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Account");
//        Query checkUserDatabase = reference.orderByChild("userId").equalTo(userId);
//
//        checkUserDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                if (snapshot.exists()) {
//                    String hoTen = snapshot.child(userId).child("hoTen").getValue(String.class);
//                    String email = snapshot.child(userId).child("email").getValue(String.class);
//                    txtProfile.setText(hoTen);
//                    txtEmail.setText(email);
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
//    }
//    private void showUserData() {
//        Intent intent = getIntent();
//
//        String hoTen = intent.getStringExtra("hoTen");
//        String email = intent.getStringExtra("email");
////        String password = intent.getStringExtra("Password");
//
//        txtProfile.setText(hoTen);
//        txtEmail.setText(email);
////        txtPassword.setText(password);
//    }
//
//    public void passUserData() {
//        String email = txtEmail.getText().toString().trim();
//
//        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Account");
//        Query checkUserDatabase = reference.orderByChild("Email").equalTo(email);
//
//        checkUserDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                if (snapshot.exists()) {
//                    String hotenFromDB = snapshot.child(email).child("HoTen").getValue(String.class);
//                    String emailFromDB = snapshot.child(email).child("Email").getValue(String.class);
//                    String passwordFromDB = snapshot.child(email).child("Password").getValue(String.class);
//
//                    Intent intent = new Intent(Account.this, AdminProfile.class);
//
//                    intent.putExtra("HoTen", hotenFromDB);
//                    intent.putExtra("Email", emailFromDB);
//                    intent.putExtra("Password", passwordFromDB);
//
//                    startActivity(intent);
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
//    }
//
    private void initUI() {
        butBack = findViewById(R.id.butBack);
        avata = findViewById(R.id.avata);
        txtProfile = findViewById(R.id.txtProfile);
        txtEmail = findViewById(R.id.txtEmail);
        txtPassword = findViewById(R.id.txtPassword);
        butProfileNext = findViewById(R.id.butProfileNext);
        butEmailNext = findViewById(R.id.butEmailNext);
        butPasswordNext = findViewById(R.id.butPasswordNext);
        butLogout = findViewById(R.id.butLogout);
    }

}