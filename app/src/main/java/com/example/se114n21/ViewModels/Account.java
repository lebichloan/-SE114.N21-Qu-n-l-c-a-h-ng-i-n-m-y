package com.example.se114n21.ViewModels;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.se114n21.R;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class Account extends AppCompatActivity {

    ShapeableImageView avata;
    ImageButton butCamera;
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
        showUserData();
        passUserData();

        butCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        butProfileNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                passUserData();
                startActivity(new Intent(Account.this, AdminProfile.class));
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

    private void showUserData() {
        Intent intent = getIntent();

        String hoTen = intent.getStringExtra("HoTen");
        String email = intent.getStringExtra("Email");
        String password = intent.getStringExtra("Password");

        txtProfile.setText(hoTen);
        txtEmail.setText(email);
        txtPassword.setText(password);
    }

    public void passUserData() {
        String email = txtEmail.getText().toString().trim();

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Account");
        Query checkUserDatabase = reference.orderByChild("Email").equalTo(email);

        checkUserDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    String hotenFromDB = snapshot.child(email).child("HoTen").getValue(String.class);
                    String emailFromDB = snapshot.child(email).child("Email").getValue(String.class);
                    String passwordFromDB = snapshot.child(email).child("Password").getValue(String.class);

                    Intent intent = new Intent(Account.this, AdminProfile.class);

                    intent.putExtra("HoTen", hotenFromDB);
                    intent.putExtra("Email", emailFromDB);
                    intent.putExtra("Password", passwordFromDB);

                    startActivity(intent);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void initUI() {
        avata = findViewById(R.id.avata);
        butCamera = findViewById(R.id.butCamera);
        txtProfile = findViewById(R.id.txtProfile);
        txtEmail = findViewById(R.id.txtEmail);
        txtPassword = findViewById(R.id.txtPassword);
        butProfileNext = findViewById(R.id.butProfileNext);
        butEmailNext = findViewById(R.id.butEmailNext);
        butPasswordNext = findViewById(R.id.butPasswordNext);
        butLogout = findViewById(R.id.butLogout);

    }
}