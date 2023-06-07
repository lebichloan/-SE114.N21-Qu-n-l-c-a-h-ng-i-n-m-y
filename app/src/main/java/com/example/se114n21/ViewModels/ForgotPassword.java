package com.example.se114n21.ViewModels;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.se114n21.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPassword extends AppCompatActivity {
    ImageButton butBack;
    EditText txtForgotPasswordEmail;
    Button butNext;
    FirebaseAuth auth;
    ProgressBar processBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        auth = FirebaseAuth.getInstance();
        butBack = findViewById(R.id.butBack);
        txtForgotPasswordEmail = findViewById(R.id.txtForgotPasswordEmail);
        butNext = findViewById(R.id.butNext);

        butBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        butNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                String email;
//                email = String.valueOf(txtForgotPasswordEmail.getText());
                String userEmail = txtForgotPasswordEmail.getText().toString();

                if (TextUtils.isEmpty(userEmail) && !Patterns.EMAIL_ADDRESS.matcher(userEmail).matches()) {
                    Toast.makeText(ForgotPassword.this, "Enter your email", Toast.LENGTH_SHORT).show();
                    return;
                }
                auth.sendPasswordResetEmail(userEmail).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            startActivity(new Intent(ForgotPassword.this, SendEmailSucess.class));
                            finish();
//                            Toast.makeText(ForgotPassword.this, "Check your email", Toast.LENGTH_SHORT).show();
//                            dialog.dismiss();
                        } else {
                            Toast.makeText(ForgotPassword.this, "Unable to send email", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        });
    }
}