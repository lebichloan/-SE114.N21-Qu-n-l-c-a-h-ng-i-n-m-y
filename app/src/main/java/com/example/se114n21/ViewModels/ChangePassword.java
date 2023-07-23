package com.example.se114n21.ViewModels;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.se114n21.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ChangePassword extends AppCompatActivity {
    ImageButton butBack;
    EditText txtOldPassword;
    ImageButton eyeButton;
    Button butNext;
    FirebaseAuth auth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        getSupportActionBar().hide();

        auth = FirebaseAuth.getInstance();
        txtOldPassword = findViewById(R.id.txtOldPassword);
        eyeButton = findViewById(R.id.eyeButton);
        butNext = findViewById(R.id.butNext);
        butBack = findViewById(R.id.butBack);

        FirebaseUser firebaseUser = auth.getCurrentUser();

        if (firebaseUser.equals("")) {
            showCustomDialog("Vui lòng đăng nhập để tiếp tục");
            Intent intent = new Intent(ChangePassword.this, Login.class);
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

    private void showCustomDialog(String data){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_fail, null);
        builder.setView(dialogView);
        Dialog dialog = builder.create();
        TextView txtContent = dialogView.findViewById(R.id.txtAlert);
        txtContent.setText(data);
        Button butOK = dialogView.findViewById(R.id.butOK);
        butOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        Window dialogWindow = dialog.getWindow();
        if (dialogWindow != null) {
            WindowManager.LayoutParams layoutParams = dialogWindow.getAttributes();
            layoutParams.gravity = Gravity.TOP;
            layoutParams.y = (int) getResources().getDimension(R.dimen.dialog_margin_top);
            dialogWindow.setAttributes(layoutParams);
        }
        dialog.show();

    }


    private void reAuthenticateUser(FirebaseUser firebaseUser) {
            butNext.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                   String oldPassword = txtOldPassword.getText().toString();
                   if (TextUtils.isEmpty(oldPassword)) {
                       showCustomDialog("Vui lòng nhập vào mật khẩu của bạn");
                       txtOldPassword.setError("Please enter your current password to authenticate");
                       txtOldPassword.requestFocus();
                   } else {
                       AuthCredential credential = EmailAuthProvider.getCredential(firebaseUser.getEmail(), oldPassword);
                       firebaseUser.reauthenticate(credential).addOnCompleteListener(new OnCompleteListener<Void>() {
                           @Override
                           public void onComplete(@NonNull Task<Void> task) {
                               if (task.isSuccessful()) {
//                                   Toast.makeText(ChangePassword.this, "You can change password now", Toast.LENGTH_SHORT).show();
                                   startActivity(new Intent(ChangePassword.this, ConfirmNewPassword.class));
                                   finish();
                               } else {
                                   try {
                                       throw task.getException();
                                   } catch (Exception e) {
                                       showCustomDialog(e.getMessage());
//                                       Toast.makeText(ChangePassword.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                   }
                               }
                           }
                       });
                   }
                }
            });
    }

}