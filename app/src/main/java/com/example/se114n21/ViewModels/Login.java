package com.example.se114n21.ViewModels;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.se114n21.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Login extends AppCompatActivity {
    ImageButton butBack;
    EditText txtEmail, txtPassword;
    ImageButton eyeButton;
    Button butLogin;
    FirebaseAuth auth;
    ProgressBar processBar;
    TextView textViewForgotPassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        auth = FirebaseAuth.getInstance();
        butBack = findViewById(R.id.butBack);
        txtEmail = findViewById(R.id.txtEmail);
        txtPassword = findViewById(R.id.txtPassword);
        eyeButton = findViewById(R.id.eyeButton);
        butLogin = findViewById(R.id.butLogin);
        textViewForgotPassword = findViewById((R.id.textViewForgotPassword));

        // Ki?m tra user ?? ??ng nh?p hay ch?a
        // ??nh d?u ch? c?a h?ng v?i nh?n vi?n b?ng vi?c verified email
//        FirebaseUser user = auth.getCurrentUser();
//        if (user != null) {
//            startActivity(new Intent(Login.this, MainActivity.class));
//        }
        butBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        butLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email, password;
                email = String.valueOf(txtEmail.getText());
                password = String.valueOf(txtPassword.getText());

                if (! email.isEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    if (! password.isEmpty()) {
                        auth.signInWithEmailAndPassword(email, password)
                                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                                    @Override
                                    public void onSuccess(AuthResult authResult) {
                                        Toast.makeText(Login.this, "Login Sucessful", Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(Login.this, Account.class));
                                        finish();
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(Login.this, "Login Failed", Toast.LENGTH_SHORT).show();
                                    }
                                });
                    } else {
                        txtPassword.setError("Password canot be empty");
                    }
                } else if (email.isEmpty()) {
                    txtEmail.setError("Email cannot be empty");
                } else {
                    txtEmail.setError("Please enter valid email");
                }

            }
        });
        textViewForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Login.this, ForgotPassword.class));
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

}

