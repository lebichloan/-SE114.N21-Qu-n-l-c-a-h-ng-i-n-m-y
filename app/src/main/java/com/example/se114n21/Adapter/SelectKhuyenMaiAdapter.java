package com.example.se114n21.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.se114n21.Interface.SelectKhuyenMaiInterface;
import com.example.se114n21.Models.KhachHang;
import com.example.se114n21.Models.KhuyenMai;
import com.example.se114n21.R;

import java.util.ArrayList;
import java.util.List;

public class SelectKhuyenMaiAdapter extends RecyclerView.Adapter<SelectKhuyenMaiAdapter.SelectKhuyenMaiViewHolder> implements Filterable {
    private List<KhuyenMai> mListKhuyenMai;
    private List<KhuyenMai> mListKhuyenMaiOLD;
    private SelectKhuyenMaiInterface selectKhuyenMaiInterface;

    public SelectKhuyenMaiAdapter(List<KhuyenMai> mListKhuyenMai, SelectKhuyenMaiInterface selectKhuyenMaiInterface) {
        this.mListKhuyenMai = mListKhuyenMai;
        this.mListKhuyenMaiOLD = mListKhuyenMai;
        this.selectKhuyenMaiInterface = selectKhuyenMaiInterface;
    }

    @NonNull
    @Override
    public SelectKhuyenMaiViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_select_khuyenmai, parent, false);
        return new SelectKhuyenMaiViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SelectKhuyenMaiViewHolder holder, int position) {
        KhuyenMai khuyenMai = mListKhuyenMai.get(position);
        if (khuyenMai == null) {
            return;
        }

        holder.id.setText(khuyenMai.getMaKM());
        holder.name.setText(khuyenMai.getTenKM());
        holder.phantram.setText(String.valueOf((int) khuyenMai.getKhuyenMai()));
        holder.giamtoida.setText(String.valueOf(khuyenMai.getGiamToiDa()));
        holder.dontoithieu.setText(String.valueOf(khuyenMai.getDonToiThieu()));
        holder.batdau.setText(khuyenMai.getNgayBD());
        holder.ketthuc.setText(khuyenMai.getNgayKT());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectKhuyenMaiInterface.onClick(khuyenMai);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (mListKhuyenMai != null) {
            return mListKhuyenMai.size();
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
                    mListKhuyenMai = mListKhuyenMaiOLD;
                }
                else {
                    List<KhuyenMai> list = new ArrayList<>();
                    for (KhuyenMai khuyenMai : mListKhuyenMaiOLD) {
                        if (khuyenMai.getMaKM().toLowerCase().contains(strSearch.toLowerCase())
                                || khuyenMai.getTenKM().toLowerCase().contains(strSearch.toLowerCase())) {
                            list.add(khuyenMai);
                        }


                    }

                    mListKhuyenMai = list;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = mListKhuyenMai;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                mListKhuyenMai =  (List<KhuyenMai>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public class SelectKhuyenMaiViewHolder extends RecyclerView.ViewHolder {
        TextView id, name, phantram, giamtoida, batdau, ketthuc, dontoithieu;
        public SelectKhuyenMaiViewHolder(@NonNull View itemView) {
            super(itemView);

            id = itemView.findViewById(R.id.id_khuyenmai);
            name = itemView.findViewById(R.id.name_khuyenmai);
            phantram = itemView.findViewById(R.id.phantram_khuyenmai);
            giamtoida = itemView.findViewById(R.id.giamtoida_khuyenmai);
            dontoithieu = itemView.findViewById(R.id.dontoithieu_khuyenmai);
            batdau = itemView.findViewById(R.id.batdau_khuyenmai);
            ketthuc = itemView.findViewById(R.id.ketthuc_khuyenmai);
        }
    }
}
