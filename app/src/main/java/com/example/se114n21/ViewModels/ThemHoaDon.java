package com.example.se114n21.ViewModels;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.app.ActionBar;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.InputFilter;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.se114n21.Adapter.ThemhoadonAdapter;
import com.example.se114n21.Interface.ThemhoadonInterface;
import com.example.se114n21.Interface.ThemhoadonInterface2;
import com.example.se114n21.Models.ChiTietHoaDon;
import com.example.se114n21.Models.KhachHang;
import com.example.se114n21.Models.LoaiSanPham;
import com.example.se114n21.Models.SanPham;
import com.example.se114n21.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ThemHoaDon extends AppCompatActivity {
    private ActivityResultLauncher<Intent> launcher;
    private List<String> mListID = new ArrayList<>();
    private Button btnSelectProduct, btnSelectCustomer;
    private ProgressDialog progressDialog;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();

    private TextView tvTongTienHang, tvPhiVanChuyen, tvPhiLapDat, tvChietKhau, tvTongThanhToan, tvTongThanhToan2;

// RCV
    private RecyclerView recyclerView;
    private List<ChiTietHoaDon> mListCTHD;
    private ThemhoadonAdapter mThemhoadonAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_them_hoa_don);

        InitUI();
    }


    private void InitUI() {
//        progressDialog
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Vui long doi mot chut");
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
//        Action bar
        androidx.appcompat.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Thêm hóa đơn");
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.baseline_arrow_back_ios_24);

//      LAUNCHER
        launcher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == Activity.RESULT_OK) {
                            Intent intent = result.getData();
                            mListID.clear();
                            mListID  = intent.getStringArrayListExtra("listId");

                            if (mListID.size() > 0) {
                                setData();
                                btnSelectProduct.setVisibility(View.GONE);
                            }
                        }
                    }
                });


//        BUTTON SELECT
        btnSelectProduct = findViewById(R.id.btn_select_product);
        btnSelectProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ThemHoaDon.this, ChonSanPham.class);
                launcher.launch(intent);
            }
        });
        
        btnSelectCustomer = findViewById(R.id.btn_themkhachhang);
        
        btnSelectCustomer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(ThemHoaDon.this, "OK", Toast.LENGTH_SHORT).show();
            }
        });


//        RCV
        recyclerView = findViewById(R.id.rcv_product_picked);

        recyclerView.setItemViewCacheSize(10);
        recyclerView.setDrawingCacheEnabled(true);
        recyclerView.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);

        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(itemDecoration);

        mListCTHD = new ArrayList<>();

        mThemhoadonAdapter = new ThemhoadonAdapter(mListCTHD, new ThemhoadonInterface() {
            @Override
            public void onClick(ChiTietHoaDon chiTietHoaDon, int pos) {
                openDialogSoLuong(Gravity.CENTER, chiTietHoaDon, pos);
            }
        }, new ThemhoadonInterface2() {
            @Override
            public void updateButton(int size) {
                setTongTienHang();
                if (size < 1) {
                    btnSelectProduct.setVisibility(View.VISIBLE);
                }
            }
        });

        recyclerView.setAdapter(mThemhoadonAdapter);

