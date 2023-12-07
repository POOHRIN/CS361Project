package com.example.project;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

public class Summary extends AppCompatActivity {

    // variable for our bar chart
    BarChart barChart;

    // variable for our bar data.
    BarData barData;

    // variable for our bar data set.
    BarDataSet barDataSet;

    // array list for storing entries.
    ArrayList barEntriesArrayList;

    int income = 0;
    int expense = 0;

    Boolean shown_dialog = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary);

        ImageButton history = (ImageButton) findViewById(R.id.history);
        ImageButton home = (ImageButton)findViewById(R.id.home);
        ImageButton back = (ImageButton)findViewById(R.id.back);

        // initializing variable for bar chart.
        barChart = findViewById(R.id.BarChart);
        Button chooseMonth = (Button) findViewById(R.id.chooseMonthbtn);

        TextView result = (TextView) findViewById(R.id.result);

        // calling method to get bar entries.
        getBarEntries(income, expense);

        // creating a new bar data set.
        barDataSet = new BarDataSet(barEntriesArrayList, "Income & Expense");

        // creating a new bar data and
        // passing our bar data set.
        barData = new BarData(barDataSet);

        // below line is to set data
        // to our bar chart.
        barChart.setData(barData);

        // adding color to our bar data set.
        barDataSet.setColors(ColorTemplate.MATERIAL_COLORS);

        // setting text color.
        barDataSet.setValueTextColor(Color.BLACK);

        // setting text size
        barDataSet.setValueTextSize(16f);
        barChart.getDescription().setEnabled(false);

        chooseMonth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                barChart.invalidate();
            }
        });

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Summary.this, MainActivity.class));
            }
        });

        history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Summary.this, History.class));
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Summary.this, MainActivity.class));
            }
        });
    }

    private void getBarEntries(int a, int b) {
        // creating a new array list
        barEntriesArrayList = new ArrayList<>();

        // adding new entry to our array list with bar
        // entry and passing x and y axis value to it.
        barEntriesArrayList.add(new BarEntry(1f, a));
        barEntriesArrayList.add(new BarEntry(2f, b));
    }
}