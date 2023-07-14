package com.example.se114n21.ViewModels;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.se114n21.R;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class Account extends AppCompatActivity {

    ImageView avata;
    Uri uri;
    TextView txtProfile, txtEmail, txtPassword;
    ImageButton butProfileNext, butEmailNext, butPasswordNext;
    Button butLogout;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        auth = FirebaseAuth.getInstance();
        initUI();
        getData();

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
                            Toast.makeText(Account.this, "No Image Selected", Toast.LENGTH_SHORT).show();
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

        butProfileNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseUser user = auth.getCurrentUser();
                DatabaseReference reference;
                reference = FirebaseDatabase.getInstance().
                        getReference("NhanVien").child(user.getUid());

                reference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()){
                            String usertype= dataSnapshot.child("loaiNhanVien").getValue().toString();
                            if(usertype.equals("admin")){
                                startActivity(new
                                        Intent(getApplicationContext(),AdminProfile.class));
                                finish();
                            }else if (usertype.equals("staff")) {
                                startActivity(new
                                        Intent(getApplicationContext(),NVProfile.class));
                                finish();
                            }
                        }
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });
            }
        });
        butEmailNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Account.this, ChangeEmail.class));
            }
        });
        butPasswordNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Account.this, ChangePassword.class));
            }
        });
        butLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCustomDialog("Bạn chắc chắn muốn đăng xuất");
            }
        });
    }

    private void showCustomDialog(String data){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_confirm, null);
        builder.setView(dialogView);
        Dialog dialog = builder.create();
        TextView txtContent = dialogView.findViewById(R.id.txtContent);
        txtContent.setText(data);
        Button butOK = dialogView.findViewById(R.id.butOK);
        butOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                auth.signOut();
                startActivity(new Intent(Account.this, Login.class));
            }
        });

        Button butCancel = dialogView.findViewById(R.id.butCancel);
        butCancel.setOnClickListener(new View.OnClickListener() {
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

    private void getData(){
        FirebaseUser user = auth.getCurrentUser();
        DatabaseReference reference;
        reference = FirebaseDatabase.getInstance().
                getReference("NhanVien").child(user.getUid());

        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    String hoTen = dataSnapshot.child("hoTen").getValue().toString();
                    String email = dataSnapshot.child("email").getValue().toString();
                    txtProfile.setText(hoTen);
                    txtEmail.setText(email);
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

    }
    private void initUI() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Thông tin tài khoản");
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_back_white);

        avata = findViewById(R.id.avata);
        txtProfile = findViewById(R.id.txtProfile);
        txtEmail = findViewById(R.id.txtEmail);
        txtPassword = findViewById(R.id.txtPassword);
        butProfileNext = findViewById(R.id.butProfileNext);
        butEmailNext = findViewById(R.id.butEmailNext);
        butPasswordNext = findViewById(R.id.butPasswordNext);
        butLogout = findViewById(R.id.butLogout);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}