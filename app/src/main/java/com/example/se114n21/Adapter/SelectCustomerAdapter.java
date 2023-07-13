package com.example.se114n21.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.example.se114n21.Interface.SelectCustomerInterface;
import com.example.se114n21.Models.KhachHang;
import com.example.se114n21.Models.SanPham;
import com.example.se114n21.R;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class SelectCustomerAdapter extends RecyclerView.Adapter<SelectCustomerAdapter.SelectCustomerViewHolder> implements Filterable {
    private List<KhachHang> mListKhachHang;
    private List<KhachHang> mListKhachHangOLD;
    private Context mContext;
    private SelectCustomerInterface selectCustomerInterface;

    public SelectCustomerAdapter(List<KhachHang> mListKhachHang, Context mContext, SelectCustomerInterface listener) {
        this.mListKhachHang = mListKhachHang;
        this.mListKhachHangOLD = mListKhachHang;
        this.mContext = mContext;
        this.selectCustomerInterface = listener;
    }

    @NonNull
    @Override
    public SelectCustomerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_select_customer, parent, false);
        return new SelectCustomerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SelectCustomerViewHolder holder, int position) {
        KhachHang khachHang = mListKhachHang.get(position);
        if (khachHang == null) {
            return;
        }

        holder.tvId.setText(khachHang.getMaKH());
        holder.tvName.setText(khachHang.getTen());
        holder.tvPhone.setText(khachHang.getDienThoai());
        holder.tvAddress.setText(khachHang.getDiaChi());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectCustomerInterface.onClick(khachHang);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (mListKhachHang != null) {
            return mListKhachHang.size();
        }
        return 0;
    }

    public class SelectCustomerViewHolder extends RecyclerView.ViewHolder {
        TextView tvId, tvName, tvPhone, tvAddress;

        public SelectCustomerViewHolder(@NonNull View itemView) {
            super(itemView);

            tvId = itemView.findViewById(R.id.tv_id_customer);
            tvName = itemView.findViewById(R.id.tv_name_customer);
            tvPhone = itemView.findViewById(R.id.tv_phone_customer);
            tvAddress = itemView.findViewById(R.id.tv_address_customer);
        }
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String strSearch = charSequence.toString();

                if (strSearch.isEmpty()) {
                    mListKhachHang = mListKhachHangOLD;
                }
                else {
                    List<KhachHang> list = new ArrayList<>();
                    for (KhachHang khachHang : mListKhachHangOLD) {
                        if (khachHang.getMaKH().toLowerCase().contains(strSearch.toLowerCase())
                                || khachHang.getTen().toLowerCase().contains(strSearch.toLowerCase())
                                || khachHang.getDienThoai().toLowerCase().contains(strSearch.toLowerCase())) {
                            list.add(khachHang);
                        }
                    }

                    mListKhachHang = list;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = mListKhachHang;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                mListKhachHang =  (List<KhachHang>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }
}
