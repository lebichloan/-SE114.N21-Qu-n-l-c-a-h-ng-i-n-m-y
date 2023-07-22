package com.example.se114n21.ViewModels;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
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
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.se114n21.Adapter.AdapterCustomer;
import com.example.se114n21.Models.KhachHang;
import com.example.se114n21.Models.KhuyenMai;
import com.example.se114n21.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class CustomerActivity extends AppCompatActivity {
    Button addcustomer;
    RecyclerView recyclerView;
    AdapterCustomer adapterCustomer;
    List<KhachHang> listkhachhang;
    SearchView searchView;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.customer);
        initUI();
        addcustomer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CustomerActivity.this,AddCustomerActivity.class);
                startActivity(intent);
            }
        });
        GetListCustomerfromDatabase();
    }

    private void showCustomDialogFail(String data){
        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(this);
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

    private void filterlist(String newText) {
        List<KhachHang> filteredList = new ArrayList<>();
        for (KhachHang item : listkhachhang)
        {
            if ((item.getMaKH().toLowerCase().contains(newText.toLowerCase()))||(item.getTen().toLowerCase().contains(newText.toLowerCase()))||(item.getDienThoai().toLowerCase().contains(newText.toLowerCase()))){
                filteredList.add(item);
            }
        }
        if (filteredList.isEmpty() == false)
        {
            adapterCustomer.setFilteredList(filteredList);
        }
    }

    private void initUI() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Quản lý khách hàng");
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_back_white);

        addcustomer = findViewById(R.id.new_customer);
        recyclerView = findViewById(R.id.recycleview_customer);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        searchView = findViewById(R.id.search_bar);
        searchView.clearFocus();

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this,DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterlist(newText);
                return false;
            }
        });

        listkhachhang = new ArrayList<>();
        adapterCustomer = new AdapterCustomer(listkhachhang, new AdapterCustomer.IclickListener() {
            @Override
            public void OnClickUpdateitem(KhachHang kh) {
                OpenDialogUpdate(kh);
            }

            @Override
            public void OnClickDeleteitem(KhachHang kh) {
//                OnClickdeletedata(kh);
                showCustomDialogConfirm("Bạn muốn xóa thông tin khách hàng đã chọn ?", kh);
            }

            @Override
            public void OnClickGetitem(KhachHang kh) {
                Intent intent = new Intent(CustomerActivity.this, CustomerDetail.class);
                String gettenkh = kh.getTen();
                String getsdt = kh.getDienThoai();
                String getmakh = kh.getMaKH();
                String getloaikh = kh.getLoaiKH();
                String getdiachi = kh.getDiaChi();
                String getemail = kh.getEmail();
                intent.putExtra("detail_tenkhachhang",gettenkh);
                intent.putExtra("detail_sdtkhachhang", getsdt);
                intent.putExtra("detail_makhachhang", getmakh);
                intent.putExtra("detail_loaikhachhang", getloaikh);
                intent.putExtra("detail_diachikhachhang", getdiachi);
                intent.putExtra("detail_emailkhachhang", getemail);
                startActivity(intent);
            }
        });

        recyclerView.setAdapter(adapterCustomer);
    }

    private void showCustomDialogConfirm(String data, KhachHang khachHang){
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
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myref = database.getReference("listKhachHang");

                myref.child(String.valueOf(khachHang.getMaKH())).removeValue(new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                        showCustomDialogSucess("Xóa khách hàng thành công");
                    }
                });

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

    private void showCustomDialogSucess(String data){
        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogViewFail = inflater.inflate(R.layout.dialog_sucess, null);
        builder.setView(dialogViewFail);
        Dialog dialog = builder.create();

        TextView txtAlert = dialogViewFail.findViewById(R.id.txtContent);
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

    private void OnClickdeletedata(KhachHang kh) {
        new AlertDialog.Builder(this).setTitle(getString(R.string.app_name))
                .setMessage("Delete this Customer ?")
                .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        FirebaseDatabase database = FirebaseDatabase.getInstance();
                        DatabaseReference myref = database.getReference("listKhachHang");

                        myref.child(String.valueOf(kh.getMaKH())).removeValue(new DatabaseReference.CompletionListener() {
                            @Override
                            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                                Toast.makeText(CustomerActivity.this, "Delete Successfull",Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }).setNegativeButton("CANCEL",null ).show();
    }

    private void OpenDialogUpdate(KhachHang kh) {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.update_customer);
        Window window = dialog.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(false);

        EditText name = dialog.findViewById(R.id.edt_name);
        EditText sdt = dialog.findViewById(R.id.edt_sdt);
        EditText address = dialog.findViewById(R.id.edt_address);
        EditText email = dialog.findViewById(R.id.edt_email);
        EditText loaikh = dialog.findViewById(R.id.edt_loaikh);
        Button cancel = dialog.findViewById(R.id.cancelupdatecustomer);
        Button updatecustomer = dialog.findViewById(R.id.updatecustomer);

        name.setText(kh.getTen());
        sdt.setText(kh.getDienThoai());
        address.setText(kh.getDiaChi());
        email.setText(kh.getEmail());
        loaikh.setText(kh.getLoaiKH());

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        updatecustomer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myref = database.getReference("listKhachHang");
                String newname = name.getText().toString().trim();
                String newsdt = sdt.getText().toString().trim();
                String newaddress = address.getText().toString().trim();
                String newemail = email.getText().toString().trim();
                String newloaikh = loaikh.getText().toString().trim();
                if (name.getText().toString().isEmpty()){
                    showCustomDialogFail("Vui lòng nhập vào tên khách hàng");
                    name.setError("Please fill information before continue");
                    name.requestFocus();
                }
                if (loaikh.getText().toString().isEmpty()){
                    showCustomDialogFail("Vui lòng nhập vào loại khách hàng");
                    loaikh.setError("Please fill information before continue");
                    loaikh.requestFocus();
                }
                if (! name.getText().toString().isEmpty() && ! loaikh.getText().toString().isEmpty())
                {
                    kh.setTen(newname);
                    kh.setDienThoai(newsdt);
                    kh.setDiaChi(newaddress);
                    kh.setEmail(newemail);
                    kh.setLoaiKH(newloaikh);
                    myref.child(String.valueOf(kh.getMaKH())).updateChildren(kh.toMap(), new DatabaseReference.CompletionListener() {
                        @Override
                        public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                            showCustomDialogSucess("Cập nhật thông tin khách hàng thành công");
                            dialog.dismiss();
                        }
                    });
                }
            }
        });
        dialog.show();
    }

    private void GetListCustomerfromDatabase(){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("listKhachHang");
        myRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                KhachHang kh = snapshot.getValue(KhachHang.class);
                if (kh != null)
                {
                    listkhachhang.add(kh);
                    adapterCustomer.notifyDataSetChanged();
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                KhachHang kh = snapshot.getValue(KhachHang.class);
                if (kh == null || listkhachhang == null || listkhachhang.isEmpty())
                {
                    return;
                }
                for (int i =0; i < listkhachhang.size();i++)
                {
                    if (kh.getMaKH() == listkhachhang.get(i).getMaKH())
                    {
                        listkhachhang.set(i, kh);
                    }
                }
                adapterCustomer.notifyDataSetChanged();
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                KhachHang kh = snapshot.getValue(KhachHang.class);
                if (kh == null || listkhachhang == null || listkhachhang.isEmpty())
                {
                    return;
                }
                for (int i =0; i < listkhachhang.size();i++)
                {
                    if (kh.getMaKH() == listkhachhang.get(i).getMaKH())
                    {
                        listkhachhang.remove(listkhachhang.get(i));
                        break;
                    }
                }
                adapterCustomer.notifyDataSetChanged();
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
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
