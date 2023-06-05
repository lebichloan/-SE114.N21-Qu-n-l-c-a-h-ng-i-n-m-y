package com.example.se114n21.ViewModels;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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
        DatabaseReference myRef2 = database.getReference("maxKhachHang");
        myRef2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                int maxKhachHang = dataSnapshot.getValue(Integer.class);
                maxid = maxKhachHang;
            }
            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
        IdGenerator generator = new IdGenerator();
        generator.init("KH","",maxid,"%04d");
        id = generator.generate();
        String name = edtname.getText().toString();
        String address = edtaddress.getText().toString();
        String sdt = edtsdt.getText().toString();
        String email = edtemail.getText().toString();
        String loaikh = edtloaikh.getText().toString();
        KhachHang kh = new KhachHang(id,name,address,sdt,email,loaikh);
        myRef.push().setValue(kh);
        maxid = maxid + 1;
        myRef2.setValue(maxid);
    }

    private void initUI() {
        edtname = findViewById(R.id.edtname);
        edtsdt = findViewById(R.id.edtsdt);
        edtaddress = findViewById(R.id.edtaddress);
        edtemail = findViewById(R.id.edtemail);
        edtloaikh = findViewById(R.id.edtloaikh);
        addbutton = findViewById(R.id.addcustomer);
    }
}
