package com.example.project;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import androidx.appcompat.app.AppCompatActivity;

import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button record = findViewById(R.id.incomeButton);
        Button summary = findViewById(R.id.summaryButton);

        Button record = (Button) findViewById(R.id.incomeButton);
        Button summary = (Button) findViewById(R.id.summaryButton);
        TextView history = (TextView) findViewById(R.id.historyButton);

        record.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, Record.class)));
        summary.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, Summary.class)));
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
    protected void onCreate(){
        ImageButton myImageView = findViewById(R.id.imageButton);
        // Get current dimensions
        int width = myImageView.getLayoutParams().width;
        int height = myImageView.getLayoutParams().height;
        // Double the dimensions
        myImageView.getLayoutParams().width = 2 * width;
        myImageView.getLayoutParams().height = 2 * height;
        // Apply the new dimensions
        myImageView.requestLayout();
    }
}