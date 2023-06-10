package com.example.se114n21.reports;

import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import com.example.se114n21.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class sell_profit extends Fragment {

    TextView tvDate, tvDate1;
    Button btnDate, btnDate1;
    Calendar calendar = Calendar.getInstance();
    int year = calendar.get(Calendar.YEAR);
    int month = calendar.get(Calendar.MONTH);
    int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

    private DatePickerDialog createDatePicker(TextView tv)
    {
        DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int Selectedyear, int Selectedmonth, int SelecteddayOfMonth) {
                // Xử lý sự kiện khi người dùng chọn ngày
                calendar.set(Calendar.YEAR, Selectedyear);
                calendar.set(Calendar.MONTH, Selectedmonth);
                calendar.set(Calendar.DAY_OF_MONTH, SelecteddayOfMonth);

                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                String selectedDate = sdf.format(calendar.getTime());

                tv.setText(selectedDate);
            }
        }, year, month, dayOfMonth);
        return datePickerDialog;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sell_profit, container, false);

        tvDate = view.findViewById(R.id.tvDate);
        tvDate1 = view.findViewById(R.id.tvDate1);

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String today = sdf.format(calendar.getTime());

        tvDate.setText(today);
        tvDate1.setText(today);

        btnDate = view.findViewById(R.id.btnDate);
        btnDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePicker = createDatePicker(tvDate);
                datePicker.show();
            }
        });

        btnDate1 = view.findViewById(R.id.btnDate1);
        btnDate1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePicker = createDatePicker(tvDate1);
                datePicker.show();
            }
        });

        return view;
    }
}