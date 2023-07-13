package com.example.se114n21.ViewModels;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
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
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class Login extends AppCompatActivity {
    ImageButton butBack;
    EditText txtEmail, txtPassword;
    ImageButton eyeButton;
    Button butLogin;
    FirebaseAuth auth;
    FirebaseDatabase database;
    TextView textViewForgotPassword, textViewSignUp;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        getSupportActionBar().hide();

        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        initUI();
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Please wait");
        progressDialog.setCanceledOnTouchOutside(false);

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
//                email = String.valueOf(txtEmail.getText());
//                password = String.valueOf(txtPassword.getText());

                email = txtEmail.getText().toString().trim();
                password = txtPassword.getText().toString().trim();

                if (! email.isEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    if (! password.isEmpty()) {
                        auth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                progressDialog.dismiss();

                                if(task.isSuccessful()){
                                    FirebaseUser user = auth.getCurrentUser();
                                    DatabaseReference reference;
                                    Toast.makeText(getApplicationContext(),"Successfully Login",Toast.LENGTH_LONG).show();
                                    reference = FirebaseDatabase.getInstance().
                                            getReference("Staff").child(user.getEmail());


                                    reference.addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(DataSnapshot dataSnapshot) {
                                            for(DataSnapshot datas: dataSnapshot.getChildren()){
                                                String usertype=datas.child("loaiNhanVien").getValue().toString();

                                                // If the users is professor but if it is
                                                // student go to userStudent.class
                                                if(usertype.equals("admin")){

                                                    startActivity(new
                                                            Intent(getApplicationContext(),BottomNavigation.class));
                                                    finish();

                                                }else if (usertype.equals("staff")) {
                                                    startActivity(new
                                                            Intent(getApplicationContext(),BottomNavigationNhanVien.class));
                                                    finish();
                                                }

                                            }
                                        }

                                        @Override
                                        public void onCancelled(DatabaseError databaseError) {
                                        }
                                    });
                                }

                            }
                        });
                    } else {
                        Toast.makeText(Login.this, "Vui l?ng nh?p v?o m?t kh?u", Toast.LENGTH_SHORT).show();
                        txtPassword.setError("Password canot be empty");
                        txtPassword.requestFocus();
                    }
                } else if (email.isEmpty()) {
                    Toast.makeText(Login.this, "Vui l?ng nh?p v?o ??a ch? email", Toast.LENGTH_SHORT).show();
                    txtEmail.setError("Email cannot be empty");
                    txtEmail.requestFocus();
                } else {
                    Toast.makeText(Login.this, "Vui l?ng nh?p v?o ??a ch? email h?p l?", Toast.LENGTH_SHORT).show();
                    txtEmail.setError("Please enter valid email");
                    txtEmail.requestFocus();
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

    private void initUI() {
        butBack = findViewById(R.id.butBack);
        txtEmail = findViewById(R.id.txtEmail);
        txtPassword = findViewById(R.id.txtPassword);
        eyeButton = findViewById(R.id.eyeButton);
        butLogin = findViewById(R.id.butLogin);
        textViewForgotPassword = findViewById((R.id.textViewForgotPassword));
//        textViewSignUp = findViewById(R.id.textViewSignUp);
    }

//    private void login() {
//        String email = txtEmail.getText().toString().trim();
//        String password = txtPassword.getText().toString().trim();
//
//        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
//            Toast.makeText(this, "Invalid email pattern...", Toast.LENGTH_SHORT).show();
//            return;
//        }
//        if (TextUtils.isEmpty(password)){
//            Toast.makeText(this, "Enter password...", Toast.LENGTH_SHORT).show();
//            return;
//        }
//
//        progressDialog.setMessage("Logging in...");
//        progressDialog.show();
//
//        auth.signInWithEmailAndPassword(email, password)
//                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
//                    @Override
//                    public void onSuccess(AuthResult authResult) {
//                        //logged in successfully
//                        makeMeOnline();
//                    }
//                })
//                .addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//                        //failed logging in
//                        progressDialog.dismiss();
//                        Toast.makeText(Login.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
//                    }
//                });
//
//    }
//
//    private void makeMeOnline() {
//        //after logging in, make user online
//        progressDialog.setMessage("Checking User...");
//
//        HashMap<String, Object> hashMap = new HashMap<>();
//        hashMap.put("online","true");
//
//        //update value to db
//        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Staff");
//        ref.child(auth.getUid()).updateChildren(hashMap)
//                .addOnSuccessListener(new OnSuccessListener<Void>() {
//                    @Override
//                    public void onSuccess(Void unused) {
//                        //update successfully
//                        checkUserType();
//                    }
//                })
//                .addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//                        //failed updating
//                        progressDialog.dismiss();
//                        Toast.makeText(Login.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
//                    }
//                });
//    }
//
//    private void checkUserType() {
//        // if user is seller, start seller main screen
//        // if user is buyer, start user mai screen
//        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Staff");
//        ref.orderByChild("maNV").equalTo(auth.getUid())
//                .addListenerForSingleValueEvent(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                        for (DataSnapshot ds: dataSnapshot.getChildren()){
//                            String accountType = ""+ds.child("loaiNhanVien");
//                            if (accountType.contains("admin")){
//                                progressDialog.dismiss();
//                                //user is seller
//                                startActivity(new Intent(Login.this, BottomNavigation.class));
////                                Intent intent = new Intent(LoginActivity.this, MainUserActivity.class);
////                                intent.putExtra("navigateToHomeFragment", true);
////                                startActivity(intent);
//                                finish();
//                            }
//                            else{
//                                progressDialog.dismiss();
//                                //user is buyer
//                                startActivity(new Intent(Login.this, BottomNavigationNhanVien.class));
////                                Intent intent = new Intent(LoginActivity.this, MainUserActivity.class);
////                                intent.putExtra("navigateToHomeFragment", true);
////                                startActivity(intent);
//                                finish();
//                            }
//                        }
//                    }
//
//
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError error) {
//
//                    }
//                });
//    }

}