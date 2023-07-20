package com.example.se114n21.ViewModels;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.se114n21.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_sale);

        initUI();

        Intent intent = getIntent();
        if (intent != null){
            String maKM = intent.getStringExtra("maKM");
        }

        butSaveSale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isValidForm()){
                    editSale();
                }
            }
        });

    }

    private void editSale() {

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

    private void initUI() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Cập nhật chương trình");
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_back_white);

        image_sale = findViewById(R.id.image_sale);
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