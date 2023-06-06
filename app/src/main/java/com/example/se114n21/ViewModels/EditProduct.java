package com.example.se114n21.ViewModels;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.se114n21.Adapter.ImageForEditAdapter;
import com.example.se114n21.Interface.ImageInterface;
import com.example.se114n21.Models.KhoHang;
import com.example.se114n21.Models.LoaiSanPham;
import com.example.se114n21.Models.SanPham;
import com.example.se114n21.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EditProduct extends AppCompatActivity {
    private EditText edit_Name, edit_Brand, edit_MGF, edit_Desc, edit_RetailPrice, edit_CostPrice, edit_Stock, edit_Commission, edit_ProductType;
    private TextInputLayout layout_Name, layout_Brand, layout_MGF, layout_Desc, layout_RetailPrice, layout_CostPrice, layout_Stock, layout_Commission, layout_ProductType;
    private Button btnUpdate, btnAddImage;
    private String ID;
    private SanPham sanPham;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private RecyclerView recyclerView;
    private List<String> mListURL;
    private ImageForEditAdapter mImageForEditAdapter;
    private ProgressDialog progressDialog;
    private ActivityResultLauncher<Intent> launcher_ProductType;
    private ActivityResultLauncher<Intent> activityResultLauncher;
    private LoaiSanPham loaiSanPhamPicked = null;
    private FirebaseStorage storage = FirebaseStorage.getInstance();
    private StorageReference storageRef = storage.getReference();
    private List<String> mListURL_delete = new ArrayList<>();
    private List<String> mListURL_moithem = new ArrayList<>();

    private boolean isDelete = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_product);

        initUI();

        Intent i = getIntent();
        ID = i.getStringExtra("ID");

        textChangeListener();

        getData();
    }

    private void initUI() {
//        ACTION BAR
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Cập nhật sản phẩm");
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.baseline_arrow_back_ios_24);
//        progress dialog
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Vui long doi mot chut");
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
//        edit text
        edit_Name = findViewById(R.id.edit_name_edit_product);
        edit_Brand = findViewById(R.id.edit_brand_edit_product);
        edit_MGF = findViewById(R.id.edit_MGF_edit_product);
        edit_Desc = findViewById(R.id.edit_desc_edit_product);
        edit_RetailPrice = findViewById(R.id.edit_retailPrice_edit_product);
        edit_CostPrice = findViewById(R.id.edit_costPrice_edit_product);
        edit_Stock = findViewById(R.id.edit_stock_edit_product);
        edit_Commission = findViewById(R.id.edit_commission_edit_product);
        edit_ProductType = findViewById(R.id.edit_type_edit_product);

//        Input Text Layout
        layout_Name = findViewById(R.id.layout_name_edit_product);
        layout_Brand = findViewById(R.id.layout_brand_edit_product);
        layout_MGF = findViewById(R.id.layout_MGF_edit_product);
        layout_Desc = findViewById(R.id.layout_desc_edit_product);
        layout_RetailPrice = findViewById(R.id.layout_retailPrice_edit_product);
        layout_CostPrice = findViewById(R.id.layout_costPrice_edit_product);
        layout_Stock = findViewById(R.id.layout_stock_edit_product);
        layout_Commission = findViewById(R.id.layout_commission_edit_product);
        layout_ProductType = findViewById(R.id.layout_type_edit_product);

        edit_ProductType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(EditProduct.this, ListProductType.class);
                i.putExtra("code","pick");
                launcher_ProductType.launch(i);
            }
        });

//        Button
        btnUpdate = findViewById(R.id.btn_update_edit_product);
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkValid()) {
                    progressDialog.show();
                    updateProduct();
                }
            }
        });

//        Button Add Image
        btnAddImage = findViewById(R.id.btn_add_image_edit_product);
        btnAddImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openGallery();
            }
        });


