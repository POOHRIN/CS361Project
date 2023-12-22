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
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;


public class Record extends AppCompatActivity {
    public static final String PREFS_NAME = "MyPrefsFile";
    Database dbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record);
        dbHelper = new Database(this);

        ImageButton summary = (ImageButton) findViewById(R.id.graph);
        ImageButton history = (ImageButton) findViewById(R.id.history);
        ImageButton home = (ImageButton)findViewById(R.id.home);
        ImageButton back = (ImageButton)findViewById(R.id.back);
        ImageButton setting = (ImageButton) findViewById(R.id.settingBtn);

        Button btnOpenDatePicker = findViewById(R.id.btnOpenDatePicker);

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

        btnOpenDatePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog(v);
            }
        });

        ToggleButton income = findViewById(R.id.toggleButton);
        ToggleButton outcome = findViewById(R.id.toggleButton2);

        boolean incomeChecked = getIntent().getBooleanExtra("incomeButtonState", false);
        income.setChecked(incomeChecked);
        outcome.setChecked(!incomeChecked);

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
                // ตรวจสอบว่าข้อมูลถูกใส่หรือไม่
                if (name.getText().toString().isEmpty() || value.getText().toString().isEmpty() || detail.getText().toString().isEmpty()) {
                    // แสดง Toast ให้ใส่ข้อมูล
                    String enterData = getString(R.string.enterdata);
                    Toast.makeText(getApplicationContext(), enterData, Toast.LENGTH_SHORT).show();
                } else {
                    String stringDate = dateTextView.getText().toString();
                    String stringName = name.getText().toString();
                    String stringValue = value.getText().toString();
                    String stringDetail = detail.getText().toString();
                    float num = Float.parseFloat(stringValue);
                    if(outcome.isChecked()){
                        num = -num;
                    }
                    AlertDialog.Builder builder = new AlertDialog.Builder(Record.this);
                    builder.setTitle(R.string.record);
                    String nameA = getString(R.string.name);
                    String dateA = getString(R.string.day);
                    String valueA = getString(R.string.value);
                    String detailA = getString(R.string.details);
                    String message = nameA + ": " + stringName + "\n" + dateA + ": " + stringDate + "\n" + valueA + ": " + num + "\n" + detailA + ": " + stringDetail;
                    builder.setMessage(message);
                    builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    float finalNum = num;
                    builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //Intent intent = new Intent(Record.this, MainActivity.class);
                            //บันทึกลง DB อยู่ด้านล่าง
                            saveExpense(finalNum);
                            //startActivity(intent);
                        }
                    });

                    builder.show();
                }

            }
        });

        Button btnDismiss = findViewById(R.id.btnCancel);

        btnDismiss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView dateTextView = findViewById(R.id.textView4);
                TextView name = findViewById(R.id.editTextName);
                TextView value = findViewById(R.id.editTextNumber);
                TextView detail = findViewById(R.id.editTextPerson);
                dateTextView.setText(R.string.day);
                name.setText(null);
                value.setText(null);
                detail.setText(null);
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

    private void saveExpense(float value) {

        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // ดึงข้อมูล
        String name = ((TextView) findViewById(R.id.editTextName)).getText().toString();
        String date = ((TextView) findViewById(R.id.textView4)).getText().toString();
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
