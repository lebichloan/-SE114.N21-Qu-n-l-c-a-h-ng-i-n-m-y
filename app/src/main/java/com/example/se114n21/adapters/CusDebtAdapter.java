package com.example.se114n21.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;

import com.example.se114n21.R;
import com.example.se114n21.models.CusDebt;

import java.util.List;

public class CusDebtAdapter extends RecyclerView.Adapter<CusDebtAdapter.CusDebtViewHolder> {

    private Context context;
    private List<CusDebt> listCusDebt;

    public CusDebtAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<CusDebt> list)
    {
        listCusDebt = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CusDebtViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_profit_cus_debt, parent, false);
        return new CusDebtViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CusDebtViewHolder holder, int position) {
        CusDebt cusDebt = listCusDebt.get(position);
        if (cusDebt == null)
            return;

        holder.tvName.setText(cusDebt.getName());
        holder.tvId.setText(cusDebt.getId());
        holder.tvTotal.setText(cusDebt.getTotal() + "vnd");
    }

    @Override
    public int getItemCount() {
        if (listCusDebt != null)
            return listCusDebt.size();
        return 0;
    }

    public class CusDebtViewHolder extends ViewHolder {

        private TextView tvName, tvId, tvTotal;

        public CusDebtViewHolder(@NonNull View itemView) {
            super(itemView);

            tvName = itemView.findViewById(R.id.tvNameCus);
            tvId = itemView.findViewById(R.id.tvIdCus);
            tvTotal = itemView.findViewById(R.id.tvTotalCus);

        }
    }
}
