package com.example.se114n21.ViewModels;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.se114n21.R;

public class EnterOTP extends AppCompatActivity {

    private EditText editOTP;
    private Button butSendOTP;
    private TextView txtSendOTP;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_otp);

        setTitleToolbar();
        initUI();
        butSendOTP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String strOTP = editOTP.getText().toString().trim();
                onClickSendOTP(strOTP);
            }
        });

        txtSendOTP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickSendOTPAgain();
            }
        });
    }

    private void onClickSendOTPAgain() {
    }

    private void onClickSendOTP(String strOTP) {

    }

    private void setTitleToolbar() {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Enter OTP");
        }
    }


    private void initUI() {
        editOTP = findViewById(R.id.edit_OTP);
        butSendOTP = findViewById(R.id.but_send_OTP);
        txtSendOTP = findViewById(R.id.txt_send_OTP);
    }

}