package com.example.se114n21.ViewModels;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.se114n21.Adapter.ImageAdapter;
import com.example.se114n21.Adapter.PropertyAdapter;
import com.example.se114n21.Models.IdGenerator;
import com.example.se114n21.Models.KhoHang;
import com.example.se114n21.Models.LoaiSanPham;
import com.example.se114n21.Models.SanPham;
import com.example.se114n21.Models.ThuocTinh;
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

import java.util.ArrayList;
import java.util.List;

public class AddProductActivity extends AppCompatActivity {
    private ImageButton btnBack;
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

//    RCV THUOC TINH
    private RecyclerView rcvThuocTinh;
    private List<ThuocTinh> mListThuocTinh;
    private PropertyAdapter mPropertyAdapter;
    private Button btnAddProperty;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);
        getSupportActionBar().hide();

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
                                    Toast.makeText(this, "Chọn tối đa 5 hình ảnh.", Toast.LENGTH_SHORT).show();
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

    private boolean checkValid() {
        boolean isValid = true;

        if (edit_Name.getText().toString().equals("")) {
            edit_Name.setError("Nội dung bắt buộc");
            edit_Name.requestFocus();
            isValid = false;
        }

        if (edit_Brand.getText().toString().equals("")) {
            edit_Brand.setError("Nội dung bắt buộc");
            edit_Brand.requestFocus();
            isValid = false;
        }

        if (edit_MGF.getText().toString().equals("")) {
            edit_MGF.setError("Nội dung bắt buộc");
            edit_MGF.requestFocus();
            isValid = false;
        }

        if (edit_RetailPrice.getText().toString().equals("")) {
            edit_RetailPrice.setError("Nội dung bắt buộc");
            edit_RetailPrice.requestFocus();
            isValid = false;
        }

        if (edit_CostPrice.getText().toString().equals("")) {
            edit_CostPrice.setError("Nội dung bắt buộc");
            edit_CostPrice.requestFocus();
            isValid = false;
        }


        if (edit_Stock.getText().toString().equals("")) {
            edit_Stock.setError("Nội dung bắt buộc");
            edit_Stock.requestFocus();
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
    }

    private void addProduct(SanPham sanPham, KhoHang khoHang) {
        DatabaseReference myRef = database.getReference("listSanPham");
        myRef.child(sanPham.getMaSP()).setValue(sanPham, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                progressDialog.dismiss();
                Toast.makeText(AddProductActivity.this, "Thêm sản phẩm thành công!", Toast.LENGTH_SHORT).show();
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
                Toast.makeText(AddProductActivity.this, "Có lỗi xảy ra khi thêm sản phẩm. Vui lòng thử lại!", Toast.LENGTH_SHORT).show();
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

        sanPham.setDSThuocTinh(mListThuocTinh);

        sanPham.setMota(edit_Desc.getText().toString().trim());

        sanPham.setGiaBan(Integer.parseInt(edit_RetailPrice.getText().toString().trim()));

        sanPham.setGiaNhap(Integer.parseInt(edit_CostPrice.getText().toString().trim()));

        sanPham.setSoLuong(Integer.parseInt(edit_Stock.getText().toString().trim()));

        sanPham.setTenLSP(edit_ProductType.getText().toString().trim());

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
                Toast.makeText(AddProductActivity.this, "Có lỗi xảy ra khi thêm sản phẩm. Vui lòng thử lại!", Toast.LENGTH_SHORT).show();
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
        btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });


        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setMessage("Please wait!");

//      BUTTON ADD PROPERTY
        btnAddProperty = findViewById(R.id.btn_add_property);
        btnAddProperty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openAddPropertyDialog(Gravity.CENTER);
            }
        });

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
        edit_ProductType = findViewById(R.id.edit_productType);

//        Input Text Layout
        layout_Name = findViewById(R.id.layout_nameProduct);
        layout_Brand = findViewById(R.id.layout_brand);
        layout_MGF = findViewById(R.id.layout_MGF);
        layout_Desc = findViewById(R.id.layout_desc);
        layout_RetailPrice = findViewById(R.id.layout_retailPrice);
        layout_CostPrice = findViewById(R.id.layout_costPrice);
        layout_Stock = findViewById(R.id.layout_stock);
        layout_ProductType = findViewById(R.id.layout_productType);

        

//        RCV

        rcvImg = findViewById(R.id.rcv_img);

        rcvImg.setItemViewCacheSize(5);
        rcvImg.setDrawingCacheEnabled(true);
        rcvImg.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false);
        rcvImg.setLayoutManager(linearLayoutManager);


        mListUri = new ArrayList<>();

        mImageAdapter = new ImageAdapter(mListUri);

        rcvImg.setAdapter(mImageAdapter);

//        RCV THUOC TINH
        rcvThuocTinh = findViewById(R.id.rcv_property);

        rcvThuocTinh.setItemViewCacheSize(5);
        rcvThuocTinh.setDrawingCacheEnabled(true);
        rcvThuocTinh.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);


        LinearLayoutManager linearLayoutManagerThuocTinh = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        rcvThuocTinh.setLayoutManager(linearLayoutManagerThuocTinh);

        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        rcvThuocTinh.addItemDecoration(itemDecoration);

        mListThuocTinh = new ArrayList<>();

        mPropertyAdapter = new PropertyAdapter(mListThuocTinh, "add");

        rcvThuocTinh.setAdapter(mPropertyAdapter);
    }

    private void openAddPropertyDialog(int gravity) {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.property_dialog);

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

        TextView tvTitle = dialog.findViewById(R.id.tv_title_property_dialog);
        EditText editName = dialog.findViewById(R.id.edit_name_property_dialog);
        EditText editValue = dialog.findViewById(R.id.edit_value_property_dialog);
        Button btnCancel = dialog.findViewById(R.id.btn_property_dialog_cancel);
        Button btnOk = dialog.findViewById(R.id.btn_property_dialog_ok);

        tvTitle.setText("Thêm thuộc tính");
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
                String name = editName.getText().toString().trim();
                String value = editValue.getText().toString().trim();
                
                if (name.isEmpty() || value.isEmpty()) {
                    Toast.makeText(AddProductActivity.this, "Vui lòng nhập đầy đủ thông tin trươớc khi tiếp tục!", Toast.LENGTH_SHORT).show();
                } else {
                    dialog.dismiss();
                    progressDialog.show();

                    ThuocTinh newThuocTinh = new ThuocTinh(name, value);

                    mListThuocTinh.add(mListThuocTinh.size(), newThuocTinh);
                    mPropertyAdapter.notifyItemInserted(mListThuocTinh.size());

                    progressDialog.dismiss();
                }
            }
        });

        dialog.show();
    }
}
