package com.example.se114n21.ViewModels;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.app.ActionBar;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Interpolator;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.InputFilter;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.se114n21.Adapter.ThemhoadonAdapter;
import com.example.se114n21.Interface.ThemhoadonInterface;
import com.example.se114n21.Interface.ThemhoadonInterface2;
import com.example.se114n21.Models.ChiTietHoaDon;
import com.example.se114n21.Models.HoaDon;
import com.example.se114n21.Models.IdGenerator;
import com.example.se114n21.Models.KhachHang;
import com.example.se114n21.Models.LoaiSanPham;
import com.example.se114n21.Models.NhanVien;
import com.example.se114n21.Models.SanPham;
import com.example.se114n21.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.net.Inet4Address;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ThemHoaDon extends AppCompatActivity {
    private ImageButton btnBack;
    private List<String> mListID = new ArrayList<>();
    private Button btnSelectProduct, btnSelectCustomer, btnPurchase, btnSelectKhuyenMai, btnClearKhuyenMai;
    private RadioGroup radioGroup;
    private ProgressDialog progressDialog;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();

    private TextView tvTongTienHang, tvPhiVanChuyen, tvPhiLapDat, tvChietKhau, tvTongThanhToan, tvTongThanhToan2,
                        tvMaKH, tvTenKH, tvDienThoaiKH, tenKM, phantramKM, giamtoidaKM, dontoithieuKM, khuyenmai;
    private EditText editPhoneNhanHang, editDiaChiNhanHang, editNote;

// RCV
    private RecyclerView recyclerView;
    private List<ChiTietHoaDon> mListCTHD;
    private ThemhoadonAdapter mThemhoadonAdapter;

//    LAUNCHER
    private ActivityResultLauncher<Intent> SelectCustomerLauncher;
    private ActivityResultLauncher<Intent> launcher;
    private ActivityResultLauncher<Intent> SelectKhuyenMaiLauncher;

//  KM
    int PHANTRAM, GIAMTOIDA, DONTOITHIEU;

    SharedPreferences sharedPreferences;
    private static final String SHARED_PREF_NAME = "mypre";
    private static final String KEY_ID = "id";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_them_hoa_don);
        getSupportActionBar().hide();

        sharedPreferences = getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);

        InitUI();
    }


    //    CLEAR FOCUS ON EDIT TEXT
    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (v instanceof EditText) {
                Rect outRect = new Rect();
                v.getGlobalVisibleRect(outRect);
                if (!outRect.contains((int)event.getRawX(), (int)event.getRawY())) {
                    v.clearFocus();
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
        }
        return super.dispatchTouchEvent(event);
    }



    private void InitUI() {
        btnBack = findViewById(R.id.btnBack_ThemHoaDon);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

//        EDIT TEXT
        editPhoneNhanHang = findViewById(R.id.phone_nhanhang);
        editDiaChiNhanHang = findViewById(R.id.diachinhanhang);
        editNote = findViewById(R.id.ghichu_donhang);

//        RADIO GROUP PAYMENT
        radioGroup = findViewById(R.id.radio_group_payment);

////        BUTTON PAYMENT
//        btnTienMat = findViewById(R.id.btn_tienmat);
//        btnChuyenKhoan = findViewById(R.id.btn_chuyenkhoan);
//        btnQuetThe = findViewById(R.id.btn_quetthe);

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

//        SELECT CUSTOMER LAUNCHER
        SelectCustomerLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == Activity.RESULT_OK) {
                            Intent intent = result.getData();
                            String ID = intent.getStringExtra("CUS_ID");
                            String NAME = intent.getStringExtra("CUS_NAME");
                            String PHONE = intent.getStringExtra("CUS_PHONE");

                            tvMaKH.setText(ID);
                            tvTenKH.setText(NAME);
                            tvDienThoaiKH.setText(PHONE);

                        }
                    }
                });

//        SELECT KHUYEN MAI LAUNCHER
        SelectKhuyenMaiLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == Activity.RESULT_OK) {
                            Intent intent = result.getData();
                            String NAME = intent.getStringExtra("KM_TEN");
                            PHANTRAM = (int) intent.getDoubleExtra("KM_PHANTRAM", 0);
                            GIAMTOIDA = (int) intent.getIntExtra("KM_GIAMTOIDA", 0);
                            DONTOITHIEU = (int) intent.getIntExtra("KM_DONTOITHIEU", 0);

                            tenKM.setText(NAME);
                            phantramKM.setText(String.valueOf(PHANTRAM));
                            giamtoidaKM.setText(String.valueOf(GIAMTOIDA));
                            dontoithieuKM.setText(String.valueOf(DONTOITHIEU));

                            setKhuyenMai();
                        }
                    }
                });


