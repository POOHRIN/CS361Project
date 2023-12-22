package com.example.project;

import android.content.Intent;
import android.content.res.Configuration;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    private  Database database;

    float value = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button income = (Button) findViewById(R.id.incomeButton);
        Button expense = (Button) findViewById(R.id.expenseButton);
        ImageButton summary = (ImageButton) findViewById(R.id.graph);
        ImageButton history = (ImageButton) findViewById(R.id.history);
        ImageButton setting = (ImageButton) findViewById(R.id.settingBtn);
        TextView net = (TextView) findViewById(R.id.net);

        database = new Database(MainActivity.this);
        try{
            String[] FROM = {"id", "name", "date", "value", "detail"};
            String ORDER_BY = "id" + " DESC";
            SQLiteDatabase db = database.getReadableDatabase();
            Cursor cursor = db.query("expenses", FROM, null, null, null, null, ORDER_BY);
            HashMap<String, String> map;
            while(cursor.moveToNext()) {
                value += cursor.getFloat(3);
            }

        }finally{
            database.close();
        }

        String netAmountMainString = getString(R.string.netAmount_main);
        String resultText = netAmountMainString + "\n" + value;
        net.setText(resultText);
        income.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Record.class);

                // Pass a boolean value indicating whether the ToggleButton should be checked
                boolean incomeChecked = true;  // Change this value as needed
                intent.putExtra("incomeButtonState", incomeChecked);

                startActivity(intent);
            }
        });
        expense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Record.class);


                boolean incomeChecked = false;
                intent.putExtra("outcomeButtonState", incomeChecked);

                startActivity(intent);
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

        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, Setting.class));
            }
        });

    }
    @Override
    public void onConfigurationChanged(Configuration newConfig) {

        super.onConfigurationChanged(newConfig);
        final TextView textView = findViewById(R.id.net);
        textView.setTextSize(newConfig.fontScale*32);
        Toast.makeText(getApplicationContext(),newConfig.fontScale+"",Toast.LENGTH_LONG).show();
    }
}