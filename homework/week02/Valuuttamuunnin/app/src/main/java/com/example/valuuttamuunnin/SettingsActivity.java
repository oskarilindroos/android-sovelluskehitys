package com.example.valuuttamuunnin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;

public class SettingsActivity extends AppCompatActivity {

    EditText sourceCurrencyNameEditText;
    EditText destinationCurrencyNameEditText;
    EditText sourceCurrencyRateEditText;
    EditText destinationCurrencyRateEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings2);

        sourceCurrencyNameEditText = findViewById(R.id.settingsSourceCurrencyNameInput);
        destinationCurrencyNameEditText = findViewById(R.id.settingsDestinationCurrencyNameInput);

        sourceCurrencyRateEditText = findViewById(R.id.settingsSourceCurrencyRateInput);
        destinationCurrencyRateEditText = findViewById(R.id.settingsDestinationCurrencyRateInput);

        // Get the initial values from the main activity
        Intent intent = getIntent();
        String sourceCurrencyName = intent.getStringExtra("sourceCurrencyName");
        sourceCurrencyNameEditText.setText(sourceCurrencyName);

        float sourceConversionRate = intent.getFloatExtra("sourceConversionRate", 1.0f);
        sourceCurrencyRateEditText.setText(Float.toString(sourceConversionRate));

        String destinationCurrencyName = intent.getStringExtra("destinationCurrencyName");
        destinationCurrencyNameEditText.setText(destinationCurrencyName);

        float destinationConversionRate = intent.getFloatExtra("destinationConversionRate", 1.0f);
        destinationCurrencyRateEditText.setText(Float.toString(destinationConversionRate));
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent();

        String sourceCurrencyName = sourceCurrencyNameEditText.getText().toString();
        intent.putExtra("sourceCurrencyName", sourceCurrencyName);

        String destinationCurrencyName = destinationCurrencyNameEditText.getText().toString();
        intent.putExtra("destinationCurrencyName", destinationCurrencyName);

        String sourceConversionRate = sourceCurrencyRateEditText.getText().toString();
        intent.putExtra("sourceConversionRate", Float.parseFloat(sourceConversionRate));

        String destinationConversionRate = destinationCurrencyRateEditText.getText().toString();
        intent.putExtra("destinationConversionRate", Float.parseFloat(destinationConversionRate));

        setResult(RESULT_OK, intent);
        finish();
    }
}