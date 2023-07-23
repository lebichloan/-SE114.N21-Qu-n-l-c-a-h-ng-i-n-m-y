package com.example.se114n21.ViewModels;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.se114n21.Adapter.ImageSliderAdapter;
import com.example.se114n21.Adapter.PropertyAdapter;
import com.example.se114n21.Models.SanPham;
import com.example.se114n21.Models.ThuocTinh;
import com.example.se114n21.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class DetailProduct extends AppCompatActivity {
    private SliderView sliderView;
    private TextView tvId, tvName, tvMGF, tvBrand, tvType, tvDesc, tvRetail, tvCost, tvStock, tvCommission;
    private Button btnDelete;
    private String ID;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private List<String> mListImageSlider;
    private ImageSliderAdapter mImageSliderAdapter;
    private ProgressDialog progressDialog;
    private SanPham sanPham;
    private ActivityResultLauncher<Intent> launcher;

//    RCV PROPERTY
    private RecyclerView rcvProperty;
    private List<ThuocTinh> mListThuocTinh;
    private PropertyAdapter mPropertyAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_product);

        initUI();

        Intent i = getIntent();
        ID = i.getStringExtra("ID");

        getData();
    }

    private void initUI() {
//        RCV
        rcvProperty = findViewById(R.id.rcv_property_detail_product);

        rcvProperty.setItemViewCacheSize(5);
        rcvProperty.setDrawingCacheEnabled(true);
        rcvProperty.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);


        LinearLayoutManager linearLayoutManagerThuocTinh = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        rcvProperty.setLayoutManager(linearLayoutManagerThuocTinh);

        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        rcvProperty.addItemDecoration(itemDecoration);

        mListThuocTinh = new ArrayList<>();

        mPropertyAdapter = new PropertyAdapter(mListThuocTinh, "detail");

        rcvProperty.setAdapter(mPropertyAdapter);


//        PROGRESS DIALOG
        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setMessage("Please wait!");

//        ACTION BAR
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Chi tiết sản phẩm");
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_back_white);
//
        tvId = findViewById(R.id.tv_id_product_detail);
        tvName = findViewById(R.id.tv_name_product_detail);
        tvMGF = findViewById(R.id.tv_mgf_product_detail);
        tvBrand = findViewById(R.id.tv_brand_product_detail);
        tvType = findViewById(R.id.tv_type_product_detail);
        tvDesc = findViewById(R.id.tv_desc_product_detail);
        tvRetail = findViewById(R.id.tv_retail_product_detail);
        tvCost = findViewById(R.id.tv_cost_product_detail);
        tvStock = findViewById(R.id.tv_stock_product_detail);
        tvCommission = findViewById(R.id.tv_commission_product_detail);
//        DELETE
        btnDelete = findViewById(R.id.btn_delete_product_detail);
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                deleteProduct();
                showCustomDialogConfirm("Bạn muốn xóa sản phẩm đã chọn ? ");
            }
        });


//        SLIDER VIEW
        sliderView = findViewById(R.id.sliderview_product_detail);

        sliderView.setIndicatorAnimation(IndicatorAnimationType.FILL);
        sliderView.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);

        mListImageSlider = new ArrayList<>();

        mImageSliderAdapter = new ImageSliderAdapter(this, mListImageSlider);

        sliderView.setSliderAdapter(mImageSliderAdapter);


