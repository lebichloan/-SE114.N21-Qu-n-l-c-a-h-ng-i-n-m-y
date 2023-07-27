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
import com.example.se114n21.models.Stock;

import java.util.List;

public class StockAdapter extends RecyclerView.Adapter<StockAdapter.StockViewHolder> {

    private Context context;
    private List<Stock> listStock;

    public StockAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<Stock> list)
    {
        listStock = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public StockViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_repo_stock, parent, false);
        return new StockViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StockViewHolder holder, int position) {
        Stock stock = listStock.get(position);
        if (stock == null)
            return;

        holder.tvName.setText(stock.getName());
        holder.tvId.setText(stock.getId());
        holder.tvTotal.setText(stock.getTotal() + "");
        holder.tvAmount.setText("SL: " + stock.getAmount());
        holder.tvCost.setText("Giá vốn: " + stock.getCost());

    }

    @Override
    public int getItemCount() {
        if (listStock != null)
            return listStock.size();
        return 0;
    }

    public class StockViewHolder extends ViewHolder {

        private TextView tvName, tvId, tvTotal, tvAmount, tvCost;

        public StockViewHolder(@NonNull View itemView) {
            super(itemView);

            tvName = itemView.findViewById(R.id.tvNameStock);
            tvId = itemView.findViewById(R.id.tvIdStock);
            tvTotal = itemView.findViewById(R.id.tvTotalStock);
            tvAmount = itemView.findViewById(R.id.tvAmountStock);
            tvCost = itemView.findViewById(R.id.tvCostStock);
        }
    }
}
