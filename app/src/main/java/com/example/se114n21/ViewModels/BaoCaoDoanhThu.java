package com.example.se114n21.ViewModels;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.se114n21.Adapter.BaoCaoAdapter;
import com.example.se114n21.Adapter.DNHAdapter;
import com.example.se114n21.Interface.BaoCaoInterface;
import com.example.se114n21.Interface.DNHInterface;
import com.example.se114n21.Models.DonNhapHang;
import com.example.se114n21.Models.HoaDon;
import com.example.se114n21.Models.ItemBaoCao;
import com.example.se114n21.Models.KhuyenMai;
import com.example.se114n21.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class BaoCaoDoanhThu extends AppCompatActivity {
    private ImageButton btnBack;
    private EditText batdau, ketthuc;
    private Button btnThongKe;
    private TextView doanhthu, sodonhang;
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
    private Calendar cal1 = Calendar.getInstance();
    private Calendar cal2 = Calendar.getInstance();

    private RecyclerView recyclerView;
    private List<HoaDon> DSHoaDon;
    private List<ItemBaoCao> mListBaoCao;
    private BaoCaoAdapter mBaoCaoAdapter;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bao_cao_doanh_thu);
        getSupportActionBar().hide();

        initUI();
    }

    private void initUI() {
        btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        batdau = findViewById(R.id.batdau);
        batdau.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(BaoCaoDoanhThu.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        cal1.set(i,i1,i2);
                        batdau.setText(simpleDateFormat.format(cal1.getTime()));
                    }
                }, cal1.get(Calendar.YEAR), cal1.get(Calendar.MONTH), cal1.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.getDatePicker().setMaxDate(new Date().getTime());
                datePickerDialog.show();
            }
        });

        ketthuc = findViewById(R.id.ketthuc);
        ketthuc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(BaoCaoDoanhThu.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        cal2.set(i,i1,i2);
                        ketthuc.setText(simpleDateFormat.format(cal2.getTime()));
                    }
                }, cal2.get(Calendar.YEAR), cal2.get(Calendar.MONTH), cal2.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.getDatePicker().setMaxDate(new Date().getTime());
                datePickerDialog.show();
            }
        });

        recyclerView = findViewById(R.id.rcv);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);

        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(itemDecoration);

        mListBaoCao = new ArrayList<>();
        DSHoaDon = new ArrayList<>();

        mBaoCaoAdapter = new BaoCaoAdapter(mListBaoCao, new BaoCaoInterface() {
            @Override
            public void onClick(ItemBaoCao itemBaoCao) {
                Intent intent = new Intent(BaoCaoDoanhThu.this, DoanhThuDetail.class);
                intent.putExtra("NGAYHD", itemBaoCao.getNgay());
                startActivity(intent);
            }
        });

        recyclerView.setAdapter(mBaoCaoAdapter);


//

        doanhthu = findViewById(R.id.doanhthu);
        sodonhang = findViewById(R.id.sodonhang);

        btnThongKe = findViewById(R.id.btnThongKe);
        btnThongKe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                thongKe();
            }
        });
    }

    private void thongKe() {
        if (batdau.getText().toString().equals("") || ketthuc.getText().toString().equals("")) {
            Toast.makeText(this, "Vui lòng chọn ngày cần thống kê!", Toast.LENGTH_SHORT).show();
        } else {
            if (batdau.getText().toString().trim().compareTo(ketthuc.getText().toString().trim()) <= 0) {
                progressDialog = ProgressDialog.show(BaoCaoDoanhThu.this,"Đang tải", "Vui lòng đợi...",false,false);
                getListHD();
            } else {
                Toast.makeText(this, "Thời gian bắt đầu không thể trễ hơn thời gian kết thúc!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void getListHD() {
        DatabaseReference myRef = database.getReference("listHoaDon");

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (mListBaoCao != null) {
                    mListBaoCao.clear();
                }

                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    HoaDon hoaDon = dataSnapshot.getValue(HoaDon.class);

                    try {
                        Date ngayHD = new SimpleDateFormat("HH:mm dd/MM/yyyy").parse(hoaDon.getNgayHD());
                        String SngayHD = new SimpleDateFormat("dd/MM/yyyy").format(ngayHD);

                        if (SngayHD.compareTo(batdau.getText().toString().trim()) >= 0 && SngayHD.compareTo(ketthuc.getText().toString().trim()) <= 0) {
                            ItemBaoCao itemBaoCao = checkNgay(SngayHD);
                            if (itemBaoCao != null) {
                                itemBaoCao.addHoaDon(hoaDon);
                            } else {
                                ItemBaoCao newItem = new ItemBaoCao();
                                newItem.setNgay(SngayHD);
                                newItem.addHoaDon(hoaDon);

                                mListBaoCao.add(newItem);
                            }
                        }
                    } catch (ParseException e) {
                        throw new RuntimeException(e);
                    }
                }

                mBaoCaoAdapter.notifyDataSetChanged();

                setData();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                progressDialog.dismiss();
                Toast.makeText(BaoCaoDoanhThu.this, "Có lỗi xảy ra!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private ItemBaoCao checkNgay(String ngay) {
        for (int i=0; i<mListBaoCao.size(); i++) {
            if (ngay.equals(mListBaoCao.get(i).getNgay())) {
                return mListBaoCao.get(i);
            }
        }
        return null;
    }

    private void setData() {
        Integer tong = 0;
        Integer tongdon = 0;
        for (int i=0; i<mListBaoCao.size(); i++) {
            tong = tong + mListBaoCao.get(i).getTongNgay();
            tongdon = tongdon + mListBaoCao.get(i).getSoLuongNgay();
        }

        doanhthu.setText(tong.toString());
        sodonhang.setText(tongdon.toString());

        progressDialog.dismiss();
    }
}