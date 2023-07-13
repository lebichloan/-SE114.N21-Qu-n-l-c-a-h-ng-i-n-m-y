package com.example.se114n21.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.se114n21.Interface.ThemhoadonInterface;
import com.example.se114n21.Interface.ThemhoadonInterface2;
import com.example.se114n21.Models.ChiTietHoaDon;
import com.example.se114n21.R;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;


public class ThemhoadonAdapter extends RecyclerView.Adapter<ThemhoadonAdapter.ThemhoadonViewHolder>{

    private List<ChiTietHoaDon> mListCTHD;
    private ThemhoadonInterface themhoadonInterface;
    private ThemhoadonInterface2 themhoadonInterface2;

    public ThemhoadonAdapter(List<ChiTietHoaDon> mListCTHD, ThemhoadonInterface themhoadonInterface, ThemhoadonInterface2 themhoadonInterface2) {
        this.mListCTHD = mListCTHD;
        this.themhoadonInterface = themhoadonInterface;
        this.themhoadonInterface2 = themhoadonInterface2;
    }

    @NonNull
    @Override
    public ThemhoadonViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product_themhoadon, parent, false);
        return new ThemhoadonViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ThemhoadonViewHolder holder, int position) {
        ChiTietHoaDon chiTietHoaDon = mListCTHD.get(position);
        if (chiTietHoaDon == null) {
            return;
        }

        holder.tvId.setText(chiTietHoaDon.getSanPham().getMaSP());
        holder.tvName.setText(chiTietHoaDon.getSanPham().getTenSP());
        holder.tvThanhTien.setText(chiTietHoaDon.getThanhTien().toString());
        holder.btnSoLuong.setText(chiTietHoaDon.getSoLuong().toString());

        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListCTHD.remove(holder.getAbsoluteAdapterPosition());
                notifyItemRemoved(holder.getAbsoluteAdapterPosition());
                notifyItemRangeChanged(holder.getAbsoluteAdapterPosition(), mListCTHD.size());

                themhoadonInterface2.updateButton(mListCTHD.size());
            }
        });

        holder.btnSoLuong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                themhoadonInterface.onClick(chiTietHoaDon, holder.getAbsoluteAdapterPosition());
            }
        });
    }

    public Integer getTongTienHang() {
        Integer tong = 0;

        for (int i=0; i<mListCTHD.size(); i++) {
            tong = tong + mListCTHD.get(i).getThanhTien();
        }

        return tong;
    }

    @Override
    public int getItemCount() {
        if (mListCTHD != null) {
            return mListCTHD.size();
        }
        return 0;
    }

    public class ThemhoadonViewHolder extends RecyclerView.ViewHolder {
        private TextView tvId, tvName, tvThanhTien;
        private Button btnDelete, btnSoLuong;

        public ThemhoadonViewHolder(@NonNull View itemView) {
            super(itemView);

            tvId = itemView.findViewById(R.id.tv_id_product_themhoadon);
            tvName = itemView.findViewById(R.id.tv_name_product_themhoadon);
            tvThanhTien = itemView.findViewById(R.id.tv_thanhtien_themhoadon);
            btnDelete = itemView.findViewById(R.id.btn_delete_themhoadon);
            btnSoLuong = itemView.findViewById(R.id.btn_soluong_themhoadon);
        }
    }
}
