package com.example.se114n21;

import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import com.example.se114n21.reports.reportContainer;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // comment

        Intent rpIntent = new Intent(MainActivity.this, reportContainer.class);
        startActivity(rpIntent);


    }
}