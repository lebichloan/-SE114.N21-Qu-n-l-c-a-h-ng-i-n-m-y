package com.example.se114n21.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.se114n21.Models.ChiTietHoaDon;
import com.example.se114n21.Models.HoaDon;
import com.example.se114n21.R;

import java.util.List;

public class AdapterChitietHD extends RecyclerView.Adapter<AdapterChitietHD.ChitietHDViewHolder> {
    List<ChiTietHoaDon> chiTietHoaDonList;
    public AdapterChitietHD(List<ChiTietHoaDon> listchitiet)
    {
        this.chiTietHoaDonList = listchitiet;
    }
    @NonNull
    @Override
    public ChitietHDViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chitiethd, parent,false);
        return new ChitietHDViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterChitietHD.ChitietHDViewHolder holder, int position) {
        ChiTietHoaDon hd = chiTietHoaDonList.get(position);
        if (hd == null)
        {
            return;
        }
        holder.tensp.setText(hd.getSanPham().getTenSP());
        holder.SL.setText(hd.getSoLuong().toString());
    }

    @Override
    public int getItemCount() {
        if (chiTietHoaDonList != null)
        {
            return chiTietHoaDonList.size();
        }
        return 0;
    }
    public class ChitietHDViewHolder extends RecyclerView.ViewHolder {
        private TextView tensp;
        private TextView SL;

        public ChitietHDViewHolder(@NonNull View item) {
            super(item);
            InitUI();
        }
        public void InitUI()
        {
            tensp = itemView.findViewById(R.id.tenhanghoa);
            SL = itemView.findViewById(R.id.soluonghanghoa);
        }
    }
}
