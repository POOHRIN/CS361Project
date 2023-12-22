package com.example.project;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import androidx.appcompat.app.AppCompatActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

public class History extends AppCompatActivity {

    private  Database database;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        ImageButton summary = (ImageButton) findViewById(R.id.graph);
        ImageButton home = (ImageButton)findViewById(R.id.home);
        ImageButton back = (ImageButton)findViewById(R.id.back);
        ImageButton setting = (ImageButton) findViewById(R.id.settingBtn);

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(History.this, MainActivity.class));
            }
        });
        summary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(History.this, Summary.class));
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(History.this, MainActivity.class));
            }
        });

        database = new Database(History.this);
        try{
            String[] FROM = {"id", "name", "date", "value", "detail"};
            String ORDER_BY = "date" + " DESC";
            SQLiteDatabase db = database.getReadableDatabase();
            Cursor cursor = db.query("expenses", FROM, null, null, null, null, ORDER_BY);
            final ListView listView = (ListView)findViewById(R.id.listView);
            final ArrayList<HashMap<String, String>> MyArrList = new ArrayList<HashMap<String, String>>();
            HashMap<String, String> map;
            while(cursor.moveToNext()) {
                map = new HashMap<String, String>();
                map.put("name", cursor.getString(1));
                map.put("date", cursor.getString(2));
                map.put("value", String.valueOf(cursor.getFloat(3)));
                map.put("detail", cursor.getString(4));
                MyArrList.add(map);
            }
            cursor.close();

            Collections.sort(MyArrList, new Comparator<HashMap<String, String>>() {
                final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());

                @Override
                public int compare(HashMap<String, String> lhs, HashMap<String, String> rhs) {
                    try {
                        Date date1 = dateFormat.parse(lhs.get("date"));
                        Date date2 = dateFormat.parse(rhs.get("date"));
                        return date2.compareTo(date1);
                    } catch (ParseException e) {
                        e.printStackTrace();
                        return 0; // Handle the exception or log it
                    }
                }
            });

            SimpleAdapter sAdapt;
            sAdapt = new SimpleAdapter( History.this, MyArrList, R.layout.activity_column,
                    new String[] {"name", "date", "value", "detail"},
                    new int[] {R.id.col_name, R.id.col_datetime, R.id.col_total, R.id.col_description} );
            listView.setAdapter(sAdapt);

        }finally{
            database.close();
        }
    }
}