//        RCV
        recyclerView = findViewById(R.id.rcv_edit_product);

        recyclerView.setHasFixedSize(true);
        recyclerView.setItemViewCacheSize(5);
        recyclerView.setDrawingCacheEnabled(true);
        recyclerView.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);

        mListURL = new ArrayList<>();

        mImageForEditAdapter = new ImageForEditAdapter(mListURL, this, new ImageInterface() {
            @Override
            public void deleteImage(boolean code, String URL_delete) {
                isDelete = code;
                mListURL_delete.add(URL_delete);
            }
        });

        recyclerView.setAdapter(mImageForEditAdapter);


//        LAUNCHER
        launcher_ProductType = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == Activity.RESULT_OK) {
                            Intent intent = result.getData();
                            String ID = intent.getStringExtra("LSP_ID");
                            String NAME = intent.getStringExtra("LSP_NAME");

                            loaiSanPhamPicked = new LoaiSanPham(ID, NAME);

                            edit_ProductType.setText(NAME);
                        }
                    }
                });

        activityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent data = result.getData();
                        if (data != null) {
                            progressDialog.show();

                            if (data.getClipData() != null) {
                                // Multiple images selected
                                int count = data.getClipData().getItemCount();
                                int limit = 5 - mListURL.size();

                                if (count >= limit)
                                    count = limit;

                                if (count < 1) {
                                    Toast.makeText(this, "You can select a maximum of " + "5" + " images.", Toast.LENGTH_SHORT).show();
                                    progressDialog.dismiss();
                                    return;
                                }

                                for (int i = 0; i < count; i++) {
                                    Uri imageUri = data.getClipData().getItemAt(i).getUri();
                                    uploadToStorage(imageUri, i+1, count);
                                }

                            } else if (data.getData() != null) {
                                // Single image selected
                                Uri imageUri = data.getData();
                                uploadToStorage(imageUri, 1, 1);
                            }
                        }
                    }
                });
    }

    private void updateProduct() {
        String path = "listSanPham/" + sanPham.getMaSP();
        DatabaseReference myRef = database.getReference(path);

        Map<String, Object> map = new HashMap<>();
        map.put("tenSP", edit_Name.getText().toString().trim());
        map.put("namSX", Integer.parseInt(edit_MGF.getText().toString().trim()));
        map.put("thuongHieu", edit_Brand.getText().toString().trim());
        if (loaiSanPhamPicked == null) {
            map.put("maLSP", sanPham.getMaLSP());
        } else {
            map.put("maLSP", loaiSanPhamPicked.getMaLSP());
        }
        map.put("mota", edit_Desc.getText().toString().trim());
        map.put("giaBan", Integer.parseInt(edit_RetailPrice.getText().toString().trim()));
        map.put("giaNhap", Integer.parseInt(edit_CostPrice.getText().toString().trim()));
        map.put("soLuong", Integer.parseInt(edit_Stock.getText().toString().trim()));
        map.put("hoaHong", Double.parseDouble(edit_Commission.getText().toString().trim()));
        map.put("linkAnhSP", mListURL);


        myRef.updateChildren(map, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                progressDialog.dismiss();
                Toast.makeText(EditProduct.this, "Cap nhat san pham thanh cong", Toast.LENGTH_SHORT).show();
                if (isDelete == true) {
                    checkImage(mListURL_delete);
                }
                Intent intent = new Intent(EditProduct.this, DetailProduct.class);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }

    private void openGallery() {
        Intent intent= new Intent();
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE,true);
        intent.setAction(Intent.ACTION_GET_CONTENT);
        activityResultLauncher.launch(Intent.createChooser(intent, "Select Images"));
    }

    private void uploadToStorage(Uri uri, int i, int size) {
        StorageReference fileRef = storageRef.child( "product/" +  System.currentTimeMillis() + "." + getFileExtension(uri));
        fileRef.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                fileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        String URL = uri.toString();

                        mListURL_moithem.add(URL);
                        mListURL.add(mListURL.size(), URL);
                        mImageForEditAdapter.notifyItemInserted(mListURL.size());

                        if (i == size) {
                            progressDialog.dismiss();
                        }
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressDialog.dismiss();
                Toast.makeText(EditProduct.this, "Upload Image failed!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private String getFileExtension(Uri mUri) {
        ContentResolver cr = getContentResolver();
        MimeTypeMap mime =  MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cr.getType(mUri));
    }

    private void getData() {
        progressDialog.show();
        DatabaseReference myRef = database.getReference("listSanPham/" + ID);

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                sanPham = new SanPham();
                sanPham = snapshot.getValue(SanPham.class);
                getType(sanPham);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void getType(SanPham sanPham) {
        DatabaseReference myRef = database.getReference("listLoaiSanPham/" + sanPham.getMaLSP());

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                LoaiSanPham loaiSanPham = snapshot.getValue(LoaiSanPham.class);
                setData(sanPham, loaiSanPham);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void setData(SanPham sanPham, LoaiSanPham loaiSanPham) {
        edit_Name.setText(sanPham.getTenSP());
        edit_MGF.setText(sanPham.getNamSX().toString());
        edit_Brand.setText(sanPham.getThuongHieu());

        edit_Desc.setText(sanPham.getMota());

        edit_RetailPrice.setText(sanPham.getGiaBan().toString());
        edit_CostPrice.setText(sanPham.getGiaNhap().toString());

        edit_Stock.setText(sanPham.getSoLuong().toString());

        edit_Commission.setText(sanPham.getHoaHong().toString());

        edit_ProductType.setText(loaiSanPham.getTenLSP());

        mListURL.addAll(sanPham.getLinkAnhSP());
        mImageForEditAdapter.notifyDataSetChanged();

        progressDialog.dismiss();
    }

    @Override
    public void onBackPressed() {
        backHandle();
        super.onBackPressed();
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId())
        {
            case android.R.id.home:
                backHandle();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void backHandle() {
        checkImage(mListURL_moithem);

        Intent intent = new Intent(EditProduct.this, DetailProduct.class);
        setResult(RESULT_OK, intent);
        finish();
    }

    private void checkImage(List<String> list) {
        for (int i = 0; i < list.size(); i++) {
            deleteImageOnStorage(list.get(i));
        }
    }

    private void deleteImageOnStorage(String URL) {
        StorageReference storageReference = storage.getReferenceFromUrl(URL);
        storageReference.delete();
    }

    private void textChangeListener() {
        edit_Name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String str = charSequence.toString();
                if (str.length() > 0) {
                    layout_Name.setError("");
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        edit_MGF.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String str = charSequence.toString();
                if (str.length() > 0) {
                    layout_MGF.setError("");
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        edit_Brand.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String str = charSequence.toString();
                if (str.length() > 0) {
                    layout_Brand.setError("");
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        edit_RetailPrice.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String str = charSequence.toString();
                if (str.length() > 0) {
                    layout_RetailPrice.setError("");
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        edit_CostPrice.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String str = charSequence.toString();
                if (str.length() > 0) {
                    layout_CostPrice.setError("");
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        edit_Stock.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String str = charSequence.toString();
                if (str.length() > 0) {
                    layout_Stock.setError("");
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        edit_Commission.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String str = charSequence.toString();
                if (str.length() > 0) {
                    layout_Commission.setError("");
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    private boolean checkValid() {
        boolean isValid = true;

        if (edit_Name.getText().toString().equals("")) {
            layout_Name.setError("* Khong duoc bo trong");
            isValid = false;
        }

        if (edit_Brand.getText().toString().equals("")) {
            layout_Brand.setError("* Khong duoc bo trong");
            isValid = false;
        }

        if (edit_MGF.getText().toString().equals("")) {
            layout_MGF.setError("* Khong duoc bo trong");
            isValid = false;
        }

        if (edit_RetailPrice.getText().toString().equals("")) {
            layout_RetailPrice.setError("* Khong duoc bo trong");
            isValid = false;
        }

        if (edit_CostPrice.getText().toString().equals("")) {
            layout_CostPrice.setError("* Khong duoc bo trong");
            isValid = false;
        }


        if (edit_Stock.getText().toString().equals("")) {
            layout_Stock.setError("* Khong duoc bo trong");
            isValid = false;
        }


        if (edit_Commission.getText().toString().equals("")) {
            layout_Commission.setError("* Khong duoc bo trong");
            isValid = false;
        }

        return isValid;
    }
}