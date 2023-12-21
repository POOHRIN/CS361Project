package com.example.project;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.ToggleButton;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;


public class Record extends AppCompatActivity {
    public static final String PREFS_NAME = "MyPrefsFile";
    Database dbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        dbHelper = new Database(this);
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

        ToggleButton income = findViewById(R.id.toggleButton);
        ToggleButton outcome = findViewById(R.id.toggleButton2);

        income.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                outcome.setChecked(!isChecked);
            }
        });
        outcome.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                income.setChecked(!isChecked);
            }
        });


        Button btnSave = findViewById(R.id.btnConfirm);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView dateTextView = findViewById(R.id.textView4);
                TextView name = findViewById(R.id.editTextName);
                TextView value = findViewById(R.id.editTextNumber);
                TextView detail = findViewById(R.id.editTextPerson);
                String stringDate = dateTextView.getText().toString();
                String stringName = name.getText().toString();
                String stringValue = value.getText().toString();
                String stringDetail = detail.getText().toString();
                int num = Integer.parseInt(stringValue);
                if(outcome.isChecked()){
                    num = Math.abs(num);
                } else if (income.isChecked()) {
                    num = -num;
                }
                // ตรวจสอบว่าข้อมูลถูกใส่หรือไม่
                if (name.getText().toString().isEmpty() || value.getText().toString().isEmpty() || detail.getText().toString().isEmpty()) {
                    // แสดง Toast ให้ใส่ข้อมูล
                    Toast.makeText(getApplicationContext(), "กรุณาใส่ข้อมูลทุกช่อง", Toast.LENGTH_SHORT).show();
                } else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(Record.this);
                    builder.setTitle("ข้อมูลที่ใส่");
                    builder.setMessage("ชื่อ:" + stringName + "\nวันที่: " + stringDate + "\nจำนวน" + num +"\nรายละเอียด:" + stringDetail);
                    builder.setNegativeButton("ยกเลิก", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    int finalNum = num;
                    int finalNum1 = num;
                    builder.setPositiveButton("ตกลง", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(Record.this, MainActivity.class);
                            //บันทึกลง DB อยู่ด้านล่าง
                            saveExpense();
                        }
                    });

                    builder.show();
                }

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

    private void saveExpense() {

        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // ดึงข้อมูล
        String name = ((TextView) findViewById(R.id.editTextName)).getText().toString();
        String date = ((TextView) findViewById(R.id.textView4)).getText().toString();
        int value = Integer.parseInt(((TextView) findViewById(R.id.editTextNumber)).getText().toString());
        String detail = ((TextView) findViewById(R.id.editTextPerson)).getText().toString();

        // เพิ่มข้อมูลลง DB
        ContentValues values = new ContentValues();
        values.put("name", name);
        values.put("date", date);
        values.put("value", value);
        values.put("detail", detail);

        long newRowId = db.insert("expenses", null, values);

        // Check
        if (newRowId != -1) {
            Toast.makeText(this, "Expense saved with ID: " + newRowId, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Error saving expense", Toast.LENGTH_SHORT).show();
        }

        db.close();
    }
}
