package com.example.se114n21.ViewModels;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.se114n21.R;

import com.example.se114n21.reports.reportContainer;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent rpIntent = new Intent(MainActivity.this, reportContainer.class);
        startActivity(rpIntent);
    }

}