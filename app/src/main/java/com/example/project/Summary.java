package com.example.project;

import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.NumberPicker;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

public class Summary extends AppCompatActivity {

    // variable for our bar chart
    BarChart barChart;

    // variable for our bar data.
    BarData barData;

    // variable for our bar data set.
    BarDataSet barDataSet;

    // array list for storing entries.
    ArrayList barEntriesArrayList;

    int income = 0, expense = 0, month = 0, year = 0;

    private  Database database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary);

        ImageButton history = (ImageButton) findViewById(R.id.history);
        ImageButton home = (ImageButton)findViewById(R.id.home);
        ImageButton back = (ImageButton)findViewById(R.id.back);
        ImageButton setting = (ImageButton) findViewById(R.id.settingBtn);

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
        barDataSet.setColors(Color.GREEN,Color.RED);

        // setting text color.
        barDataSet.setValueTextColor(Color.BLACK);

        // setting text size
        barDataSet.setValueTextSize(16f);
        barChart.getDescription().setEnabled(false);


        chooseMonth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(result);
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

    private void getBarEntries(int income, int expense) {
        // creating a new array list
        barEntriesArrayList = new ArrayList<>();

        // adding new entry to our array list with bar
        // entry and passing x and y axis value to it.
        barEntriesArrayList.add(new BarEntry(1f, income));
        barEntriesArrayList.add(new BarEntry(2f, expense));
    }

    private static final int MAX_YEAR = 2099;
    public void showDialog(TextView result){
        final Dialog dialog = new Dialog(Summary.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.month_year_picker_dialog);

        Button dialogButton = (Button) dialog.findViewById(R.id.okBtn);

        Calendar cal = Calendar.getInstance();

        final NumberPicker monthPicker = (NumberPicker) dialog.findViewById(R.id.picker_month);
        final NumberPicker yearPicker = (NumberPicker) dialog.findViewById(R.id.picker_year);

        monthPicker.setMinValue(1);
        monthPicker.setMaxValue(12);
        monthPicker.setValue(cal.get(Calendar.MONTH) + 1);

        int yearC = cal.get(Calendar.YEAR);
        yearPicker.setMinValue(2000);
        yearPicker.setMaxValue(Calendar.getInstance().get(Calendar.YEAR));
        yearPicker.setValue(yearC);
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                income = 0;
                expense = 0;
                month = monthPicker.getValue();
                year = yearPicker.getValue();

                database = new Database(Summary.this);
                try{
                    String[] FROM = {"id", "name", "date", "value", "detail"};
                    String ORDER_BY = "id" + " DESC";
                    SQLiteDatabase db = database.getReadableDatabase();
                    Cursor cursor = db.query("expenses", FROM, null, null, null, null, ORDER_BY);
                    final ListView listView = (ListView)findViewById(R.id.listView);
                    final ArrayList<HashMap<String, String>> MyArrList = new ArrayList<HashMap<String, String>>();
                    while(cursor.moveToNext()) {
                        String date = cursor.getString(2);
                        float value = cursor.getFloat(3);
                        String[] parts = date.split("/");
                        int monthD = Integer.parseInt(parts[1]);
                        int yearD = Integer.parseInt(parts[2]);
                        if(monthD == month && yearD == year){
                            if (value < 0){
                                expense -= value;
                            }else if (value > 0){
                                income += value;
                            }
                        }
                    }


                }finally{
                    database.close();
                }

                result.setText("Month " + month + " Year " + year);

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
                barDataSet.setColors(Color.GREEN,Color.RED);

                // setting text color.
                barDataSet.setValueTextColor(Color.BLACK);

                // setting text size
                barDataSet.setValueTextSize(16f);
                barChart.getDescription().setEnabled(false);

                barChart.invalidate();

                dialog.dismiss();
            }
        });

        dialog.show();

    }
}