//        TEXTVIEW
        tvTongTienHang = findViewById(R.id.tv_tongtienhang);

        
        tvPhiVanChuyen = findViewById(R.id.phivanchuyen);
        tvPhiVanChuyen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OpenDiaLogFees(Gravity.CENTER, "phivanchuyen", tvPhiVanChuyen);
            }
        });




        tvPhiLapDat = findViewById(R.id.philapdat);
        tvPhiLapDat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OpenDiaLogFees(Gravity.CENTER, "philapdat", tvPhiLapDat);
            }
        });




        tvChietKhau = findViewById(R.id.chietkhau);
        tvChietKhau.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OpenDiaLogFees(Gravity.CENTER, "chietkhau", tvChietKhau);
            }
        });

        tvTongThanhToan = findViewById(R.id.tongthanhtoan);
        tvTongThanhToan2 = findViewById(R.id.tongthanhtoan2);
    }

    private void setData() {
        progressDialog.show();

            DatabaseReference myRef = database.getReference("listSanPham");
            myRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        SanPham sanPham = dataSnapshot.getValue(SanPham.class);
                        for (int i=0; i<mListID.size(); i++) {
                            if (sanPham.getMaSP().equals(mListID.get(i))){
                                mListCTHD.add(new ChiTietHoaDon(sanPham, 1));
                            }
                        }
                    }

                    mThemhoadonAdapter.notifyDataSetChanged();
                    progressDialog.dismiss();
                    setTongTienHang();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
    }

    private void OpenDiaLogFees(int gravity, String code, TextView tv) {
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

//        nhan ra ngoai thi tat dialog
//        dialog.setCancelable(true);

        TextView tvDialogTitle = dialog.findViewById(R.id.tv_title_themhoadon_dialog);
        EditText editDialogFill = dialog.findViewById(R.id.edit_name_themhoadon_dialog);
        Button btnCancel = dialog.findViewById(R.id.btn_themhoadon_dialog_cancel);
        Button btnOk = dialog.findViewById(R.id.btn_themhoadon_dialog_ok);

        editDialogFill.setFilters(new InputFilter[] { new InputFilter.LengthFilter(10) });;


        String Title = "Chiết khấu";
        if (code == "phivanchuyen") {
            Title = "Phí vận chuyển";
        } else if (code == "philapdat") {
            Title = "Phí lắp đặt";
        }

        tvDialogTitle.setText(Title);
        editDialogFill.setText(tv.getText().toString().trim());
        editDialogFill.setHint(Title);
        btnCancel.setText("Hủy");
        btnOk.setText("Lưu");

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Integer TienTra = Integer.parseInt(tvTongTienHang.getText().toString().trim()) +
                        Integer.parseInt(tvPhiVanChuyen.getText().toString().trim()) +
                        Integer.parseInt(tvPhiLapDat.getText().toString().trim());

                if (code == "chietkhau" && Integer.parseInt(editDialogFill.getText().toString().trim()) > TienTra) {
                    Toast.makeText(ThemHoaDon.this, "Chiết Khấu không thể lớn hơn Tổng Thanh Toán. Tổng Thanh Toán là " + tvTongThanhToan.getText().toString().trim(), Toast.LENGTH_SHORT).show();
                } else {
                    if (Integer.parseInt(editDialogFill.getText().toString().trim()) == 0) {
                        tv.setText(editDialogFill.getText().toString().trim());
                    } else {
                        tv.setText(editDialogFill.getText().toString().trim());
                    }

                    setTongThanhToan();

                    dialog.dismiss();
                }
            }
        });

        dialog.show();
    }

    private void openDialogSoLuong(int gravity, ChiTietHoaDon chiTietHoaDon, int pos) {
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

//        nhan ra ngoai thi tat dialog
//        dialog.setCancelable(true);

        TextView tvDialogTitle = dialog.findViewById(R.id.tv_title_themhoadon_dialog);
        EditText editDialogFill = dialog.findViewById(R.id.edit_name_themhoadon_dialog);
        Button btnCancel = dialog.findViewById(R.id.btn_themhoadon_dialog_cancel);
        Button btnOk = dialog.findViewById(R.id.btn_themhoadon_dialog_ok);

        tvDialogTitle.setText("Cập nhật số lượng");
        editDialogFill.setText(chiTietHoaDon.getSoLuong().toString());
        editDialogFill.setHint(chiTietHoaDon.getSanPham().getTenSP());
        btnCancel.setText("Hủy");
        btnOk.setText("Cập nhật");

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


//                UPDATE
                String soluong = editDialogFill.getText().toString();
                
                if (soluong.isEmpty()) {
                    Toast.makeText(ThemHoaDon.this, "Số lượng không được bỏ trống", Toast.LENGTH_SHORT).show();
                } else 
                    if (Integer.parseInt(soluong) <= 0) {
                        Toast.makeText(ThemHoaDon.this, "Số lượng không thể bằng 0", Toast.LENGTH_SHORT).show();
                    } else 
                        if (Integer.parseInt(soluong) > chiTietHoaDon.getSanPham().getSoLuong()) {
                            Toast.makeText(ThemHoaDon.this, "Còn " + chiTietHoaDon.getSanPham().getSoLuong().toString() + " sản phẩm trong kho", Toast.LENGTH_SHORT).show();
                        } else {
                            dialog.dismiss();
                            progressDialog.show();

                            chiTietHoaDon.setSoLuong(Integer.parseInt(soluong));
                            chiTietHoaDon.setThanhTien(Integer.parseInt(soluong) * chiTietHoaDon.getSanPham().getGiaBan());

                            mListCTHD.set(pos, chiTietHoaDon);
                            mThemhoadonAdapter.notifyItemChanged(pos);

                            progressDialog.dismiss();
                            setTongTienHang();
                        }
            }
        });

        dialog.show();
    }

    private void setTongTienHang() {
        Integer tong = mThemhoadonAdapter.getTongTienHang();
        tvTongTienHang.setText(tong.toString());

        setTongThanhToan();
    }

    private void setTongThanhToan() {
        Integer tong = Integer.parseInt(tvTongTienHang.getText().toString().trim()) +
                       Integer.parseInt(tvPhiVanChuyen.getText().toString().trim()) +
                       Integer.parseInt(tvPhiLapDat.getText().toString().trim()) -
                       Integer.parseInt(tvChietKhau.getText().toString().trim());
        tvTongThanhToan.setText(tong.toString());
        tvTongThanhToan2.setText(tong.toString());
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId())
        {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}