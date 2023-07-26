package com.example.se114n21.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.se114n21.Interface.BaoCaoInterface;
import com.example.se114n21.Interface.LoiNhuanInterface;
import com.example.se114n21.Models.ItemBaoCao;
import com.example.se114n21.Models.ItemLoiNhuan;
import com.example.se114n21.R;

import java.util.List;

public class LoiNhuanAdapter extends RecyclerView.Adapter<LoiNhuanAdapter.LoiNhuanViewHolder>{

    private List<ItemLoiNhuan> mListLoiNhuan;

    public LoiNhuanAdapter(List<ItemLoiNhuan> mListLoiNhuan, LoiNhuanInterface loiNhuanInterface) {
        this.mListLoiNhuan = mListLoiNhuan;
        this.loiNhuanInterface = loiNhuanInterface;
    }

    private LoiNhuanInterface loiNhuanInterface;

    @NonNull
    @Override
    public LoiNhuanViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_baocao, parent, false);
        return new LoiNhuanViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LoiNhuanViewHolder holder, int position) {
        ItemLoiNhuan itemLoiNhuan = mListLoiNhuan.get(position);
        if (itemLoiNhuan == null) {
            return;
        }

        holder.ngay.setText(itemLoiNhuan.getNgay());
        holder.tongngay.setText(String.valueOf(itemLoiNhuan.getTongNgay()));
        holder.sodonhang.setText(String.valueOf(itemLoiNhuan.getSoLuongNgay()));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loiNhuanInterface.onClick(itemLoiNhuan);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (mListLoiNhuan != null) {
            return mListLoiNhuan.size();
        }
        return 0;
    }

    public class LoiNhuanViewHolder extends RecyclerView.ViewHolder {
        TextView ngay, tongngay, sodonhang;

        public LoiNhuanViewHolder(@NonNull View itemView) {
            super(itemView);

            ngay = itemView.findViewById(R.id.ngay);
            tongngay = itemView.findViewById(R.id.tongngay);
            sodonhang = itemView.findViewById(R.id.sodonhang);
        }
    }
}
