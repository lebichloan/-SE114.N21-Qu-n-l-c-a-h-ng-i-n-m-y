package com.example.se114n21.ViewModels;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.se114n21.Models.NhanVien;
import com.example.se114n21.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import de.hdodenhof.circleimageview.CircleImageView;

public class DetailStaff extends AppCompatActivity {
    private CircleImageView avt;
    private TextView role, id, name, phone, email, birthday, address, state;
    private Button delete;
    private ImageButton btnBack, btnEdit;
    private ProgressDialog progressDialog;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private NhanVien nhanVien = new NhanVien();
    private ActivityResultLauncher<Intent> updateStaffLauncher;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_staff);
        getSupportActionBar().hide();

        initUI();

        Intent i = getIntent();
        String MaND = i.getStringExtra("MaND");
        
        getData(MaND);

        updateStaffLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == Activity.RESULT_OK) {
                            getData(MaND);
                        }
                    }
                });
    }
    
    private void getData(String MaND) {
        progressDialog.show();
        DatabaseReference myRef = database.getReference("Users/" + MaND);

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                nhanVien = snapshot.getValue(NhanVien.class);
                setData(nhanVien);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                progressDialog.dismiss();
                Toast.makeText(DetailStaff.this, "Có lỗi xảy ra!", Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void showCustomDialogConfirm(String data){
        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(this);
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

    private void showCustomDialogFail(String data){
        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogViewFail = inflater.inflate(R.layout.dialog_fail, null);
        builder.setView(dialogViewFail);
        Dialog dialog = builder.create();

        TextView txtAlert = dialogViewFail.findViewById(R.id.txtAlert);
        txtAlert.setText(data);

        Window dialogWindow = dialog.getWindow();
        if (dialogWindow != null) {
            WindowManager.LayoutParams layoutParams = dialogWindow.getAttributes();
            layoutParams.gravity = Gravity.TOP;
            layoutParams.y = (int) getResources().getDimension(R.dimen.dialog_margin_top);
            dialogWindow.setAttributes(layoutParams);
        }
        dialog.show();
    }

    private void showCustomDialogSucess(String data){
        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogViewFail = inflater.inflate(R.layout.dialog_sucess, null);
        builder.setView(dialogViewFail);
        Dialog dialog = builder.create();

        TextView txtContent = dialogViewFail.findViewById(R.id.txtContent);
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

    private void setData(NhanVien nhanVien) {
        Glide.with(this)
                .load(nhanVien.getLinkAvt()) // image url
                .placeholder(R.drawable.ic_launcher_background) // any placeholder to load at start
                .error(R.mipmap.ic_launcher)  // any image in case of error
                .into(avt);

        if (nhanVien.getMaNV() != null) {
            role.setText("Nhân viên");
            id.setText(nhanVien.getMaNV());
        } else {
            role.setText("Admin");
            id.setText("--");
        }

        if (nhanVien.isTrangThai() == false) {
            state.setText("Đã nghỉ việc");
            state.setTextColor(Color.parseColor("#EE786C"));
        } else {
            state.setText("Đang làm việc");
            state.setTextColor(Color.parseColor("#39AAFC"));
        }

        name.setText(nhanVien.getHoTen());
        phone.setText(nhanVien.getSDT());
        email.setText(nhanVien.getEmail());
        birthday.setText(nhanVien.getNgaySinh());
        address.setText(nhanVien.getDiaChi());

        if (nhanVien.getLoaiNhanVien().equals("Admin")) {
            delete.setVisibility(View.GONE);
            btnEdit.setVisibility(View.GONE);
        }

        progressDialog.dismiss();
    }

    private void deleteStaff() {
        new AlertDialog.Builder(DetailStaff.this)
                .setTitle("Xóa")
                .setMessage("Bạn có chắc chắn xóa người dùng này không?")
                .setPositiveButton("Xóa", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        progressDialog.show();

                        String path = "Users/" + nhanVien.getMaND();

                        DatabaseReference myRef = database.getReference(path);

                        myRef.removeValue(new DatabaseReference.CompletionListener() {
                            @Override
                            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                                if (error == null) {
//                                    Toast.makeText(DetailStaff.this, "Xóa người dùng thành công!", Toast.LENGTH_SHORT).show();
                                showCustomDialogSucess("Xóa người dùng thành công");
                                } else {
//                                    Toast.makeText(DetailStaff.this, "Có lỗi xảy ra!", Toast.LENGTH_SHORT).show();
                                    showCustomDialogFail("Có lỗi xảy ra. Vui lòng thử lại sau");
                                }
                                progressDialog.dismiss();

                                Intent intent = new Intent(DetailStaff.this, ListStaff.class);
                                setResult(RESULT_OK, intent);
                                finish();
                            }
                        });
                    }
                })
                .setNegativeButton("Hủy", null)
                .show();
    }

    private void initUI() {
//        PROGRESS DIALOG
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait!");
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);

//        AVT
        avt = findViewById(R.id.avt_staff);

//        TEXTVIEW
        role = findViewById(R.id.tv_role_detail_staff);
        id = findViewById(R.id.tv_id_detail_staff);
        name = findViewById(R.id.tv_name_detail_staff);
        phone = findViewById(R.id.tv_phone_detail_staff);
        email = findViewById(R.id.tv_email_detail_staff);
        birthday = findViewById(R.id.tv_birthday_detail_staff);
        address = findViewById(R.id.tv_address_detail_staff);
        state = findViewById(R.id.tv_state_detail_staff);

//        DELETE BUTTON
        delete = findViewById(R.id.btn_delete_detail_staff);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteStaff();
            }
        });

//        BACK BUTTON
        btnBack = findViewById(R.id.btnBack_DetailStaff);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

//        EDIT BUTTON
        btnEdit = findViewById(R.id.btnEdit_DetailStaff);
        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DetailStaff.this, UpdateStaff.class);
                intent.putExtra("MaND", nhanVien.getMaND());
                updateStaffLauncher.launch(intent);
            }
        });
    }
}