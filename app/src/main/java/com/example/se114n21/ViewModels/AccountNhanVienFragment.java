package com.example.se114n21.ViewModels;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.se114n21.Models.NhanVien;
import com.example.se114n21.R;

public class AccountNhanVienFragment extends Fragment {

    private Button btnAccount, btnSetting;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initUI();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_account_nhan_vien, container, false);
    }

    private void initUI() {
        btnAccount = (Button) getView().findViewById(R.id.btn_account_nhan_vien);
        btnSetting = (Button) getView().findViewById(R.id.btn_setting_nhan_vien);

        btnAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), Account.class);
                startActivity(intent);
            }
        });

        btnSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "Chuyển qua cài đặt đổi mật khẩu đồ đó", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
