package com.example.se114n21.ViewModels;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.se114n21.Models.KhuyenMai;
import com.example.se114n21.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class EditSale extends AppCompatActivity {

    ImageView image_sale;
    Uri uri;
    EditText txtTenCT;
    EditText txtMoTa;
    EditText txtNgayBD;
    EditText txtNgayKT;
    SimpleDateFormat dateFormat;
    EditText txtDonToiThieu;
    EditText txtKhuyenMai;
    EditText txtGiamToiDa;
    Button butSaveSale;
    KhuyenMai km;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_sale);

        initUI();

        String maKM = null;
        Intent intent = getIntent();
        if (intent != null){
            maKM = intent.getStringExtra("maKM");
            Log.d("Edit sale maKM: ", maKM);
            getData(maKM);
        }
//        setDataSale(maKM);

        butSaveSale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editSale(km);
            }
        });

        txtNgayBD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog(txtNgayBD);
            }
        });

        txtNgayKT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog(txtNgayKT);
            }
        });
    }

    private void editSale(KhuyenMai khuyenMai) {
        String tenCT = txtTenCT.getText().toString().trim();
        String mota = txtMoTa.getText().toString().trim();
        String ngayBD = txtNgayBD.getText().toString();
        String ngayKT = txtNgayKT.getText().toString();

        int donToiThieu = 0;
        try {
            donToiThieu = Integer.parseInt(txtDonToiThieu.getText().toString());
        } catch (NumberFormatException e) {}

        double giaTriKhuyenMai = 0;
        try {
            giaTriKhuyenMai = Double.parseDouble(txtKhuyenMai.getText().toString());
        } catch (NumberFormatException e) {}

        int giamToiDa = 0;
        try {
            giamToiDa = Integer.parseInt(txtGiamToiDa.getText().toString());
        } catch (NumberFormatException e) {}

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference("KhuyenMai");

        km.setTenKM(tenCT);
        km.setMoTa(mota);
        km.setNgayBD(ngayBD);
        km.setNgayKT(ngayKT);
        km.setDonToiThieu(donToiThieu);
        km.setKhuyenMai(giaTriKhuyenMai);
        km.setGiamToiDa(giamToiDa);

        reference.child(String.valueOf(km.getMaKM())).updateChildren(km.toMap(), new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                showCustomDialogSucess("Cập nhật thông tin chương trình thành công");
                startActivity(new Intent(getApplicationContext(), QLKhuyenMai.class));
            }
        });

    }

    private void getData(String maKM){
        DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("KhuyenMai/" + maKM);

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                KhuyenMai khuyenMai = snapshot.getValue(KhuyenMai.class);
                km = khuyenMai;
                setData(khuyenMai);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("Edit Sale", "Get data erorr");
//                Toast.makeText(getApplicationContext(), "Có lỗi xảy ra!", Toast.LENGTH_SHORT).show();
            }
        });

//        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("KhuyenMai");
//
//        Query query = reference.orderByChild("MaKM").equalTo(maKM);
//
//        query.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                if (snapshot.exists()){
//                    DataSnapshot dataSnapshot = snapshot.getChildren().iterator().next();
//                    KhuyenMai khuyenMai = dataSnapshot.getValue(KhuyenMai.class);
//                    if (khuyenMai != null){
//                        Log.d("get data edit sale", khuyenMai.getTenKM());
//                        setData(khuyenMai);
//                    } else {
////                    // No data found
//                    }
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
////                // error
//
//            }
//        });
//

