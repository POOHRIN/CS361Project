package com.example.project;

import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

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

        // initializing variable for bar chart.
        barChart = findViewById(R.id.BarChart);
        Button show = (Button) findViewById(R.id.showButton);

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

        show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText monthET = (EditText) findViewById(R.id.monthInput);
                EditText yearET = (EditText) findViewById(R.id.yearInput);
                int month = Integer.parseInt(String.valueOf(monthET.getText()));
                int year = Integer.parseInt(String.valueOf(yearET.getText()));

                if (month < 1 || month > 12 || year < 1990 || year > 2060) {
                    if (!shown_dialog) {
                        shown_dialog = true;
                        final AlertDialog.Builder viewDialog;
                        viewDialog = new AlertDialog.Builder(Summary.this);
                        viewDialog.setTitle("Alert");
                        viewDialog.setMessage("Enter the correct month and year format" +
                                "\n enter year between 1990 to 2060");
                        viewDialog.setPositiveButton("OK",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                        shown_dialog = false;
                                    }
                                });
                        viewDialog.show();
                    }
                } else {
                    result.setText(month + "/" + year);
                    income = 20000;
                    expense = 18250;

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

                    barChart.invalidate();
                }
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