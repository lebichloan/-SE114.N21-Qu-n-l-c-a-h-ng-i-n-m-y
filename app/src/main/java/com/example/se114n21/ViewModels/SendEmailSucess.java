package com.example.se114n21.ViewModels;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import com.example.se114n21.R;

public class SendEmailSucess extends AppCompatActivity {

    Button butBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_email_sucess);

        butBack = findViewById(R.id.butBack);

        butBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SendEmailSucess.this, Login.class));
                finish();
            }
        });
    }
}