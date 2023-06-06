package com.example.se114n21.ViewModels;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
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
import com.example.se114n21.Adapter.ImageAdapter;
import com.example.se114n21.Adapter.ImageSliderAdapter;
import com.example.se114n21.Models.IdGenerator;
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
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.smarteist.autoimageslider.SliderView;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class AddProductActivity extends AppCompatActivity {
    private Button btn_AddImage, btn_SaveProduct;
    private EditText edit_Name, edit_Brand, edit_MGF, edit_Desc, edit_RetailPrice, edit_CostPrice, edit_Stock, edit_Commission, edit_ProductType;
    private TextInputLayout layout_Name, layout_Brand, layout_MGF, layout_Desc, layout_RetailPrice, layout_CostPrice, layout_Stock, layout_Commission, layout_ProductType;
    private ActivityResultLauncher<Intent> activityResultLauncher;
    private ActivityResultLauncher<Intent> launcher_ProductType;

    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private FirebaseStorage storage = FirebaseStorage.getInstance();
    private StorageReference storageRef = storage.getReference();

    private ProgressDialog progressDialog;
//
    private RecyclerView rcvImg;
    private ImageAdapter mImageAdapter;
    private List<Uri> mListUri;


//    passing
    LoaiSanPham loaiSanPhamPicked = null;

//
    List<String> mListURL = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);

        activityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent data = result.getData();
                        if (data != null) {
                            if (data.getClipData() != null) {
                                // Multiple images selected
                                int count = data.getClipData().getItemCount();
                                int limit = 5 - mListUri.size();

                                if (count >= limit)
                                    count = limit;

                                if (count < 1) {
                                    Toast.makeText(this, "You can select a maximum of " + "5" + " images.", Toast.LENGTH_SHORT).show();
                                    return;
                                }

                                for (int i = 0; i < count; i++) {
                                    Uri imageUri = data.getClipData().getItemAt(i).getUri();
                                    mListUri.add(imageUri);
                                }
                            } else if (data.getData() != null) {
                                // Single image selected
                                Uri imageUri = data.getData();
                                mListUri.add(imageUri);
                            }
                            // Process the selected images
                            mImageAdapter.notifyDataSetChanged();
                        }
                    }
                });

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

        initUI();
        textChangeListener();

        btn_AddImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openGallery();
            }
        });
        
        edit_ProductType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(AddProductActivity.this, ListProductType.class);
                i.putExtra("code","pick");
                launcher_ProductType.launch(i);
            }
        });

        btn_SaveProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkValid()) {
                    progressDialog.show();
                    getmaxId();
                }
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

    private void addProduct(SanPham sanPham, KhoHang khoHang) {
        DatabaseReference myRef = database.getReference("listSanPham");
        myRef.child(sanPham.getMaSP()).setValue(sanPham, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                progressDialog.dismiss();
                Toast.makeText(AddProductActivity.this, "Upload Product Successfully!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(AddProductActivity.this, ListProduct.class);
                setResult(RESULT_OK, intent);
                finish();
            }
        });

        DatabaseReference myRef2 = database.getReference("listKhoHang");

        myRef2.child(khoHang.getMaSP()).setValue(khoHang);
    }

    private boolean save = false;
    private void getmaxId() {
        save = false;
        Query lastItem = FirebaseDatabase.getInstance().getReference().child("listSanPham").limitToLast(1);
        lastItem.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    for (DataSnapshot item: snapshot.getChildren()) {
                        if (save == false) {
                            SanPham sanPham = item.getValue(SanPham.class);
                            Integer maxId = Integer.parseInt(sanPham.getMaSP().substring(3));
                            PrepareProduct(maxId);
                            save = true;
                        }
                    }
                }
                else {
                    PrepareProduct(0);
                    save = true;
                }

            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                progressDialog.dismiss();
                Toast.makeText(AddProductActivity.this, "Upload Product failed!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private String createID(Integer maxId) {
        IdGenerator generator = new IdGenerator();
        generator.init("SP", "", maxId, "%04d");

        return generator.generate();
    }

    private void PrepareProduct(Integer maxId) {
        SanPham sanPham = new SanPham();

        String ID = createID(maxId);

        sanPham.setMaSP(ID);

        sanPham.setTenSP(edit_Name.getText().toString().trim());

        sanPham.setNamSX(Integer.parseInt(edit_MGF.getText().toString().trim()));

        sanPham.setThuongHieu(edit_Brand.getText().toString().trim());

        if (loaiSanPhamPicked != null) {
            sanPham.setMaLSP(loaiSanPhamPicked.getMaLSP());
        }

        sanPham.setMota(edit_Desc.getText().toString().trim());

        sanPham.setGiaBan(Integer.parseInt(edit_RetailPrice.getText().toString().trim()));

        sanPham.setGiaNhap(Integer.parseInt(edit_CostPrice.getText().toString().trim()));

        sanPham.setHoaHong((double) Integer.parseInt(edit_Commission.getText().toString().trim()));

        sanPham.setSoLuong(Integer.parseInt(edit_Stock.getText().toString().trim()));

        KhoHang khoHang = new KhoHang(ID, Long.parseLong(edit_Stock.getText().toString().trim()));

        if (mListUri.size() > 0) {
            mListURL.clear();
            for (int i = 0; i < mListUri.size(); i++) {
                uploadToStorage(mListUri.get(i), i+1, mListUri.size(), sanPham, khoHang);
            }
        } else  {
            addProduct(sanPham, khoHang);
        }
    }

    private void uploadToStorage(Uri uri, int i, int size, SanPham sanPham, KhoHang khoHang) {
        StorageReference fileRef = storageRef.child( "product/" + System.currentTimeMillis() + "." + getFileExtension(uri));
        fileRef.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                fileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        mListURL.add(uri.toString());

                        if (mListURL.size() == mListUri.size()) {
                            sanPham.setLinkAnhSP(mListURL);
                            addProduct(sanPham, khoHang);
                        }
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressDialog.dismiss();
                Toast.makeText(AddProductActivity.this, "Upload Product failed!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private String getFileExtension(Uri mUri) {
        ContentResolver cr = getContentResolver();
        MimeTypeMap mime =  MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cr.getType(mUri));
    }

    private void openGallery() {
        Intent intent= new Intent();
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE,true);
        intent.setAction(Intent.ACTION_GET_CONTENT);
        activityResultLauncher.launch(Intent.createChooser(intent, "Select Images"));
    }

    private void initUI() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setMessage("Please wait!");



