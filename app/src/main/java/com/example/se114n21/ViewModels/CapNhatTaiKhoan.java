package com.example.se114n21.ViewModels;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.se114n21.Models.NhanVien;
import com.example.se114n21.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class CapNhatTaiKhoan extends AppCompatActivity {
    private ImageButton btnBack;
    private Button btnChangeAvt, btnSave;
    private CircleImageView circleImageView;
    private EditText name, birthday, phone, address;
    private ProgressDialog progressDialog;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private FirebaseStorage storage = FirebaseStorage.getInstance();
    private StorageReference storageRef = storage.getReference();
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
    private Calendar cal = Calendar.getInstance();
    private ActivityResultLauncher<Intent> selectImageLauncher;
    private Uri imageUri = null;
    SharedPreferences sharedPreferences;
    private static final String SHARED_PREF_NAME = "mypre";
    private static final String KEY_ID = "id";
    private NhanVien nhanVien;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cap_nhat_tai_khoan);
        getSupportActionBar().hide();

        sharedPreferences = getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);

        initUI();

        String keyid = sharedPreferences.getString(KEY_ID, null);
        getData(keyid);
    }

    //    CLEAR FOCUS ON EDIT TEXT
    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (v instanceof EditText) {
                Rect outRect = new Rect();
                v.getGlobalVisibleRect(outRect);
                if (!outRect.contains((int)event.getRawX(), (int)event.getRawY())) {
                    v.clearFocus();
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
        }
        return super.dispatchTouchEvent(event);
    }

    private void getData(String MaND) {
        progressDialog = ProgressDialog.show(CapNhatTaiKhoan.this,"Đang tải", "Vui lòng đợi...",false,false);
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
//                Toast.makeText(CapNhatTaiKhoan.this, "Có lỗi xảy ra!", Toast.LENGTH_SHORT).show();
            showCustomDialogFail("Có lỗi xảy ra. Vui lòng thử lại");
            }
        });
    }

    private void setData(NhanVien nhanVien) {
        Glide.with(this)
                .load(nhanVien.getLinkAvt()) // image url
                .placeholder(R.drawable.blank_img) // any placeholder to load at start
                .error(R.drawable.blank_img)  // any image in case of error
                .into(circleImageView);

        name.setText(nhanVien.getHoTen());
        birthday.setText(nhanVien.getNgaySinh());
        phone.setText(nhanVien.getSDT());
        address.setText(nhanVien.getDiaChi());

        progressDialog.dismiss();
    }

    private void initUI() {
        btnBack = findViewById(R.id.btnBack_CapNhatTaiKhoan);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        btnChangeAvt = findViewById(R.id.btnChangeAvt_CapNhatTaiKhoan);
        btnChangeAvt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                selectImageLauncher.launch(Intent.createChooser(intent, "Select Images"));
            }
        });

        btnSave = findViewById(R.id.btnSave_CapNhatTaiKhoan);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (name.getText().toString().trim().equals("")) {
                    name.setError("Nội dung bắt buộc");
                    name.requestFocus();
                } else {
                    progressDialog = ProgressDialog.show(CapNhatTaiKhoan.this,"Đang tải", "Vui lòng đợi...",false,false);
                    if (imageUri != null) {
                        uploadToStorage(imageUri);
                    } else {
                        capNhatTaiKhoan(null);
                    }
                }
            }
        });

//        TEXTVIEW
        name = findViewById(R.id.txt_name_CapNhatTaiKhoan);

        birthday = findViewById(R.id.txt_birthday_CapNhatTaiKhoan);
        birthday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(CapNhatTaiKhoan.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        cal.set(i,i1,i2);
                        birthday.setText(simpleDateFormat.format(cal.getTime()));
                    }
                }, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.getDatePicker().setMaxDate(new Date().getTime());
                datePickerDialog.show();
            }
        });

        phone = findViewById(R.id.txt_phone_CapNhatTaiKhoan);
        address = findViewById(R.id.txt_address_CapNhatTaiKhoan);

//        AVT
        circleImageView = findViewById(R.id.avt_CapNhatTaiKhoan);

//
        selectImageLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent data = result.getData();
                        if (data != null) {
                            if (data.getData() != null) {
                                imageUri = data.getData();
                                Glide.with(this)
                                        .load(imageUri) // image url
                                        .placeholder(R.drawable.ic_launcher_background) // any placeholder to load at start
                                        .error(R.mipmap.ic_launcher)  // any image in case of error
                                        .into(circleImageView);
                            }
                        }
                    }
                });
    }

    private void uploadToStorage(Uri uri) {
        StorageReference fileRef = storageRef.child( "Avt/" +  System.currentTimeMillis() + "." + getFileExtension(uri));
        fileRef.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                fileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        String URL = uri.toString();

                        if (nhanVien.getLinkAvt() != null) {
                            deleteImageOnStorage(nhanVien.getLinkAvt(), URL);
                        } else {
                            capNhatTaiKhoan(URL);
                        }
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressDialog.dismiss();
//                Toast.makeText(CapNhatTaiKhoan.this, "Upload Image failed!", Toast.LENGTH_SHORT).show();
                showCustomDialogFail("Cập nhật ảnh đại diện không thành công. Vui lòng thử lại sau");
            }
        });
    }

    private String getFileExtension(Uri mUri) {
        ContentResolver cr = getContentResolver();
        MimeTypeMap mime =  MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cr.getType(mUri));
    }

    private void deleteImageOnStorage(String deleteURL, String URL) {
        StorageReference storageReference = storage.getReferenceFromUrl(deleteURL);
        storageReference.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                capNhatTaiKhoan(URL);
            }
        });
    }

    private void capNhatTaiKhoan(String URL) {
        Map<String, Object> map = new HashMap<>();
        if (URL != null) {
            map.put("linkAvt",URL);
        }
        map.put("hoTen",name.getText().toString().trim());
        map.put("ngaySinh",birthday.getText().toString().trim());
        map.put("SDT",phone.getText().toString().trim());
        map.put("diaChi",address.getText().toString().trim());


        DatabaseReference myRef = database.getReference("Users/" + nhanVien.getMaND());

        myRef.updateChildren(map, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                progressDialog.dismiss();
//                Toast.makeText(CapNhatTaiKhoan.this, "Cập nhật thông tin tài khoản thành công!", Toast.LENGTH_SHORT).show();
                showCustomDialogSucess("Cập nhật thông tin tài khoản thành công");
                Intent intent = new Intent(CapNhatTaiKhoan.this, AccountFragment.class);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }

    private void showCustomDialogConfirm(String data){
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

    private void showCustomDialogSucess(String data){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
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

}