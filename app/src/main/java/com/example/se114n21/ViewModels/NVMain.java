package com.example.se114n21.ViewModels;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.se114n21.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class NVMain extends AppCompatActivity {

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
        setContentView(R.layout.activity_nvmain);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setSelectedItemId(R.id.bottom_account);

        bottomNavigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()){
                case R.id.bottom_khachHang:
                    startActivity(new Intent(getApplicationContext(), QLKhachHang.class));
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                    finish();
                    return true;
                case R.id.bottom_sanPham:
                    startActivity(new Intent(getApplicationContext(), QLSanPham.class));
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                    finish();
                    return true;
                case R.id.bottom_hoaDon:
                    startActivity(new Intent(getApplicationContext(), QLHoaDon.class));
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                    finish();
                    return true;
                case R.id.bottom_account:
//                    startActivity(new Intent(getApplicationContext(), NVMain.class));
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
//                            Toast.makeText(NVMain.this, "No Image Selected", Toast.LENGTH_SHORT).show();
                            showCustomDialogFail("Vui lòng chọn hình ảnh để tiếp tục");
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
                    startActivity(new Intent(NVMain.this, AdminProfile.class));
                }  else {
                    startActivity(new Intent(NVMain.this, NVProfile.class));
                }
            }
        });
        butEmailNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(NVMain.this, ChangeEmail.class));
            }
        });
        butPasswordNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(NVMain.this, ChangePassword.class));
            }
        });
        butLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                auth.signOut();
                startActivity(new Intent(NVMain.this, Login.class));
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