//        BUTTON SELECT PRODUCT
        btnSelectProduct = findViewById(R.id.btn_select_product);
        btnSelectProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ThemHoaDon.this, ChonSanPham.class);
                launcher.launch(intent);
            }
        });

//        BUTTON SELECT CUSTOMER
        btnSelectCustomer = findViewById(R.id.btn_themkhachhang);
        btnSelectCustomer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ThemHoaDon.this, SelectCustomer.class);
                SelectCustomerLauncher.launch(intent);
            }
        });

//        BUTTON SELECT KHUYEN MAI
        btnSelectKhuyenMai = findViewById(R.id.btn_themkhuyenmai);
        btnSelectKhuyenMai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ThemHoaDon.this, SelectKhuyenMai.class);
                SelectKhuyenMaiLauncher.launch(intent);
            }
        });

//        BUTTON PURCHASE
        btnPurchase = findViewById(R.id.btn_thanhtoan);
        btnPurchase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkBill() == true) {
                    if (khuyenmai.getText().toString().equals("0") && tenKM.getText().toString().length() > 0) {
                        Toast.makeText(ThemHoaDon.this, "Không thể áp dụng khuyến mãi do chưa đạt đến số tiền hàng tối thiểu!", Toast.LENGTH_SHORT).show();
                    } else {
                        progressDialog = ProgressDialog.show(ThemHoaDon.this,"Đang tải", "Vui lòng đợi...",false,false);
                        getMaxId();
                    }
                }
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

        tvMaKH = findViewById(R.id.makhachhang);
        tvTenKH = findViewById(R.id.tenkhachhang);
        tvDienThoaiKH = findViewById(R.id.sodienthoaikhachhang);

        tenKM = findViewById(R.id.tenkhuyenmai);
        phantramKM = findViewById(R.id.phantram);
        giamtoidaKM = findViewById(R.id.giamtoida);
        dontoithieuKM = findViewById(R.id.dontoithieu);
        khuyenmai = findViewById(R.id.khuyenmai);

        btnClearKhuyenMai = findViewById(R.id.btn_clearkhuyenmai);
        btnClearKhuyenMai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tenKM.setText("");
                phantramKM.setText("");
                giamtoidaKM.setText("");
                dontoithieuKM.setText("");
                khuyenmai.setText("0");
                setTongThanhToan();
            }
        });
    }

    boolean save = false;
    private void getMaxId() {
        save = false;
        DatabaseReference myRef = database.getReference("maxHoaDon");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (save == false) {
                    String maHD = snapshot.getValue(String.class);
                    Integer maxId = Integer.parseInt(maHD.substring(3));
                    PrepareBill(maxId);
                    save = true;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(ThemHoaDon.this, "Có lỗi xảy ra!", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });
    }

    private String createID(Integer maxId) {
        IdGenerator generator = new IdGenerator();
        generator.init("HD", "", maxId, "%04d");

        return generator.generate();
    }

    private void PrepareBill(Integer maxId) {
        HoaDon hoaDon = new HoaDon();

        hoaDon.setMaHD(createID(maxId));

        hoaDon.setKhuyenMai(Integer.parseInt(khuyenmai.getText().toString().trim()));


        hoaDon.setMaKH(tvMaKH.getText().toString().trim());
        hoaDon.setTenKH(tvTenKH.getText().toString().trim());
        hoaDon.setSoDienThoaiKH(tvDienThoaiKH.getText().toString().trim());

        if (tvMaKH.getText().toString().trim().equals("Khách lẻ")) {
            hoaDon.setTenKH(tvMaKH.getText().toString().trim());
        }

//      DATE
        Date currentTime = Calendar.getInstance().getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm dd/MM/yyyy");
        hoaDon.setNgayHD(sdf.format(currentTime));

        hoaDon.setDienThoaiNhanHang(editPhoneNhanHang.getText().toString().trim());
        hoaDon.setDiaCHiNhanHang(editDiaChiNhanHang.getText().toString().trim());

        hoaDon.setTongTienHang(Integer.parseInt(tvTongTienHang.getText().toString().trim()));
        hoaDon.setPhiVanChuyen(Integer.parseInt(tvPhiVanChuyen.getText().toString().trim()));
        hoaDon.setPhiLapDat(Integer.parseInt(tvPhiLapDat.getText().toString().trim()));
        hoaDon.setChietKhau(Integer.parseInt(tvChietKhau.getText().toString().trim()));

        hoaDon.setTongTienPhaiTra(Integer.parseInt(tvTongThanhToan.getText().toString().trim()));

        hoaDon.setGhiChu(editNote.getText().toString().trim());

        RadioButton radioButton = findViewById(radioGroup.getCheckedRadioButtonId());
        hoaDon.setPhuongThucThanhToan(radioButton.getText().toString());

        hoaDon.setChiTietHD(mListCTHD);

//        TIEN VON
        Integer tong = 0;
        for (int i=0; i<mListCTHD.size(); i++) {
            tong += mListCTHD.get(i).getTienVon();
        }
        hoaDon.setTienVon(tong);

        setMaNV(hoaDon);
    }

    private void setMaNV(HoaDon hoaDon) {
        String MaND = sharedPreferences.getString(KEY_ID, null);
        DatabaseReference myRef = database.getReference("Users/" + MaND);
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                NhanVien nhanVien = snapshot.getValue(NhanVien.class);
                if (nhanVien.getLoaiNhanVien().equals("Admin")) {
                    hoaDon.setMaNV("Admin - " + nhanVien.getHoTen());
                } else {
                    hoaDon.setMaNV(nhanVien.getMaNV() + " - " + nhanVien.getHoTen());
                }

                addBill(hoaDon);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                progressDialog.dismiss();
                Toast.makeText(ThemHoaDon.this, "Có lỗi xảy ra!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void addBill(HoaDon hoaDon) {
        DatabaseReference myRef = database.getReference("listHoaDon");
        myRef.child(hoaDon.getMaHD()).setValue(hoaDon, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                updateMaxId(hoaDon);
            }
        });
    }

    private void updateMaxId(HoaDon hoaDon) {
        DatabaseReference myRef = database.getReference("maxHoaDon");
        myRef.setValue(hoaDon.getMaHD(), new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                for(int i=0; i<mListCTHD.size(); i++) {
                    String path = "listSanPham/" + mListCTHD.get(i).getSanPham().getMaSP();
                    DatabaseReference myRef = database.getReference(path);

                    Map<String, Object> map = new HashMap<>();
                    map.put("soLuong", mListCTHD.get(i).getSanPham().getSoLuong() - mListCTHD.get(i).getSoLuong());


                    myRef.updateChildren(map, new DatabaseReference.CompletionListener() {
                        @Override
                        public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                        }
                    });
                }

                progressDialog.dismiss();

                Toast.makeText(ThemHoaDon.this, "Thêm hóa đơn thành công!", Toast.LENGTH_SHORT).show();
                onBackPressed();

            }
        });
    }

    private boolean checkBill() {

        if (mThemhoadonAdapter.getItemCount() == 0) {
            Toast.makeText(this, "Chưa chọn sản phẩm", Toast.LENGTH_SHORT).show();
            return false;
        } else
        if (tvMaKH.getText().toString().trim().equals("")){
            Toast.makeText(this, "Chưa thêm khách hàng", Toast.LENGTH_SHORT).show();
            return false;
        } else
        if (radioGroup.getCheckedRadioButtonId() == -1) {
            Toast.makeText(this, "Chưa chọn phương thức thanh toán", Toast.LENGTH_SHORT).show();
            return false;
        }
        
        return true;
    }

    private void setData() {
        progressDialog = ProgressDialog.show(ThemHoaDon.this,"Đang tải", "Vui lòng đợi...",false,false);

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
                            progressDialog = ProgressDialog.show(ThemHoaDon.this,"Đang tải", "Vui lòng đợi...",false,false);

                            chiTietHoaDon.setSoLuong(Integer.parseInt(soluong));
                            chiTietHoaDon.setThanhTien(Integer.parseInt(soluong) * chiTietHoaDon.getSanPham().getGiaBan());
                            chiTietHoaDon.setTienVon(Integer.parseInt(soluong) * chiTietHoaDon.getSanPham().getGiaNhap());

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

        setKhuyenMai();
    }

    private void setKhuyenMai() {
        if (tenKM.getText().toString().equals("")) {
            setTongThanhToan();
        } else {
            Integer tienhang = Integer.parseInt(tvTongTienHang.getText().toString().trim());
            if (tienhang >= DONTOITHIEU) {
                Integer sotiengiam = tienhang * PHANTRAM / 100;
                if (sotiengiam > GIAMTOIDA) {
                    sotiengiam = GIAMTOIDA;
                }
                khuyenmai.setText(String.valueOf(sotiengiam));
            } else {
                Toast.makeText(this, "Chưa đạt số tiền hàng tối thiểu!", Toast.LENGTH_SHORT).show();
            }
            setTongThanhToan();
        }
    }

    private void setTongThanhToan() {
        Integer tong = Integer.parseInt(tvTongTienHang.getText().toString().trim()) +
                       Integer.parseInt(tvPhiVanChuyen.getText().toString().trim()) +
                       Integer.parseInt(tvPhiLapDat.getText().toString().trim()) -
                       Integer.parseInt(tvChietKhau.getText().toString().trim()) -
                       Integer.parseInt(khuyenmai.getText().toString().trim());
        tvTongThanhToan.setText(tong.toString());
        tvTongThanhToan2.setText(tong.toString());
    }
}