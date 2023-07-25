package com.example.se114n21.ViewModels;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.se114n21.Models.ChiTietHoaDon;
import com.example.se114n21.Models.DonNhapHang;
import com.example.se114n21.Models.KhachHang;
import com.example.se114n21.Models.SanPham;
import com.example.se114n21.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DonNhapHangDetail extends AppCompatActivity {
    private ImageButton btnBack, btnReturn;
    private TextView madnh, manv, thoigian, masp, tensp, soluongnhap, soluongtrahang;
    private DonNhapHang donNhapHang = new DonNhapHang();
    private ProgressDialog progressDialog;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_don_nhap_hang_detail);
        getSupportActionBar().hide();
        
        initUI();

        Intent intent = getIntent();
        String MaDNH = intent.getStringExtra("MaDNH");

        getData(MaDNH);
    }

    private void getData(String MaDNH) {
        progressDialog = ProgressDialog.show(DonNhapHangDetail.this,"Đang tải", "Vui lòng đợi...",false,false);
        DatabaseReference myRef = database.getReference("DonNhapHang/" + MaDNH);

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                donNhapHang = snapshot.getValue(DonNhapHang.class);
                setData(donNhapHang);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                progressDialog.dismiss();
                Toast.makeText(DonNhapHangDetail.this, "Có lỗi xảy ra!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setData(DonNhapHang donNhapHang) {
        madnh.setText(donNhapHang.getMaDNH());
        manv.setText(donNhapHang.getMaNV());
        thoigian.setText(donNhapHang.getNgayDNH());
        masp.setText(donNhapHang.getMaSP());
        tensp.setText(donNhapHang.getTenSP());
        soluongnhap.setText(String.valueOf(donNhapHang.getSoLuongNhap()));
        soluongtrahang.setText(String.valueOf(donNhapHang.getSoLuongTraHang()));

        progressDialog.dismiss();
    }

    private void initUI() {
        btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        madnh = findViewById(R.id.madnh);
        manv = findViewById(R.id.manv);
        thoigian = findViewById(R.id.thoigian);
        masp = findViewById(R.id.masp);
        tensp = findViewById(R.id.tensp);
        soluongnhap = findViewById(R.id.soluongnhap);
        soluongtrahang = findViewById(R.id.soluongtrahang);

        btnReturn = findViewById(R.id.btnReturn);
        btnReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDialogReturn(Gravity.CENTER);
            }
        });
    }

    private void openDialogReturn(int gravity) {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.themhoadon_dialog);

        Window window = dialog.getWindow();

        if (window == null) {
            return;
        }

        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        WindowManager.LayoutParams windowAttributes = window.getAttributes();
        windowAttributes.gravity = gravity;
        window.setAttributes(windowAttributes);


        TextView tvDialogTitle = dialog.findViewById(R.id.tv_title_themhoadon_dialog);
        EditText editDialogFill = dialog.findViewById(R.id.edit_name_themhoadon_dialog);
        Button btnCancel = dialog.findViewById(R.id.btn_themhoadon_dialog_cancel);
        Button btnOk = dialog.findViewById(R.id.btn_themhoadon_dialog_ok);

        tvDialogTitle.setText("Hoàn trả sản phẩm");
        editDialogFill.setHint("Số lượng");
        btnCancel.setText("Hủy");
        btnOk.setText("Hoàn trả");

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (editDialogFill.getText().toString().equals("")) {
                    Toast.makeText(DonNhapHangDetail.this, "Nhập số lượng sản phẩm để trả hàng!", Toast.LENGTH_SHORT).show();
                } else {
                    Integer duoctra = donNhapHang.getSoLuongNhap() - donNhapHang.getSoLuongTraHang();
                    Integer trahang = Integer.parseInt(editDialogFill.getText().toString().trim());

                    if (trahang > 0) {
                        if (duoctra >= trahang) {
                            progressDialog = ProgressDialog.show(DonNhapHangDetail.this,"Đang tải", "Vui lòng đợi...",false,false);
                            checkKho(dialog, trahang);
                        } else {
                            Toast.makeText(DonNhapHangDetail.this, "Số lượng có thể trả là " + duoctra.toString(), Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(DonNhapHangDetail.this, "Số lượng phải lớn hơn 0", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        dialog.show();
    }

    private void checkKho(Dialog dialog, Integer trahang) {
        DatabaseReference myRef = database.getReference("listSanPham/" + donNhapHang.getMaSP());

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    SanPham sanPham = snapshot.getValue(SanPham.class);
                    if (sanPham.getSoLuong() >= trahang) {
                        Integer conlai = sanPham.getSoLuong() - trahang;
                        updateSP(dialog, conlai, trahang);
                    } else {
                        Toast.makeText(DonNhapHangDetail.this, "Số lượng trong kho chỉ còn " + sanPham.getSoLuong(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    progressDialog.dismiss();
                    Toast.makeText(DonNhapHangDetail.this, "Sản phẩm không còn tồn tại!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                progressDialog.dismiss();
                Toast.makeText(DonNhapHangDetail.this, "Có lỗi xảy ra!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void updateSP(Dialog dialog, Integer conlai, Integer trahang) {
        DatabaseReference myRef = database.getReference("listSanPham/" + donNhapHang.getMaSP() + "/soLuong");

        myRef.setValue(conlai, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                if (error == null) {
                    updateDNH(dialog, trahang);
                } else {
                    Toast.makeText(DonNhapHangDetail.this, "Có lỗi xảy ra!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void updateDNH(Dialog dialog, Integer trahang) {
        DatabaseReference myRef = database.getReference("DonNhapHang/" + donNhapHang.getMaDNH() + "/soLuongTraHang");

        Integer tongtrahang = trahang + donNhapHang.getSoLuongTraHang();
        
        myRef.setValue(tongtrahang, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                progressDialog.dismiss();
                dialog.dismiss();

                if (error == null) {
                    getData(donNhapHang.getMaDNH());
                    Toast.makeText(DonNhapHangDetail.this, "Hoàn trả thành công!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(DonNhapHangDetail.this, "Có lỗi xảy ra!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}