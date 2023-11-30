package com.example.project;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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