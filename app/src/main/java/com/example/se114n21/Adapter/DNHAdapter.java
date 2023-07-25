package com.example.se114n21.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.se114n21.Interface.DNHInterface;
import com.example.se114n21.Models.DonNhapHang;
import com.example.se114n21.Models.KhachHang;
import com.example.se114n21.R;

import java.util.ArrayList;
import java.util.List;

public class DNHAdapter extends RecyclerView.Adapter<DNHAdapter.DNHViewHolder> implements Filterable {
    private List<DonNhapHang> mListDNH;
    private List<DonNhapHang> mListDNHOLD;
    private DNHInterface dnhInterface;

    public DNHAdapter(List<DonNhapHang> mListDNH, DNHInterface dnhInterface) {
        this.mListDNH = mListDNH;
        this.mListDNHOLD = mListDNH;
        this.dnhInterface = dnhInterface;
    }

    @NonNull
    @Override
    public DNHViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_dnh, parent, false);
        return new DNHViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DNHViewHolder holder, int position) {
        DonNhapHang donNhapHang = mListDNH.get(position);
        if (donNhapHang == null) {
            return;
        }

        holder.madnh.setText(donNhapHang.getMaDNH());
        holder.masp.setText(donNhapHang.getMaSP());
        holder.tensp.setText(donNhapHang.getTenSP());
        holder.thoigian.setText(donNhapHang.getNgayDNH());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dnhInterface.onClick(donNhapHang);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (mListDNH != null) {
            return mListDNH.size();
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
                    mListDNH = mListDNHOLD;
                }
                else {
                    List<DonNhapHang> list = new ArrayList<>();
                    for (DonNhapHang donNhapHang : mListDNHOLD) {
                        if (donNhapHang.getMaDNH().toLowerCase().contains(strSearch.toLowerCase())
                                || donNhapHang.getMaSP().toLowerCase().contains(strSearch.toLowerCase())
                                || donNhapHang.getTenSP().toLowerCase().contains(strSearch.toLowerCase())) {
                            list.add(donNhapHang);
                        }
                    }

                    mListDNH = list;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = mListDNH;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                mListDNH =  (List<DonNhapHang>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public class DNHViewHolder extends RecyclerView.ViewHolder {
        TextView madnh, masp, tensp, thoigian;

        public DNHViewHolder(@NonNull View itemView) {
            super(itemView);

            madnh = itemView.findViewById(R.id.madnh);
            masp = itemView.findViewById(R.id.id_sanpham);
            tensp = itemView.findViewById(R.id.ten_sanpham);
            thoigian = itemView.findViewById(R.id.thoigian);
        }
    }
}