//        query.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                if (snapshot.exists()){
//                    for (DataSnapshot dataSnapshot : snapshot.getChildren()){
//                        KhuyenMai khuyenMai = snapshot.getValue(KhuyenMai.class);
//                        setData(khuyenMai);
//                    }
//                } else {
//                    // No data found
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//                // error
//            }
//        });
    }

    private void setDataSale(String maKM) {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("KhuyenMai");
        reference.child(maKM).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    String tenCT = snapshot.child("TenKM").getValue(String.class);
                    String moTa = snapshot.child("MoTa").getValue(String.class);
                    String ngayBD = snapshot.child("NgayBD").getValue(String.class);
                    String ngayKT = snapshot.child("NgayKT").getValue(String.class);
                    Integer donToiThieu = snapshot.child("DonToiThieu").getValue(Integer.class);
                    Double giaTriKhuyenMai = snapshot.child("KhuyenMai").getValue(Double.class);
                    Integer giamToiDa = snapshot.child("GiamToiDa").getValue(Integer.class);

                    txtTenCT.setText(tenCT);
                    txtTenCT.setEnabled(false);

                    txtMoTa.setText(moTa);
                    txtNgayBD.setText(ngayBD);
                    txtNgayKT.setText(ngayKT);
                    txtDonToiThieu.setText(String.valueOf(donToiThieu));
                    txtKhuyenMai.setText(String.valueOf(giaTriKhuyenMai));
                    txtGiamToiDa.setText(String.valueOf(giamToiDa));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void setData(KhuyenMai khuyenMai) {
        txtTenCT.setText(khuyenMai.getTenKM());
        txtTenCT.setEnabled(false);

        txtMoTa.setText(khuyenMai.getMoTa());

        txtNgayBD.setText(khuyenMai.getNgayBD());
        txtNgayBD.setEnabled(false);
        txtNgayKT.setText(khuyenMai.getNgayKT());
        txtNgayKT.setEnabled(false);

        txtDonToiThieu.setText(String.valueOf(khuyenMai.getDonToiThieu()));
        txtKhuyenMai.setText(String.valueOf(khuyenMai.getDonToiThieu()));
        txtGiamToiDa.setText(String.valueOf(khuyenMai.getGiamToiDa()));
    }

    private boolean isValidForm(){
        if (isTenCTEmpty()) {
            showCustomDialogFail("Vui lòng nhập vào tên chương trình");
            txtTenCT.setError("Please fill information before next");
            txtTenCT.requestFocus();
            return false;
        } else if (isMoTaEmpty()) {
            showCustomDialogFail("Vui lòng nhập vào mô tả");
            txtMoTa.setError("Please fill information before next");
            txtMoTa.requestFocus();
            return false;
        } else if (isNgayBDEmpty()) {
            showCustomDialogFail("Vui lòng chọn ngày bắt đầu");
            txtNgayBD.setError("Please fill information before next");
            txtNgayBD.requestFocus();
            return false;
        } else if (isNgayKTEmpty()) {
            showCustomDialogFail("Vui lòng chọn ngày kết thúc");
            txtNgayKT.setError("Please fill information before next");
            txtNgayKT.requestFocus();
            return false;
        } else if (isNgayHopLe(txtNgayKT.getText().toString(), txtNgayBD.getText().toString())){
            showCustomDialogFail("Vui lòng chọn ngày kết thúc sau ngày bắt đầu");
            txtNgayKT.setError("Please fill information valid before next");
            txtNgayKT.requestFocus();
            return false;
        } else if (isDonToiThieuEmpty()){
            showCustomDialogFail("Vui lòng nhập vào giá trị đơn hàng được áp dụng tối thiểu");
            txtDonToiThieu.setError("Please fill information before next");
            txtDonToiThieu.requestFocus();
            return false;
        } else if (isKhuyenMaiEmpty()){
            showCustomDialogFail("Vui lòng nhập vào giá trị khuyến mãi");
            txtKhuyenMai.setError("Please fill information before next");
            txtKhuyenMai.requestFocus();
            return false;
        } else if (isGiamToiDaEmpty()) {
            showCustomDialogFail("Vui lòng nhập vào giá trị khuyến mãi tối đa");
            txtGiamToiDa.setError("Please fill information before next");
            txtGiamToiDa.requestFocus();
            return false;
        }
        return true;
    }

    private boolean isTenCTEmpty(){
        return txtTenCT.getText().toString().isEmpty();
    }

    private boolean isMoTaEmpty(){
        return txtMoTa.getText().toString().isEmpty();
    }

    private boolean isNgayBDEmpty(){
        return txtNgayBD.getText().toString().isEmpty();
    }

    private boolean isNgayKTEmpty(){
        return txtNgayKT.getText().toString().isEmpty();
    }

    private boolean isNgayHopLe(String ngayBD, String ngayKT) {
        try {
            dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
            Date date1 = dateFormat.parse(ngayBD);
            Date date2 = dateFormat.parse(ngayKT);

            if (date1.compareTo(date2) < 0) {
                return true;
            } else {
                return false;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return true;
    }

    private boolean isDonToiThieuEmpty(){
        return txtDonToiThieu.getText().toString().isEmpty();
    }

    private boolean isKhuyenMaiEmpty(){
        return txtKhuyenMai.getText().toString().isEmpty();
    }

    private boolean isGiamToiDaEmpty(){
        return txtGiamToiDa.getText().toString().isEmpty();
    }

    private void showDatePickerDialog(final EditText editText) {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog = new DatePickerDialog(EditSale.this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        Calendar selectedCalendar = Calendar.getInstance();
                        selectedCalendar.set(year, month, dayOfMonth);
                        String selectedDate = dateFormat.format(selectedCalendar.getTime());
                        editText.setText(selectedDate);
                    }
                }, year, month, dayOfMonth);
        datePickerDialog.show();
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
        actionBar.setTitle("Cập nhật chương trình");
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_back_white);

//        image_sale = findViewById(R.id.image_sale);
        txtTenCT = findViewById(R.id.txtTenCT);
        txtMoTa = findViewById(R.id.txtMoTa);
        txtNgayBD = findViewById(R.id.txtNgayBD);
        txtNgayKT = findViewById(R.id.txtNgayKT);
        txtDonToiThieu = findViewById(R.id.txtDonToiThieu);
        txtKhuyenMai = findViewById(R.id.txtKhuyenMai);
        txtGiamToiDa = findViewById(R.id.txtGiamToiDa);
        butSaveSale = findViewById(R.id.butSaveSale);
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