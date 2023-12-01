package com.example.project;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Record extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record);
        Button a = (Button) findViewById(R.id.toggleButton);
        TextView b = (TextView) findViewById(R.id.textView2);
        a.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                b.setText("test");
            }
        });
    }
}