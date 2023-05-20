package com.example.se114n21;

import android.content.Intent;
import android.support.v4.app.SupportActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.se114n21.reports.report_activity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // comment

        Intent rpIntent = new Intent(MainActivity.this, report_activity.class);
        startActivity(rpIntent);


    }
}