package com.example.project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

public class Record extends AppCompatActivity {

    Button btnOpenDatePicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnOpenDatePicker = findViewById(R.id.btnOpenDatePicker);
        Button a = (Button) findViewById(R.id.toggleButton);
        TextView b = (TextView) findViewById(R.id.textView2);
        ImageButton summary = (ImageButton) findViewById(R.id.graph);
        ImageButton history = (ImageButton) findViewById(R.id.history);
        ImageButton home = (ImageButton)findViewById(R.id.home);
        ImageButton back = (ImageButton)findViewById(R.id.back);

        a.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                b.setText("test");
            }
        });

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Record.this, MainActivity.class));
            }
        });
        summary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Record.this, Summary.class));
            }
        });

        history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Record.this, History.class));
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Record.this, MainActivity.class));
            }
        });
    }

    public void showDatePickerDialog(View view) {
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        // ทำสิ่งที่คุณต้องการกับวันที่ที่ถูกเลือก
                        String selectedDate = day + "/" + (month + 1) + "/" + year;
                        btnOpenDatePicker.setText(selectedDate);
                    }
                },
                year,
                month,
                day
        );

        datePickerDialog.show();
    }
}