package com.example.project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.DatePicker;
import android.app.DatePickerDialog;
import android.app.AlertDialog;
import android.content.DialogInterface;

import java.util.Calendar;


public class Record extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record);

        Button a = (Button) findViewById(R.id.toggleButton);
        TextView b = (TextView) findViewById(R.id.textView2);
        ImageButton summary = (ImageButton) findViewById(R.id.graph);
        ImageButton history = (ImageButton) findViewById(R.id.history);
        ImageButton home = (ImageButton)findViewById(R.id.home);
        ImageButton back = (ImageButton)findViewById(R.id.back);
        setContentView(R.layout.activity_record);

        Button btnOpenDatePicker = findViewById(R.id.btnOpenDatePicker);
        btnOpenDatePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog(v);
            }
        });
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

        Button btnSave = findViewById(R.id.btnConfirm);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView dateTextView = findViewById(R.id.textView4);
                String selectedDate = dateTextView.getText().toString();

                AlertDialog.Builder builder = new AlertDialog.Builder(Record.this);
                builder.setTitle("ข้อมูลทีใส่");
                builder.setMessage("วันที่ที่คุณเลือกคือ: " + selectedDate);
                builder.setNegativeButton("ยกเลิก", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                builder.setPositiveButton("ตกลง", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // TODO: เดี๋ยวให้มาใส่ DB ในนี้
                    }
                });

                builder.show();
            }
        });
    }


    public void showDatePickerDialog(View view) {
        // รับเวลาปัจจุบัน
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        if (view.isShown()) {
                            String selectedDate = dayOfMonth + "/" + (monthOfYear + 1) + "/" + year;

                            TextView date = findViewById(R.id.textView4);
                            date.setText(selectedDate);
                        }
                    }
                },
                year, month, day);

        datePickerDialog.show();
    }

}
