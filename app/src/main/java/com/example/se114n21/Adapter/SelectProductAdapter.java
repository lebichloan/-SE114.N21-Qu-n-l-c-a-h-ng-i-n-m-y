package com.example.se114n21.Adapter;

import android.content.Context;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.se114n21.Models.SanPham;
import com.example.se114n21.Models.SelectProduct;
import com.example.se114n21.R;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class SelectProductAdapter extends RecyclerView.Adapter<SelectProductAdapter.SelectProductViewHolder> implements Filterable{

    private Context mContext;
    private List<SelectProduct> mListSelectProduct;
    private List<SelectProduct> mListSelectProductOLD;



    public SelectProductAdapter(Context mContext, List<SelectProduct> mListSelectProduct) {
        this.mContext = mContext;
        this.mListSelectProduct = mListSelectProduct;
        this.mListSelectProductOLD = mListSelectProduct;
    }

    @NonNull
    @Override
    public SelectProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_select_product, parent, false);
        return new SelectProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SelectProductViewHolder holder, int position) {
        SelectProduct selectProduct = mListSelectProduct.get(position);
        if (selectProduct == null){
            return;
        }

        holder.bind(selectProduct);
    }

    @Override
    public int getItemCount() {
        if (mListSelectProduct != null) {
            return mListSelectProduct.size();
        }
        return 0;
    }

    public List<SanPham> getProductSelected() {
        List<SanPham> list = new ArrayList<>();

        for (int i=0; i<mListSelectProduct.size(); i++) {
            if (mListSelectProduct.get(i).isSeleted()) {
                list.add(mListSelectProduct.get(i).getSanPham());
            }
        }

        return list;
    }

    public List<SelectProduct> getAll() {
        return mListSelectProduct;
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String strSearch = charSequence.toString();

                if (strSearch.isEmpty()) {
                    mListSelectProduct = mListSelectProductOLD;
                }
                else {
                    List<SelectProduct> list = new ArrayList<>();
                    for (SelectProduct selectProduct : mListSelectProductOLD) {
                        if (selectProduct.getSanPham().getTenSP().toLowerCase().contains(strSearch.toLowerCase())
                                || selectProduct.getSanPham().getMaSP().toLowerCase().contains(strSearch.toLowerCase())) {
                            list.add(selectProduct);
                        }
                    }

                    mListSelectProduct = list;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = mListSelectProduct;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                mListSelectProduct =  (List<SelectProduct>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }


    public class SelectProductViewHolder extends RecyclerView.ViewHolder {
        private ImageView imgProduct, imgCheck;
        private TextView tvId, tvName, tvRetail, tvStock;

        public SelectProductViewHolder(@NonNull View itemView) {
            super(itemView);

            imgProduct = itemView.findViewById(R.id.img_select_product);
            imgCheck = itemView.findViewById(R.id.check_icon);
            tvId = itemView.findViewById(R.id.tv_id_select_product);
            tvName = itemView.findViewById(R.id.tv_name_select_product);
            tvRetail = itemView.findViewById(R.id.tv_retail_select_product);
            tvStock = itemView.findViewById(R.id.tv_stock_select_product);
        }


        void bind(final SelectProduct selectProduct) {
            imgCheck.setVisibility(selectProduct.isSeleted() ? View.VISIBLE : View.GONE);

            if (selectProduct.getSanPham().getLinkAnhSP().size() > 0) {
                List<String> listURL = new ArrayList<>();
                listURL.clear();
                listURL = selectProduct.getSanPham().getLinkAnhSP();

                Glide.with(mContext)
                        .load(listURL.get(0)) // image url
                        .placeholder(R.drawable.ic_launcher_background) // any placeholder to load at start
                        .error(R.mipmap.ic_launcher)  // any image in case of error
                        .into(imgProduct);

            } else {
                imgProduct.setImageResource(R.mipmap.ic_launcher);
            }

            tvId.setText(selectProduct.getSanPham().getMaSP());
            tvName.setText(selectProduct.getSanPham().getTenSP());
            tvRetail.setText(selectProduct.getSanPham().getGiaBan().toString());
            if (selectProduct.getSanPham().getSoLuong() != null) {
                tvStock.setText(selectProduct.getSanPham().getSoLuong().toString());
            }

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    selectProduct.setSeleted(!selectProduct.isSeleted());
                    imgCheck.setVisibility(selectProduct.isSeleted() ? View.VISIBLE : View.GONE);
                }
            });
        }
    }
}
