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

public class AddSale extends AppCompatActivity {

    ImageView image_sale;
    Uri uri;
    EditText txtTenCT;
    EditText txtMoTa;
    EditText txtNgayBD;
    EditText txtNgayKT;
    EditText txtDonToiThieu;
    EditText txtKhuyenMai;
    EditText txtGiamToiDa;
    Button butAddSale;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_sale);

        initUI();

        butAddSale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addSale();
            }
        });
    }

    private void addSale() {

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
        actionBar.setTitle("Thêm chương trình mới");
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
        butAddSale = findViewById(R.id.butAddSale);
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