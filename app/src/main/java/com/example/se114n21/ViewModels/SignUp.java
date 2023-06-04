package com.example.se114n21.ViewModels;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.se114n21.Models.Account;
import com.example.se114n21.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUp extends AppCompatActivity {

    ImageButton butBack;
    ShapeableImageView avata;
    ImageButton butCamera;
    EditText txtHoTen, txtEmail, txtPassword;
    ImageButton eyeButton;
    Button butSignUp;
    TextView textViewLogin;
    FirebaseAuth auth;
    FirebaseDatabase database;
    DatabaseReference reference;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        initUI();
        auth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Please wait");
        progressDialog.setCanceledOnTouchOutside(false);

        butBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        butSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = txtEmail.getText().toString().trim();
                String password = txtPassword.getText().toString().trim();

                if (email.isEmpty()) {
                    Toast.makeText(SignUp.this, "Vui l?ng nh?p v?o ??a ch? email", Toast.LENGTH_SHORT).show();
                    txtEmail.setError("Email can not be empty");
                    txtEmail.requestFocus();
                }
                if (password.isEmpty()) {
                    Toast.makeText(SignUp.this, "Vui l?ng nh?p v?o m?t kh?u", Toast.LENGTH_SHORT).show();
                    txtPassword.setError("Password can not be empty");
                    txtEmail.requestFocus();
                } else {
                    auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                String userId = auth.getUid();
//                                Update avata
//                                Uri linkAvata =
                                String hoTen = txtHoTen.getText().toString().trim();
//                                S? d?ng bi?n x?c nh?n email ?? ph?n quy?n
//                                String phanQuyen = "admin";

                                FirebaseUser user = auth.getCurrentUser();
                                // G?i email x?c th?c
                                user.sendEmailVerification()
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    startActivity(new Intent(SignUp.this, SignUpSucess.class));
                                                }
                                            }
                                        });
                                // Update profile
                                UserProfileChangeRequest profileUpdate = new UserProfileChangeRequest.Builder()
                                        .setDisplayName(hoTen)
                                        .build();

                                user.updateProfile(profileUpdate)
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    // Update success
                                                }
                                            }
                                        });
                                database = FirebaseDatabase.getInstance();
                                reference = database.getReference("Account");
                                Account account = new Account(userId, linkAvata, hoTen, email, phanQuyen);
                                reference.child(userId).setValue(account);

                            } else {
                                Toast.makeText(SignUp.this, "Sign up failed" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });

        textViewLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignUp.this, Login.class));
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

        eyeButton.setOnClickListener(view -> {
            if (txtPassword.getInputType() == 129) {
                txtPassword.setInputType(InputType.TYPE_CLASS_TEXT);
                eyeButton.setImageDrawable(getDrawable(R.drawable.eye_blind));
            }
            else
            {
                eyeButton.setImageDrawable(getDrawable(R.drawable.eye));
            }
        });

    }

    private void initUI() {
        butBack = findViewById(R.id.butBack);
        avata = findViewById(R.id.avata);
        butCamera = findViewById(R.id.butCamera);
        txtHoTen = findViewById(R.id.txtHoTen);
        txtEmail = findViewById(R.id.txtEmail);
        txtPassword = findViewById(R.id.txtPassword);
        eyeButton = findViewById(R.id.eyeButton);
        butSignUp = findViewById(R.id.butSignUp);
        textViewLogin = findViewById(R.id.textViewLogin);
    }


}