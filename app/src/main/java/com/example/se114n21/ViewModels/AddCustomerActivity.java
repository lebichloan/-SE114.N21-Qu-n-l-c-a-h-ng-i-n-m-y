package com.example.se114n21.ViewModels;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.se114n21.Models.IdGenerator;
import com.example.se114n21.Models.KhachHang;
import com.example.se114n21.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AddCustomerActivity extends AppCompatActivity {
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("listKhachHang");
    FirebaseDatabase database1 = FirebaseDatabase.getInstance();
    DatabaseReference myref2 = database1.getReference("maxKhachHang");
    int maxid;
    EditText edtname,edtsdt,edtaddress,edtemail,edtloaikh;
    Button addbutton;
    String id;
    private ImageButton btnBack;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_customer);
        getSupportActionBar().hide();


        initUI();
        addbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isValidForm()) {
                    Pushdata();
                    Intent intent = new Intent(AddCustomerActivity.this,CustomerActivity.class);
                    startActivity(intent);
                }
            }
        });
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

    private void Pushdata() {
        myref2.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int count = snapshot.getValue(Integer.class);
                maxid = count;
                IdGenerator generator = new IdGenerator();
                generator.init("KH","",maxid,"%04d");
                id = generator.generate();
                String name = edtname.getText().toString();
                String address = edtaddress.getText().toString();
                String sdt = edtsdt.getText().toString();
                String email = edtemail.getText().toString();
                KhachHang kh = new KhachHang(id,name,address,sdt,email, "null");
                myRef.child(id).setValue(kh);
                maxid = maxid + 1;
                myref2.setValue(maxid);
                showCustomDialogSucess("Thêm khách hàng mới thành công");
                onBackPressed();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                showCustomDialogFail("Quá trình thất bại. Vui lòng thử lại");
            }
        });
    }

    private boolean isValidForm(){
        if (isTenKHEmpty()){
//            showCustomDialogFail("Vui lòng nhập vào tên khách hàng");
            edtname.setError("Nội dung bắt buộc");
            edtname.requestFocus();
            return false;
        }
        return true;
    }

    private boolean isTenKHEmpty(){
        return edtname.getText().toString().isEmpty();
    }


    private void showCustomDialogFail(String data){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogViewFail = inflater.inflate(R.layout.dialog_fail, null);
        builder.setView(dialogViewFail);
        Dialog dialog = builder.create();

//        TextView txtAlert = dialogViewFail.findViewById(R.id.txtAlert);
//        txtAlert.setText(data);
//        Button butOK = dialogViewFail.findViewById(R.id.butOK);
//        butOK.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                dialog.dismiss();
//            }
//        });

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

    private void initUI() {
        edtname = findViewById(R.id.edtname);
        edtsdt = findViewById(R.id.edtsdt);
        edtaddress = findViewById(R.id.edtaddress);
        edtemail = findViewById(R.id.edtemail);
        addbutton = findViewById(R.id.addcustomer);

        btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

}
