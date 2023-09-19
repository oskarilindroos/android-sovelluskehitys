package com.example.simpleapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView helloTextView = findViewById(R.id.helloTextView);
        helloTextView.setText(R.string.hello_from_code);
    }

    public void handleButtonClick(View view) {
        TextView helloTextView = findViewById(R.id.helloTextView);
        helloTextView.setText(R.string.button_clicked);
    }
}