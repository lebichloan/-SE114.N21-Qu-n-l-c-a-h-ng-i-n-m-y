package com.example.se114n21.reports;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.example.se114n21.R;
import com.example.se114n21.adapters.StockAdapter;
import com.example.se114n21.models.Stock;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class repo_stock extends Fragment {

    RecyclerView rvStock;
    List<Stock> stockList;
    StockAdapter adapter;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_repo_stock, container, false);

        initUI(view);
        initEvent();

        return view;
    }

    private void initUI(View view)
    {
        rvStock = view.findViewById(R.id.rvStock);
    }

    private void initEvent()
    {
        //set up data for recycle view
        LinearLayoutManager manager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        rvStock.setLayoutManager(manager);

        DividerItemDecoration divider = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
        rvStock.addItemDecoration(divider);

        stockList = new ArrayList<>();
        getDataRealTime();

        adapter = new StockAdapter(getContext());
        adapter.setData(stockList);

        rvStock.setAdapter(adapter);


    }

    private void getDataRealTime()
    {
        List<Stock> list = new ArrayList<>();

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("stock");

        Query query = ref.orderByChild("id");

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (stockList != null)
                    stockList.clear();

                for (DataSnapshot dataSnapshot : snapshot.getChildren())
                {
                    Stock stock = dataSnapshot.getValue(Stock.class);
                    stockList.add(stock);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });
    }
}

