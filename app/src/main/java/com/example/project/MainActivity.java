package com.example.project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button record = (Button) findViewById(R.id.incomeButton);
        Button summary = (Button) findViewById(R.id.summaryButton);

        record.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, Record.class));
            }
        });

        summary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, Summary.class));
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