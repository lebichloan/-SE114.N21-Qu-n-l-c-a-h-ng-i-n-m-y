package com.example.se114n21.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.se114n21.Interface.BaoCaoInterface;
import com.example.se114n21.Models.DonNhapHang;
import com.example.se114n21.Models.ItemBaoCao;
import com.example.se114n21.R;

import java.util.List;

public class BaoCaoAdapter extends RecyclerView.Adapter<BaoCaoAdapter.BaoCaoViewHolder> {
    private List<ItemBaoCao> mListBaoCao;
    private BaoCaoInterface baoCaoInterface;

    public BaoCaoAdapter(List<ItemBaoCao> mListBaoCao, BaoCaoInterface baoCaoInterface) {
        this.mListBaoCao = mListBaoCao;
        this.baoCaoInterface = baoCaoInterface;
    }

    @NonNull
    @Override
    public BaoCaoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_baocao, parent, false);
        return new BaoCaoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BaoCaoViewHolder holder, int position) {
        ItemBaoCao itemBaoCao = mListBaoCao.get(position);
        if (itemBaoCao == null) {
            return;
        }

        holder.ngay.setText(itemBaoCao.getNgay());
        holder.tongngay.setText(String.valueOf(itemBaoCao.getTongNgay()));
        holder.sodonhang.setText(String.valueOf(itemBaoCao.getSoLuongNgay()));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                baoCaoInterface.onClick(itemBaoCao);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (mListBaoCao != null) {
            return mListBaoCao.size();
        }
        return 0;
    }

    public class BaoCaoViewHolder extends RecyclerView.ViewHolder {
        TextView ngay, tongngay, sodonhang;
        public BaoCaoViewHolder(@NonNull View itemView) {
            super(itemView);

            ngay = itemView.findViewById(R.id.ngay);
            tongngay = itemView.findViewById(R.id.tongngay);
            sodonhang = itemView.findViewById(R.id.sodonhang);
        }
    }
}