//        LAUNCHER
        launcher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == Activity.RESULT_OK) {
                            getData();
                        }
                    }
                });
    }

    private void getData() {
        mListImageSlider.clear();
        mListThuocTinh.clear();
        progressDialog.show();
        DatabaseReference myRef = database.getReference("listSanPham/" + ID);

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                sanPham = new SanPham();
                sanPham = snapshot.getValue(SanPham.class);
                setData(sanPham);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void setData(SanPham sanPham) {
        tvId.setText(sanPham.getMaSP());
        tvName.setText(sanPham.getTenSP());
        tvMGF.setText(sanPham.getNamSX().toString());
        tvBrand.setText(sanPham.getThuongHieu());

        tvDesc.setText(sanPham.getMota());

        tvRetail.setText(sanPham.getGiaBan().toString());
        tvCost.setText(sanPham.getGiaNhap().toString());

        tvStock.setText(sanPham.getSoLuong().toString());

        tvCommission.setText(sanPham.getHoaHong().toString());

        mListImageSlider.addAll(sanPham.getLinkAnhSP());
        mImageSliderAdapter.notifyDataSetChanged();

        mListThuocTinh.addAll(sanPham.getDSThuocTinh());
        mPropertyAdapter.notifyDataSetChanged();

        progressDialog.dismiss();
    }

    private void deleteProduct() {
        new AlertDialog.Builder(DetailProduct.this)
                .setTitle("Xoa san pham?")
                .setMessage("Ban co chac chan muon xoa san pham nay hay khong?")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        progressDialog.show();

                        deleteImage(sanPham.getLinkAnhSP());

                        String path = "listSanPham/" + ID;

                        DatabaseReference myRef = database.getReference(path);

                        myRef.removeValue(new DatabaseReference.CompletionListener() {
                            @Override
                            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                                progressDialog.dismiss();
                                Intent intent = new Intent(DetailProduct.this, ListProduct.class);
                                setResult(RESULT_OK, intent);
                                finish();
                            }
                        });
                    }
                })
                .setNegativeButton("Cancel", null)
                .show();
    }

    private void showCustomDialogConfirm(String data){
        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_confirm, null);
        builder.setView(dialogView);
        Dialog dialog = builder.create();
        TextView txtContent = dialogView.findViewById(R.id.txtContent);
        txtContent.setText(data);
        Button butOK = dialogView.findViewById(R.id.butOK);
        butOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                progressDialog.show();

                deleteImage(sanPham.getLinkAnhSP());

                String path = "listSanPham/" + ID;

                DatabaseReference myRef = database.getReference(path);

                myRef.removeValue(new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                        progressDialog.dismiss();
                        showCustomDialogSucess("Xóa sản phẩm thành công");
                        Intent intent = new Intent(DetailProduct.this, ListProduct.class);
                        setResult(RESULT_OK, intent);
                        finish();
                    }
                });

            }
        });

        Button butCancel = dialogView.findViewById(R.id.butCancel);
        butCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        Window dialogWindow = dialog.getWindow();
        if (dialogWindow != null) {
            WindowManager.LayoutParams layoutParams = dialogWindow.getAttributes();
            layoutParams.gravity = Gravity.TOP;
            layoutParams.y = (int) getResources().getDimension(R.dimen.dialog_margin_top);
            dialogWindow.setAttributes(layoutParams);
        }
        dialog.show();

    }

    private void showCustomDialogSucess(String data){
        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogViewFail = inflater.inflate(R.layout.dialog_sucess, null);
        builder.setView(dialogViewFail);
        Dialog dialog = builder.create();

        TextView txtAlert = dialogViewFail.findViewById(R.id.txtContent);
        txtAlert.setText(data);

        Window dialogWindow = dialog.getWindow();
        if (dialogWindow != null) {
            WindowManager.LayoutParams layoutParams = dialogWindow.getAttributes();
            layoutParams.gravity = Gravity.TOP;
            layoutParams.y = (int) getResources().getDimension(R.dimen.dialog_margin_top);
            dialogWindow.setAttributes(layoutParams);
        }
        dialog.show();
    }

    private void showCustomDialogFail(String data){
        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogViewFail = inflater.inflate(R.layout.dialog_fail, null);
        builder.setView(dialogViewFail);
        Dialog dialog = builder.create();

        TextView txtAlert = dialogViewFail.findViewById(R.id.txtAlert);
        txtAlert.setText(data);
        Button butOK = dialogViewFail.findViewById(R.id.butOK);
        butOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        Window dialogWindow = dialog.getWindow();
        if (dialogWindow != null) {
            WindowManager.LayoutParams layoutParams = dialogWindow.getAttributes();
            layoutParams.gravity = Gravity.TOP;
            layoutParams.y = (int) getResources().getDimension(R.dimen.dialog_margin_top);
            dialogWindow.setAttributes(layoutParams);
        }
        dialog.show();
    }

    private void deleteImage(List<String> listURL) {
        for (int i=0; i<listURL.size(); i++) {
            FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();
            StorageReference storageReference = firebaseStorage.getReferenceFromUrl(listURL.get(i));
            storageReference.delete();
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(DetailProduct.this, ListProduct.class);
        setResult(RESULT_OK, intent);
        finish();
        super.onBackPressed();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.product_detail_menu, menu);        
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.action_edit:
                openEditProduct();
                break;
            case android.R.id.home:
                Intent intent = new Intent(DetailProduct.this, ListProduct.class);
                setResult(RESULT_OK, intent);
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void openEditProduct() {
        Intent intent = new Intent(DetailProduct.this, EditProduct.class);
        intent.putExtra("ID", sanPham.getMaSP());
        launcher.launch(intent);
    }
}