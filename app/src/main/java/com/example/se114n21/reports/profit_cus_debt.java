package com.example.se114n21.reports;

import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import com.example.se114n21.R;
import com.example.se114n21.adapters.CusDebtAdapter;
import com.example.se114n21.models.CusDebt;
import com.example.se114n21.models.Fund;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class profit_cus_debt extends Fragment {

    RecyclerView rvCusDebt;
    List<CusDebt> cusDebtList;
    CusDebtAdapter adapter;




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profit_cus_debt, container, false);

        initUI(view);
        initEvent();

        return view;
    }

    private void initUI(View view)
    {
        rvCusDebt = view.findViewById(R.id.rvCusDebt);
    }

    private void initEvent()
    {
        //set up data for recycle view
        LinearLayoutManager manager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        rvCusDebt.setLayoutManager(manager);

        DividerItemDecoration divider = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
        rvCusDebt.addItemDecoration(divider);

        cusDebtList = new ArrayList<>();
        getDataRealTime();

        adapter = new CusDebtAdapter(getContext());
        adapter.setData(cusDebtList);

        rvCusDebt.setAdapter(adapter);

    }

    private void getDataRealTime()
    {
        List<CusDebt> list = new ArrayList<>();

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("cusDebt");

        Query query = ref.orderByChild("id");

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (cusDebtList != null)
                    cusDebtList.clear();

                for (DataSnapshot dataSnapshot : snapshot.getChildren())
                {
                    CusDebt cusDebt = dataSnapshot.getValue(CusDebt.class);
                        cusDebtList.add(cusDebt);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });
    }
}

