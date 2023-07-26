package com.example.se114n21.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.se114n21.Interface.CustomerInterface;
import com.example.se114n21.Models.KhachHang;
import com.example.se114n21.Models.KhuyenMai;
import com.example.se114n21.R;

import java.util.ArrayList;
import java.util.List;

public class CustomerApdater extends RecyclerView.Adapter<CustomerApdater.CustomerViewHolder> implements Filterable {
    private List<KhachHang> mListKhachHang;
    private List<KhachHang> mListKhachHangOLD;
    private CustomerInterface customerInterface;

    public CustomerApdater(List<KhachHang> mListKhachHang, CustomerInterface customerInterface) {
        this.mListKhachHang = mListKhachHang;
        this.mListKhachHangOLD = mListKhachHang;
        this.customerInterface = customerInterface;
    }

    @NonNull
    @Override
    public CustomerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.customer_layout, parent, false);
        return new CustomerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomerViewHolder holder, int position) {
        KhachHang khachHang = mListKhachHang.get(position);
        if (khachHang == null) {
            return;
        }

        holder.name.setText(khachHang.getTen());
        holder.sodienthoai.setText(khachHang.getDienThoai());
        holder.diachi.setText(khachHang.getDiaChi());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                customerInterface.onClick(khachHang);
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

    public class CustomerViewHolder extends RecyclerView.ViewHolder {
        TextView name, sodienthoai, diachi;
        public CustomerViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.name_customer);
            sodienthoai = itemView.findViewById(R.id.phone_customer);
            diachi = itemView.findViewById(R.id.address_customer);
        }
    }
}
