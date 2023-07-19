package com.example.se114n21.ViewModels;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.example.se114n21.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class BottomNavigation extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom_navigation);

        getSupportActionBar().hide();

        initUI();

        replaceFragment(new BaoCaoFragment());
        bottomNavigationView.setBackground(null);

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.baocao_nav:
                        replaceFragment(new BaoCaoFragment());
                        break;

                    case R.id.donhang_nav:
                        replaceFragment(new DonHangFragment());
                        break;

                    case R.id.sanpham_nav:
                        replaceFragment(new SanPhamFragment());
                        break;

                    case R.id.account_nav:
                        replaceFragment(new AccountFragment());
                        break;
                }

                return true;
            }
        });
    }
    private void initUI() {
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout, fragment);
        fragmentTransaction.commit();
    }
}