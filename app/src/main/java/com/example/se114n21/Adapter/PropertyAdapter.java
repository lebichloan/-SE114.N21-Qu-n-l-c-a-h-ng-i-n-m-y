package com.example.se114n21.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.se114n21.Models.ThuocTinh;
import com.example.se114n21.R;

import java.util.List;

public class PropertyAdapter extends RecyclerView.Adapter<PropertyAdapter.PropertyViewHolder>{

    private List<ThuocTinh> mListThuocTinh;
    private String code;

    public PropertyAdapter(List<ThuocTinh> mListThuocTinh, String code) {
        this.mListThuocTinh = mListThuocTinh;
        this.code = code;
    }

    @NonNull
    @Override
    public PropertyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_property, parent, false);
        return new PropertyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PropertyViewHolder holder, int position) {
        ThuocTinh thuocTinh = mListThuocTinh.get(position);
        if (thuocTinh == null) {
            return;
        }

        holder.tvName.setText(thuocTinh.getTenTT());
        holder.tvValue.setText(thuocTinh.getGiaTriTT());

        if (code != "detail") {
            holder.btnDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mListThuocTinh.remove(holder.getAbsoluteAdapterPosition());
                    notifyItemRemoved(holder.getAbsoluteAdapterPosition());
                    notifyItemRangeChanged(holder.getAbsoluteAdapterPosition(), mListThuocTinh.size());
                    }

            });
        }
    }

    @Override
    public int getItemCount() {
        if (mListThuocTinh != null) {
            return mListThuocTinh.size();
        }
        return 0;
    }

    public class PropertyViewHolder extends RecyclerView.ViewHolder {
        private TextView tvName, tvValue;
        private Button btnDelete;

        public PropertyViewHolder(@NonNull View itemView) {
            super(itemView);

            tvName = itemView.findViewById(R.id.tv_name_property);
            tvValue = itemView.findViewById(R.id.tv_value_property);
            btnDelete = itemView.findViewById(R.id.btn_delete_property);
        }
    }
}
