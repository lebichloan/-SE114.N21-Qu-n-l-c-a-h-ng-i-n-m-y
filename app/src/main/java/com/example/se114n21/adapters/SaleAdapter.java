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
import com.example.se114n21.models.Sale;

import java.util.List;

public class SaleAdapter extends RecyclerView.Adapter<SaleAdapter.SaleViewHolder> {

    private Context context;
    private List<Sale> listSale;

    public SaleAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<Sale> list)
    {
        listSale = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public SaleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_sale_sell, parent, false);
        return new SaleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SaleViewHolder holder, int position) {
        Sale sale = listSale.get(position);
        if (sale == null)
            return;

        holder.tvDate.setText(sale.getDate());
        holder.tvCost.setText(sale.getTotal() + " vnd");
        holder.tvOrder.setText(sale.getOrder() + "");
    }

    @Override
    public int getItemCount() {
        if (listSale != null)
            return listSale.size();
        return 0;
    }

    public class SaleViewHolder extends ViewHolder {

        private TextView tvDate, tvCost, tvOrder;

        public SaleViewHolder(@NonNull View itemView) {
            super(itemView);

            tvDate = itemView.findViewById(R.id.tvDateSale);
            tvCost = itemView.findViewById(R.id.tvCostSale);
            tvOrder = itemView.findViewById(R.id.tvOrderSale);
        }
    }
}
