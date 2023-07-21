package com.example.se114n21.ViewModels;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
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
                Pushdata();
                Toast.makeText(getApplicationContext(),"Successful!",Toast.LENGTH_SHORT).show();
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
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
        Intent intent = new Intent(AddCustomerActivity.this,CustomerActivity.class);
        startActivity(intent);
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
