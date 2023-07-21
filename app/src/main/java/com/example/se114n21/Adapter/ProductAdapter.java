package com.example.se114n21.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.se114n21.Interface.ProductInterface;
import com.example.se114n21.Models.SanPham;
import com.example.se114n21.R;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> implements Filterable {
    private List<SanPham> mListSanPham;
    private List<SanPham> mListSanPhamOLD;
    private Context mContext;
    private ProductInterface productInterface;

    public ProductAdapter(List<SanPham> mListSanPham, Context mContext, ProductInterface productInterface) {
        this.mListSanPham = mListSanPham;
        this.mContext = mContext;
        this.productInterface = productInterface;
        this.mListSanPhamOLD = mListSanPham;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product, parent, false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        SanPham sanPham = mListSanPham.get(position);
        if (sanPham == null) {
            return;
        }

        if (sanPham.getLinkAnhSP().size() > 0) {
            List<String> listURL = new ArrayList<>();
            listURL.clear();
            listURL = sanPham.getLinkAnhSP();

            Glide.with(mContext)
                    .load(listURL.get(0)) // image url
                    .placeholder(R.drawable.ic_launcher_background) // any placeholder to load at start
                    .error(R.mipmap.ic_launcher)  // any image in case of error
                    .into(holder.imgProduct);

        } else {
            holder.imgProduct.setImageResource(R.mipmap.ic_launcher);
        }

        holder.tvId.setText(sanPham.getMaSP());
        holder.tvName.setText(sanPham.getTenSP());
        holder.tvRetail.setText(sanPham.getGiaBan().toString());
        if (sanPham.getSoLuong() != null) {
            holder.tvStock.setText(sanPham.getSoLuong().toString());
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                productInterface.onClick(sanPham);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (mListSanPham != null) {
            return mListSanPham.size();
        }
        return 0;
    }

    public class ProductViewHolder extends RecyclerView.ViewHolder {
        private TextView tvId, tvName, tvRetail, tvStock;
        private ImageView imgProduct;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            tvId = itemView.findViewById(R.id.tv_id_product);
            tvName = itemView.findViewById(R.id.tv_name_product);
            tvRetail = itemView.findViewById(R.id.tv_retail_product);
            tvStock = itemView.findViewById(R.id.tv_stock_product);
            imgProduct = itemView.findViewById(R.id.img_product);
        }
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String strSearch = charSequence.toString();

                if (strSearch.isEmpty()) {
                    mListSanPham = mListSanPhamOLD;
                }
                else {
                    List<SanPham> list = new ArrayList<>();
                    for (SanPham sanPham : mListSanPhamOLD) {
                        if (sanPham.getTenSP().toLowerCase().contains(strSearch.toLowerCase())
                            || sanPham.getMaSP().toLowerCase().contains(strSearch.toLowerCase())) {
                            list.add(sanPham);
                        }
                    }

                    mListSanPham = list;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = mListSanPham;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                mListSanPham =  (List<SanPham>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }
}
