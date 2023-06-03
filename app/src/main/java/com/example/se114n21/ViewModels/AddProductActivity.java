package com.example.se114n21.ViewModels;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.se114n21.Adapter.ImageAdapter;
import com.example.se114n21.Adapter.ImageSliderAdapter;
import com.example.se114n21.R;
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;
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

    private SliderView sliderView;
    private List<String> mListImageSlider;
    private ImageSliderAdapter mImageSliderAdapter;

    private ImageView imageView;

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
//        SLIDER VIEW
//        sliderView = findViewById(R.id.sliderview_add_product);
//
//        mListImageSlider = new ArrayList<>();
//
//        mListImageSlider.add("https://plus.unsplash.com/premium_photo-1675019222084-1be97a344202?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=502&q=80");
//        mListImageSlider.add("https://images.unsplash.com/photo-1684388021048-b0a9f52a8a80?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=387&q=80");
//        mListImageSlider.add("https://images.unsplash.com/photo-1685519318525-4e0689672391?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=435&q=80");
//        mListImageSlider.add("https://images.unsplash.com/photo-1661956600655-e772b2b97db4?ixlib=rb-4.0.3&ixid=M3wxMjA3fDF8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=435&q=80");
//        mListImageSlider.add("https://images.unsplash.com/photo-1685268960236-024737840fa7?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=435&q=80");
//
//        mImageSliderAdapter = new ImageSliderAdapter(this, mListImageSlider);
//
//        sliderView.setSliderAdapter(mImageSliderAdapter);
//
//        sliderView.setIndicatorAnimation(IndicatorAnimationType.FILL);
//        sliderView.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
//
//        mImageSliderAdapter.notifyDataSetChanged();



//        ACTION BAR
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Thêm sản phẩm");
        actionBar.setDisplayHomeAsUpEnabled(true);

        actionBar.setHomeAsUpIndicator(R.drawable.baseline_arrow_back_ios_24);


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
