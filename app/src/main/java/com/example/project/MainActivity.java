package com.example.project;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import androidx.appcompat.app.AppCompatActivity;
import android.app.Dialog;

import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button income = (Button) findViewById(R.id.incomeButton);
        Button expense = (Button) findViewById(R.id.expenseButton);
        ImageButton summary = (ImageButton) findViewById(R.id.graph);
        ImageButton history = (ImageButton) findViewById(R.id.history);

        income.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, Record.class));
            }
        });
        expense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO : ทำให้อ่านข้อมูลจากตัวเครื่อง

                Intent intent = getIntent();
                String name = intent.getStringExtra("Name");
                String date = intent.getStringExtra("Date");
                int value = intent.getIntExtra("Value", 0);
                String detail = intent.getStringExtra("Detail");

                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("ข้อมูลที่ได้รับ");
                builder.setMessage("ชื่อ:" + name + "\nวันที่: " + date + "\nจำนวน" + value +"\nรายละเอียด:" + detail);

                //startActivity(new Intent(MainActivity.this, Record.class));
            }
        });
        summary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, Summary.class));
            }
        });

        history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, History.class));
            }
        });

    }
    @Override
    public void onConfigurationChanged(Configuration newConfig) {

        super.onConfigurationChanged(newConfig);
        final TextView textView = findViewById(R.id.textView);
        textView.setTextSize(newConfig.fontScale*32);
        Toast.makeText(getApplicationContext(),newConfig.fontScale+"",Toast.LENGTH_LONG).show();
    }

}