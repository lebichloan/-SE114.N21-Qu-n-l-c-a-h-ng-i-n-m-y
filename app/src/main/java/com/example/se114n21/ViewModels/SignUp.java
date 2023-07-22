package com.example.se114n21.ViewModels;

import android.app.Activity;
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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.se114n21.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class SignUp extends AppCompatActivity {

    ImageButton butBack;
    ImageView avata;
    String linkAvata;
    Uri uri;
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
                            Toast.makeText(SignUp.this, "No Image Selected", Toast.LENGTH_SHORT).show();
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
                                Uri urlImage = null;
                                String hoTen = txtHoTen.getText().toString().trim();
//                                S? d?ng bi?n x?c nh?n email ?? ph?n quy?n
                                String phanQuyen = "admin";

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

//                                ??y data l?n firebase realtime
                                StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("Account")
                                        .child(uri.getLastPathSegment());

                                AlertDialog.Builder builder = new AlertDialog.Builder(SignUp.this);
                                builder.setCancelable(false);
                                builder.setView(R.layout.dialog_loading);
                                AlertDialog dialog = builder.create();
                                dialog.show();

                                storageReference.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                    @Override
                                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                        Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                                        while (!uriTask.isComplete());
                                        Uri urlImage = uriTask.getResult();
                                        linkAvata = urlImage.toString();
//                                        Account account = new Account(userId, linkAvata, hoTen, email, phanQuyen);
//                                        FirebaseDatabase.getInstance().getReference("Account").child(userId)
//                                                        .setValue(account).addOnCompleteListener(new OnCompleteListener<Void>() {
//                                                    @Override
//                                                    public void onComplete(@NonNull Task<Void> task) {
//                                                        if (task.isSuccessful()){
////                                                            Th?ng b?o luwu th?nh c?ng
////                                                            Toast.makeText(SignUp.this, "Saved", Toast.LENGTH_SHORT).show();
////                                                            finish();
//                                                        }
//                                                    }
//                                                }).addOnFailureListener(new OnFailureListener() {
//                                                    @Override
//                                                    public void onFailure(@NonNull Exception e) {
////                                                        B?o l?i
////                                                        Toast.makeText(SignUp.this, e.getMessage().toString(), Toast.LENGTH_SHORT).show();
//                                                    }
//                                                });
//                                        dialog.dismiss();
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        dialog.dismiss();
                                    }
                                });
//                                database = FirebaseDatabase.getInstance();
//                                reference = database.getReference("Account");
//                                Account account = new Account(userId, linkAvata, hoTen, email, phanQuyen);
//                                reference.child(userId).setValue(account);
                                
                                // Update profile
                                UserProfileChangeRequest profileUpdate = new UserProfileChangeRequest.Builder()
                                        .setDisplayName(hoTen)
                                        // Update avata
                                        .setPhotoUri(urlImage)
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
        txtHoTen = findViewById(R.id.txtHoTen);
        txtEmail = findViewById(R.id.txtEmail);
        txtPassword = findViewById(R.id.txtPassword);
        eyeButton = findViewById(R.id.eyeButton);
        butSignUp = findViewById(R.id.butSignUp);
        textViewLogin = findViewById(R.id.textViewLogin);
    }

}