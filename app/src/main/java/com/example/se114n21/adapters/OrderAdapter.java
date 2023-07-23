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
import com.example.se114n21.models.Order;

import java.util.List;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.OrderViewHolder> {

    private Context context;
    private List<Order> listOrder;

    public OrderAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<Order> list)
    {
        listOrder = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_sale_order, parent, false);
        return new OrderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderViewHolder holder, int position) {
        Order order = listOrder.get(position);
        if (order == null)
            return;

        holder.tvDate.setText(order.getDate());
        holder.tvTotal.setText(order.getTotal() + " vnd");
        holder.tvId.setText(order.getId());
    }

    @Override
    public int getItemCount() {
        if (listOrder != null)
            return listOrder.size();
        return 0;
    }

    public class OrderViewHolder extends ViewHolder {

        private TextView tvDate, tvTotal, tvId;

        public OrderViewHolder(@NonNull View itemView) {
            super(itemView);

            tvDate = itemView.findViewById(R.id.tvDateOrder);
            tvTotal = itemView.findViewById(R.id.tvTotalOrder);
            tvId = itemView.findViewById(R.id.tvOrderId);
        }
    }
}
