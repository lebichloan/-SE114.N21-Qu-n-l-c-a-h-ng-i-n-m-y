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

public class CTHDAdapter extends RecyclerView.Adapter<CTHDAdapter.CTHDViewHolder>{
    private List<ChiTietHoaDon> mListCTHD;

    public CTHDAdapter(List<ChiTietHoaDon> mListCTHD) {
        this.mListCTHD = mListCTHD;
    }

    @NonNull
    @Override
    public CTHDViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cthd, parent, false);
        return new CTHDViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CTHDViewHolder holder, int position) {
        ChiTietHoaDon chiTietHoaDon = mListCTHD.get(position);
        if (chiTietHoaDon == null) {
            return;
        }

        holder.ten.setText(chiTietHoaDon.getSanPham().getTenSP());
        holder.soluong.setText(String.valueOf(chiTietHoaDon.getSoLuong()));
        holder.gia.setText(String.valueOf(chiTietHoaDon.getThanhTien()));
    }

    @Override
    public int getItemCount() {
        if (mListCTHD != null) {
            return mListCTHD.size();
        }
        return 0;
    }

    public class CTHDViewHolder extends RecyclerView.ViewHolder {
        TextView ten, soluong, gia;
        public CTHDViewHolder(@NonNull View itemView) {
            super(itemView);

            ten = itemView.findViewById(R.id.TenSP);
            soluong = itemView.findViewById(R.id.SoLuongSP);
            gia = itemView.findViewById(R.id.ThanhTienSP);
        }
    }
}
