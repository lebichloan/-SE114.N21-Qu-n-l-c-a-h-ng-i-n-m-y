package com.example.se114n21.ViewModels;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.RequiresPermission;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.se114n21.Adapter.ImageAdapter;
import com.example.se114n21.R;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class AddProductActivity extends AppCompatActivity {
    private Button btn_AddImage, btn_ProductType;
    private EditText edit_Name, edit_Brand, edit_MGF, edit_Desc, edit_RetailPrice, edit_CostPrice, edit_Stock, edit_Commission;
    private ImageView img_Product;
    private ActivityResultLauncher<Intent> activityResultLauncher;
//
    private RecyclerView rcvImg;
    private ImageAdapter mImageAdapter;
    private List<Uri> mListUri;

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




        initUI();

        btn_AddImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openGallery();
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

    private void initUI() {
        btn_AddImage = findViewById(R.id.btn_addImage);
        btn_ProductType = findViewById(R.id.btn_productType);
        btn_ProductType = findViewById(R.id.btn_saveProduct);
        edit_Name = findViewById(R.id.edit_nameProduct);
        edit_Brand = findViewById(R.id.edit_brand);
        edit_MGF = findViewById(R.id.edit_MGF);
        edit_Desc = findViewById(R.id.edit_desc);
        edit_RetailPrice = findViewById(R.id.edit_retailPrice);
        edit_CostPrice = findViewById(R.id.edit_costPrice);
        edit_Stock = findViewById(R.id.edit_stock);
        edit_Commission = findViewById(R.id.edit_commission);
        img_Product = findViewById(R.id.img_product);

        rcvImg = findViewById(R.id.rcv_img);

        rcvImg.setHasFixedSize(true);
        rcvImg.setItemViewCacheSize(20);
        rcvImg.setDrawingCacheEnabled(true);
        rcvImg.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false);
        rcvImg.setLayoutManager(linearLayoutManager);


        mListUri = new ArrayList<>();

        mImageAdapter = new ImageAdapter(mListUri);

        rcvImg.setAdapter(mImageAdapter);
    }
}
