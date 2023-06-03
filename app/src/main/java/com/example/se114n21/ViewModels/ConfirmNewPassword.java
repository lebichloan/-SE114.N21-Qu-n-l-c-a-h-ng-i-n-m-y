package com.example.se114n21.ViewModels;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
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

public class ConfirmNewPassword extends AppCompatActivity {
    ImageButton butBack;
    EditText txtNewPassword, txtConfirmNewPassword;
    ImageButton eyeButton, eyeButton1;
    Button butSave;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_new_password);

        auth = FirebaseAuth.getInstance();
        butBack = findViewById(R.id.butBack);
        txtNewPassword = findViewById(R.id.txtNewPassword);
        txtConfirmNewPassword = findViewById(R.id.txtConfirmNewPassword);
        eyeButton = findViewById(R.id.eyeButton);
        eyeButton1 = findViewById(R.id.eyeButton1);
        butSave = findViewById(R.id.butSave);
        FirebaseUser firebaseUser = auth.getCurrentUser();

        butBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        butSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changePassword(firebaseUser);
            }
        });
        txtNewPassword.addTextChangedListener(new TextWatcher() {
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
            if (txtNewPassword.getInputType() == 129) {
                txtNewPassword.setInputType(InputType.TYPE_CLASS_TEXT);
                eyeButton.setImageDrawable(getDrawable(R.drawable.eye_blind));
            }
            else
            {
                eyeButton.setImageDrawable(getDrawable(R.drawable.eye));
            }
        });

        txtConfirmNewPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() > 0) {
                    eyeButton1.setVisibility(View.VISIBLE);
                } else {
                    eyeButton1.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        eyeButton1.setOnClickListener(view -> {
            if (txtConfirmNewPassword.getInputType() == 129) {
                txtConfirmNewPassword.setInputType(InputType.TYPE_CLASS_TEXT);
                eyeButton1.setImageDrawable(getDrawable(R.drawable.eye_blind));
            }
            else
            {
                eyeButton1.setImageDrawable(getDrawable(R.drawable.eye));
            }
        });


    }

    private void changePassword(FirebaseUser firebaseUser) {
        String newPassword = txtNewPassword.getText().toString();
        String confirmNewPassword = txtConfirmNewPassword.getText().toString();

        if (TextUtils.isEmpty(newPassword)) {
            Toast.makeText(ConfirmNewPassword.this, "New password is need", Toast.LENGTH_SHORT).show();
            txtNewPassword.setError("Please enter your new password");
            txtNewPassword.requestFocus();
        } else if (TextUtils.isEmpty(confirmNewPassword)) {
            Toast.makeText(ConfirmNewPassword.this, "Please confirm your new password", Toast.LENGTH_SHORT).show();
            txtConfirmNewPassword.setError("Please re-enter your new password");
            txtConfirmNewPassword.requestFocus();
        } else if (! newPassword.matches(confirmNewPassword)) {
            Toast.makeText(ConfirmNewPassword.this, "Password did not match", Toast.LENGTH_SHORT).show();
            txtConfirmNewPassword.setError("Please re-enter same password");
            txtConfirmNewPassword.requestFocus();
        }
//        else if (! oldPassword.matches(newPassword)) {
//            Toast.makeText(ConfirmNewPassword.this, "New password can not be same old password", Toast.LENGTH_SHORT).show();
//            txtNewPassword.setError("Please enter new password");
//            txtNewPassword.requestFocus();
//        }
        else {
            // show process bar
            firebaseUser.updatePassword(newPassword).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        Toast.makeText(ConfirmNewPassword.this, "Password has been change", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(ConfirmNewPassword.this, Account.class);
                        startActivity(intent);
                        finish();
                    } else {
                        try {
                            throw task.getException();
                        } catch (Exception e) {
                            Toast.makeText(ConfirmNewPassword.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                    //show process bar

                }
            });
        }
    }

}