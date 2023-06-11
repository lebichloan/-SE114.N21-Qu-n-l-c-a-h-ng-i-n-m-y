package com.example.se114n21.ViewModels;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
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
import com.google.android.material.imageview.ShapeableImageView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class Account extends AppCompatActivity {

    ImageView avata;
    Uri uri;
    TextView txtProfile, txtEmail, txtPassword;
    ImageButton butProfileNext, butEmailNext, butPasswordNext;
    Button butLogout;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        auth = FirebaseAuth.getInstance();
        initUI();
        getUserProfile();

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
                            Toast.makeText(Account.this, "No Image Selected", Toast.LENGTH_SHORT).show();
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
                    startActivity(new Intent(Account.this, AdminProfile.class));
                }  else {
                    startActivity(new Intent(Account.this, NVProfile.class));
                }
            }
        });
        butEmailNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Account.this, ChangeEmail.class));
            }
        });
        butPasswordNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Account.this, ChangePassword.class));
            }
        });
        butLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                auth.signOut();
                startActivity(new Intent(Account.this, Login.class));
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
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Thông tin tài khoản");
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_back_white);

        avata = findViewById(R.id.avata);
        txtProfile = findViewById(R.id.txtProfile);
        txtEmail = findViewById(R.id.txtEmail);
        txtPassword = findViewById(R.id.txtPassword);
        butProfileNext = findViewById(R.id.butProfileNext);
        butEmailNext = findViewById(R.id.butEmailNext);
        butPasswordNext = findViewById(R.id.butPasswordNext);
        butLogout = findViewById(R.id.butLogout);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}