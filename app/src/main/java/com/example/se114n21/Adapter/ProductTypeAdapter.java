package com.example.se114n21.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.example.se114n21.Interface.ProductTypeInterface;
import com.example.se114n21.Models.LoaiSanPham;
import com.example.se114n21.R;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ProductTypeAdapter extends RecyclerView.Adapter<ProductTypeAdapter.ProductTypeViewHolder> implements Filterable {
    private List<LoaiSanPham> mListLoaiSanPham;
    private List<LoaiSanPham> mListLoaiSanPhamOLD;

    private ProductTypeInterface productTypeInterface;

    public ProductTypeAdapter(List<LoaiSanPham> mListLoaiSanPham, ProductTypeInterface listener) {
        this.mListLoaiSanPham = mListLoaiSanPham;
        this.mListLoaiSanPhamOLD = mListLoaiSanPham;
        this.productTypeInterface = listener;
    }

    @NonNull
    @Override
    public ProductTypeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product_type, parent, false);
        return new ProductTypeViewHolder(view);
    }

    @Override
    public int getItemCount() {
        if (mListLoaiSanPham != null) {
            return mListLoaiSanPham.size();
        }
        return 0;
    }

    @Override
    public void onBindViewHolder(@NonNull ProductTypeViewHolder holder, int position) {
        LoaiSanPham loaiSanPham = mListLoaiSanPham.get(position);
        if (loaiSanPham == null) {
            return;
        }

        holder.tv_Name_Product_Type.setText(loaiSanPham.getTenLSP());
        holder.btn_Edit_Product_Type.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                productTypeInterface.onClick(loaiSanPham, "edit");
            }
        });

        holder.btn_Delete_Product_Type.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                productTypeInterface.onClick(loaiSanPham, "delete");
            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                productTypeInterface.onClick(loaiSanPham, "pick");
            }
        });

    }

    public class ProductTypeViewHolder extends RecyclerView.ViewHolder {

        private TextView tv_Name_Product_Type;
        private Button btn_Edit_Product_Type, btn_Delete_Product_Type;

        public ProductTypeViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_Name_Product_Type = itemView.findViewById(R.id.tv_name_product_type);
            btn_Edit_Product_Type =  itemView.findViewById(R.id.btn_edit_product_type);
            btn_Delete_Product_Type = itemView.findViewById(R.id.btn_delete_product_type);
        }
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String strSearch = charSequence.toString();

                if (strSearch.isEmpty()) {
                    mListLoaiSanPham = mListLoaiSanPhamOLD;
                }
                else {
                    List<LoaiSanPham> list = new ArrayList<>();
                    for (LoaiSanPham loaiSanPham : mListLoaiSanPhamOLD) {
                        if (loaiSanPham.getTenLSP().toLowerCase().contains(strSearch.toLowerCase())) {
                            list.add(loaiSanPham);
                        }
                    }

                    mListLoaiSanPham = list;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = mListLoaiSanPham;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                mListLoaiSanPham =  (List<LoaiSanPham>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }
}
