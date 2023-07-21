package com.example.se114n21.ViewModels;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.app.TaskInfo;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.se114n21.Models.NhanVien;
import com.example.se114n21.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import de.hdodenhof.circleimageview.CircleImageView;
import me.zhanghai.android.materialprogressbar.CircularProgressDrawable;

public class AccountFragment extends Fragment {
    SharedPreferences sharedPreferences;
    private static final String SHARED_PREF_NAME = "mypre";
    private static final String KEY_ID = "id";

    private Button btnAccount, btnChangePassword, btnListNhanVien, btnLogout;
    private ProgressDialog progressDialog;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private TextView name, email, role, id, birthday, phone, address;
    private CircleImageView circleImageView;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        sharedPreferences = this.getActivity().getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);

        initUI();

        String keyid = sharedPreferences.getString(KEY_ID, null);
        getData(keyid);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_account, container, false);
    }

    private void getData(String MaND) {
        progressDialog = ProgressDialog.show(getActivity(),"Đang tải", "Vui lòng đợi...",false,false);
        DatabaseReference myRef = database.getReference("Users/" + MaND);

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                NhanVien nhanVien = snapshot.getValue(NhanVien.class);
                setData(nhanVien);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                progressDialog.dismiss();
                Toast.makeText(getActivity(), "Có lỗi xảy ra!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setData(NhanVien nhanVien) {
        Glide.with(this)
                .load(nhanVien.getLinkAvt()) // image url
                .placeholder(R.drawable.ic_launcher_background) // any placeholder to load at start
                .error(R.mipmap.ic_launcher)  // any image in case of error
                .into(circleImageView);

        name.setText(nhanVien.getHoTen());
        email.setText(nhanVien.getEmail());
        role.setText(nhanVien.getLoaiNhanVien());
        if (nhanVien.getLoaiNhanVien().equals("Admin")) {
            id.setText("--");
        } else {
            id.setText(nhanVien.getMaNV());
        }
        birthday.setText(nhanVien.getNgaySinh());
        phone.setText(nhanVien.getSDT());
        address.setText(nhanVien.getDiaChi());

        progressDialog.dismiss();
    }

    private void initUI() {
//        LOG OUT
        btnLogout = (Button) getView().findViewById(R.id.btn_logout_account_profile);
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(getActivity())
                        .setTitle("Đăng xuất")
                        .setMessage("Bạn có chắc chắn đăng xuất khỏi tài khoản này không?")
                        .setPositiveButton("Đăng xuất", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.clear();
                                editor.commit();

                                startActivity(new Intent(getActivity(), Login.class));
                                getActivity().finish();
                            }
                        })
                        .setNegativeButton("Hủy", null)
                        .show();
            }
        });

//        TEXTVIEW
        name = (TextView) getView().findViewById(R.id.tv_name_account_profile);
        email = (TextView) getView().findViewById(R.id.tv_email_account_profile);
        role = (TextView) getView().findViewById(R.id.tv_role_account_profile);
        id = (TextView) getView().findViewById(R.id.tv_id_account_profile);
        birthday = (TextView) getView().findViewById(R.id.tv_birthday_account_profile);
        phone = (TextView) getView().findViewById(R.id.tv_phone_account_profile);
        address = (TextView) getView().findViewById(R.id.tv_address_account_profile);

//        AVT
        circleImageView = (CircleImageView) getView().findViewById(R.id.avt_account_profile);

        btnAccount = (Button) getView().findViewById(R.id.btn_account);
        btnListNhanVien = (Button) getView().findViewById(R.id.btn_list_nhan_vien);
        btnChangePassword = (Button) getView().findViewById(R.id.btn_changepassword);

//        btnAccount.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(getActivity(), Account.class);
//                startActivity(intent);
//            }
//        });

        btnListNhanVien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), ListStaff.class);
                startActivity(intent);
            }
        });

        btnChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), DoiMatKhau.class));
            }
        });
    }
}