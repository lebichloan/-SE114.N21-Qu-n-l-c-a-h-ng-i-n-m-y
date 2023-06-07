package com.example.se114n21.ViewModels;

import androidx.appcompat.app.AppCompatActivity;
import com.example.se114n21.R;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class AddProductActivity extends AppCompatActivity {
    Button btn_AddImage, btn_ProductType;
    EditText edit_Name, edit_Brand, edit_MGF, edit_Desc, edit_RetailPrice, edit_CostPrice, edit_Stock, edit_Commission;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);


        initUI();
    }

    private void initUI() {
        btn_AddImage = findViewById(R.id.btn_addImage);
        btn_ProductType = findViewById(R.id.btn_productType);
        btn_AddImage = findViewById(R.id.btn_saveProduct);
        edit_Name = findViewById(R.id.edit_nameProduct);
        edit_Brand = findViewById(R.id.edit_brand);
        edit_MGF = findViewById(R.id.edit_MGF);
        edit_Desc = findViewById(R.id.edit_desc);
        edit_RetailPrice = findViewById(R.id.edit_retailPrice);
        edit_CostPrice = findViewById(R.id.edit_costPrice);
        edit_Stock = findViewById(R.id.edit_stock);
        edit_Commission = findViewById(R.id.edit_commission);
    }
}