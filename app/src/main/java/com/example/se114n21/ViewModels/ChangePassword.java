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
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ChangePassword extends AppCompatActivity {

    EditText txtOldPassword;
    ImageButton eyeButton;
    Button butNext;
    FirebaseAuth auth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        auth = FirebaseAuth.getInstance();
        txtOldPassword = findViewById(R.id.txtOldPassword);
        eyeButton = findViewById(R.id.eyeButton);
        butNext = findViewById(R.id.butNext);

        butNext.setEnabled(false);

        FirebaseUser firebaseUser = auth.getCurrentUser();

        if (firebaseUser.equals("")) {
            Toast.makeText(ChangePassword.this, "User not available", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(ChangePassword.this, Login.class);
            startActivity(intent);
            finish();
        } else {
            reAuthenticateUser(firebaseUser);
        }

        txtOldPassword.addTextChangedListener(new TextWatcher() {
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
            if (txtOldPassword.getInputType() == 129) {
                txtOldPassword.setInputType(InputType.TYPE_CLASS_TEXT);
                eyeButton.setImageDrawable(getDrawable(R.drawable.eye_blind));
            }
            else
            {
                eyeButton.setImageDrawable(getDrawable(R.drawable.eye));
            }
        });

    }


    private void reAuthenticateUser(FirebaseUser firebaseUser) {
            butNext.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                   String oldPassword = txtOldPassword.getText().toString();
                   if (TextUtils.isEmpty(oldPassword)) {
                       Toast.makeText(ChangePassword.this, "Please input your old password", Toast.LENGTH_SHORT).show();
                       txtOldPassword.setError("Please enter your current password to authenticate");
                       txtOldPassword.requestFocus();
                   } else {
                       // show process bar
                       AuthCredential credential = EmailAuthProvider.getCredential(firebaseUser.getEmail(), oldPassword);
                       firebaseUser.reauthenticate(credential).addOnCompleteListener(new OnCompleteListener<Void>() {
                           @Override
                           public void onComplete(@NonNull Task<Void> task) {
                               if (task.isSuccessful()) {
                                   // show process bar
                                   startActivity(new Intent(ChangePassword.this, ConfirmNewPassword.class));
                                   finish();
//                                   txtOldPassword.setEnabled(false);
//                                   txtNewPassword.setEnabled(true);
//                                   txtConfirmNewPassword.setEnabled(true);
                                   butNext.setEnabled(true);
//                                   butSave.setEnabled(true);
                                   Toast.makeText(ChangePassword.this, "You can change password now", Toast.LENGTH_SHORT).show();
                                   
//                                   butSave.setOnClickListener(new View.OnClickListener() {
//                                       @Override
//                                       public void onClick(View v) {
//                                           changePassword(firebaseUser);
//                                       }
//                                   });

//                                   butNext.setOnClickListener(new View.OnClickListener() {
//                                       @Override
//                                       public void onClick(View v) {
//                                           startActivity(new Intent(ChangePassword.this, ConfirmNewPassword.class));
//                                           finish();
//                                       }
//                                   });
                               } else {
                                   try {
                                       throw task.getException();
                                   } catch (Exception e) {
                                       Toast.makeText(ChangePassword.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                   }
                               }
                               //show process bar
                           }
                       });
                   }
                }
            });
    }

}