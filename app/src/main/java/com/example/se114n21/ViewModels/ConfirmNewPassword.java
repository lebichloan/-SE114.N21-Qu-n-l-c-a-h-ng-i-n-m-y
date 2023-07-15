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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

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
        getSupportActionBar().hide();

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

        if (newPassword.isEmpty()) {
            showCustomDialogFail("Vui lòng nhập vào mật khẩu mới của bạn");
            txtNewPassword.setError("Please enter your new password");
            txtNewPassword.requestFocus();
        } else if (confirmNewPassword.isEmpty()) {
            showCustomDialogFail("Vui lòng xác nhận mật khẩu mới trước khi tiếp tục");
            txtConfirmNewPassword.setError("Please re-enter your new password");
            txtConfirmNewPassword.requestFocus();
        } else if (! newPassword.matches(confirmNewPassword)) {
            showCustomDialogFail("Mật khẩu mới và mật khẩu xác nhận phải trùng khớp");
            txtConfirmNewPassword.setError("Please re-enter same password");
            txtConfirmNewPassword.requestFocus();
        }
        else {
            firebaseUser.updatePassword(newPassword).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        showCustomDialogSucess("Đổi mật khẩu thành công");
                        startActivity(new Intent(ConfirmNewPassword.this, Account.class));
                        finish();
                    } else {
                        try {
                            throw task.getException();
                        } catch (Exception e) {
                            showCustomDialogFail(e.getMessage());
//                            Toast.makeText(ConfirmNewPassword.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            });

        }
    }
    private void showCustomDialogSucess(String data) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_sucess, null);
        builder.setView(dialogView);
        Dialog dialog = builder.create();
        TextView txtContent = dialogView.findViewById(R.id.txtContent);
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
    private void showCustomDialogFail(String data){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogViewFail = inflater.inflate(R.layout.dialog_fail, null);
        builder.setView(dialogViewFail);
        Dialog dialog = builder.create();

        TextView txtAlert = dialogViewFail.findViewById(R.id.txtAlert);
        txtAlert.setText(data);
        Button butOK = dialogViewFail.findViewById(R.id.butOK);
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

}