//        ACTION BAR
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Thêm sản phẩm");
        actionBar.setDisplayHomeAsUpEnabled(true);

        actionBar.setHomeAsUpIndicator(R.drawable.baseline_arrow_back_ios_24);

//        button
        btn_AddImage = findViewById(R.id.btn_addImage);
        btn_SaveProduct = findViewById(R.id.btn_saveProduct);

//        edit text
        edit_Name = findViewById(R.id.edit_nameProduct);
        edit_Brand = findViewById(R.id.edit_brand);
        edit_MGF = findViewById(R.id.edit_MGF);
        edit_Desc = findViewById(R.id.edit_desc);
        edit_RetailPrice = findViewById(R.id.edit_retailPrice);
        edit_CostPrice = findViewById(R.id.edit_costPrice);
        edit_Stock = findViewById(R.id.edit_stock);
        edit_Commission = findViewById(R.id.edit_commission);
        edit_ProductType = findViewById(R.id.edit_productType);

//        Input Text Layout
        layout_Name = findViewById(R.id.layout_nameProduct);
        layout_Brand = findViewById(R.id.layout_brand);
        layout_MGF = findViewById(R.id.layout_MGF);
        layout_Desc = findViewById(R.id.layout_desc);
        layout_RetailPrice = findViewById(R.id.layout_retailPrice);
        layout_CostPrice = findViewById(R.id.layout_costPrice);
        layout_Stock = findViewById(R.id.layout_stock);
        layout_Commission = findViewById(R.id.layout_commission);
        layout_ProductType = findViewById(R.id.layout_productType);

        

//        RCV

        rcvImg = findViewById(R.id.rcv_img);

        rcvImg.setHasFixedSize(true);
        rcvImg.setItemViewCacheSize(5);
        rcvImg.setDrawingCacheEnabled(true);
        rcvImg.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false);
        rcvImg.setLayoutManager(linearLayoutManager);


        mListUri = new ArrayList<>();

        mImageAdapter = new ImageAdapter(mListUri);

        rcvImg.setAdapter(mImageAdapter);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
