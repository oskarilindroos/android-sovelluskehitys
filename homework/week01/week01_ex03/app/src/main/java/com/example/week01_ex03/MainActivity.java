package com.example.week01_ex03;

import androidx.annotation.ColorLong;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void changeButtonColor(View view) {
        int id = view.getId();

        Button clickedButton = findViewById(id);
        clickedButton.setBackgroundColor(Color.YELLOW);
    }
}