package com.example.se114n21.ViewModels;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
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

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_customer);
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
                String loaikh = edtloaikh.getText().toString();
                KhachHang kh = new KhachHang(id,name,address,sdt,email,loaikh);
                myRef.child(id).setValue(kh);
                maxid = maxid + 1;
                myref2.setValue(maxid);
                showCustomDialogSucess("Thêm khách hàng mới thành công");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                showCustomDialogFail("Quá trình thất bại. Vui lòng thử lại");
            }
        });
    }

    private boolean isValidForm(){
        if (isTenKHEmpty()){
            showCustomDialogFail("Vui lòng nhập vào tên khách hàng");
            edtname.setError("Please fill information before next");
            edtname.requestFocus();
            return false;
        } else if (isLoaiKHEmpty()){
            showCustomDialogFail("Vui lòng nhập vào loại khách hàng");
            edtloaikh.setError("Please fill information before next");
            edtloaikh.requestFocus();
            return false;
        }
        return true;
    }

    private boolean isTenKHEmpty(){
        return edtname.getText().toString().isEmpty();
    }

    private boolean isLoaiKHEmpty(){
        return edtloaikh.getText().toString().isEmpty();
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

    private void initUI() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Thêm khách hàng mới");
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_back_white);

        edtname = findViewById(R.id.edtname);
        edtsdt = findViewById(R.id.edtsdt);
        edtaddress = findViewById(R.id.edtaddress);
        edtemail = findViewById(R.id.edtemail);
        edtloaikh = findViewById(R.id.edtloaikh);
        addbutton = findViewById(R.id.addcustomer);
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
