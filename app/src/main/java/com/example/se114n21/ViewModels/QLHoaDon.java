package com.example.se114n21.ViewModels;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.se114n21.Adapter.AdapterHoaDon;
import com.example.se114n21.Models.HoaDon;
import com.example.se114n21.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class QLHoaDon extends AppCompatActivity {

    Button addhoadon;
    RecyclerView recyclerView;
    AdapterHoaDon adapterHoaDon;
    List<HoaDon> listhoadon;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qlhoa_don);
        initUI();
        addhoadon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(QLHoaDon.this,ThemHoaDon.class);
                startActivity(intent);
            }
        });
        GetListHoaDonfromDatabase();
    }

    private void initUI() {
        addhoadon = findViewById(R.id.addHoadonbut);
        recyclerView = findViewById(R.id.recycleview_hoadon);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this,DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);

        listhoadon = new ArrayList<>();
        adapterHoaDon = new AdapterHoaDon(listhoadon, new AdapterHoaDon.IclickListener() {
            @Override
            public void OnClickDeleteitem(HoaDon hd) {
                OnClickdeletedata(hd);
            }
        });

        recyclerView.setAdapter(adapterHoaDon);
    }

    private void OnClickdeletedata(HoaDon hd) {
        new AlertDialog.Builder(this).setTitle(getString(R.string.app_name))
                .setMessage("Bạn muốn xoá khách hàng này ?")
                .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        FirebaseDatabase database = FirebaseDatabase.getInstance();
                        DatabaseReference myref = database.getReference("listHoaDon");

                        myref.child(String.valueOf(hd.getMaHD())).removeValue(new DatabaseReference.CompletionListener() {
                            @Override
                            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                                Toast.makeText(QLHoaDon.this, "Delete Successfull",Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }).setNegativeButton("CANCEL",null ).show();
    }

    private void GetListHoaDonfromDatabase(){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("listHoaDon");
        myRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                HoaDon hd = snapshot.getValue(HoaDon.class);
                if (hd != null)
                {
                    listhoadon.add(hd);
                    adapterHoaDon.notifyDataSetChanged();
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                HoaDon hd = snapshot.getValue(HoaDon.class);
                if (hd == null || listhoadon == null || listhoadon.isEmpty())
                {
                    return;
                }
                for (int i =0; i < listhoadon.size();i++)
                {
                    if (hd.getMaHD() == listhoadon.get(i).getMaHD())
                    {
                        listhoadon.set(i, hd);
                    }
                }
                adapterHoaDon.notifyDataSetChanged();
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                HoaDon hd = snapshot.getValue(HoaDon.class);
                if (hd == null || listhoadon == null || listhoadon.isEmpty())
                {
                    return;
                }
                for (int i =0; i < listhoadon.size();i++)
                {
                    if (hd.getMaHD() == listhoadon.get(i).getMaHD())
                    {
                        listhoadon.remove(listhoadon.get(i));
                        break;
                    }
                }
                adapterHoaDon.notifyDataSetChanged();
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}