package com.example.se114n21.ViewModels;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.se114n21.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ChangeEmail extends AppCompatActivity {
    ImageButton butBack;
    EditText txtEmail;
    Button butSave;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_email);

        auth = FirebaseAuth.getInstance();
        initUI();
        FirebaseUser firebaseUser = auth.getCurrentUser();

        if (firebaseUser.equals("")) {
            Toast.makeText(ChangeEmail.this, "User not available", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(ChangeEmail.this, Login.class);
            startActivity(intent);
            finish();
        } else {
            reAuthenticateUser(firebaseUser);
        }

        butBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }

    private void reAuthenticateUser(FirebaseUser firebaseUser) {
        butSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = txtEmail.getText().toString();
                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(ChangeEmail.this, "Please input your new email", Toast.LENGTH_SHORT).show();
                    txtEmail.setError("Please enter your new email to update");
                    txtEmail.requestFocus();
                } else {
                    firebaseUser.updateEmail(email)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(ChangeEmail.this, "Your email address updated", Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(ChangeEmail.this, Account.class));
                                    }
                                }
                            });
                }
            }
        });
    }

    private void initUI() {
        butBack = findViewById(R.id.butBack);
        txtEmail = findViewById(R.id.txtEmail);
        butSave = findViewById(R.id.butSave);
    }
}