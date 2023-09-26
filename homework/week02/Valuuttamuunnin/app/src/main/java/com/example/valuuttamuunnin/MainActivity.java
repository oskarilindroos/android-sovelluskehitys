package com.example.valuuttamuunnin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private float sourceConversionRate = 1.06f;
    private float destinationConversionRate = 0.94f;
    private String sourceCurrencyName = "EUR";
    private String destinationCurrencyName = "USD";

    EditText sourceCurrencyEditText;
    EditText destinationCurrencyEditText;
    TextView sourceCurrencyTextView;
    TextView destinationCurrencyTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sourceCurrencyEditText = findViewById(R.id.sourceCurrencyEditText);
        destinationCurrencyEditText = findViewById(R.id.destinationCurrencyEditText);

        sourceCurrencyTextView = findViewById(R.id.sourceCurrencyTextView);
        destinationCurrencyTextView = findViewById(R.id.destinationCurrencyTextView);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            sourceCurrencyName = data.getStringExtra("sourceCurrencyName");
            destinationCurrencyName = data.getStringExtra("destinationCurrencyName");

            sourceConversionRate = data.getFloatExtra("sourceConversionRate", sourceConversionRate);
            destinationConversionRate = data.getFloatExtra("destinationConversionRate", destinationConversionRate);

            sourceCurrencyTextView.setText(sourceCurrencyName);
            destinationCurrencyTextView.setText(destinationCurrencyName);

            }
    }

    public void openSettings(View view) {
        Intent intent = new Intent(this, SettingsActivity.class);

        intent.putExtra("sourceCurrencyName", sourceCurrencyName);
        intent.putExtra("destinationCurrencyName", destinationCurrencyName);

        intent.putExtra("sourceConversionRate", sourceConversionRate);
        intent.putExtra("destinationConversionRate", destinationConversionRate);

        startActivityForResult(intent, 2);
    }

    public void convertCurrency(View view) {
        String sourceCurrencyInput = sourceCurrencyEditText.getText().toString();
        float sourceCurrencyAmount = Float.parseFloat(sourceCurrencyInput);

        String destinationCurrencyInput = destinationCurrencyEditText.getText().toString();
        float destinationCurrencyAmount = Float.parseFloat(destinationCurrencyInput);

        float currencyConversion = sourceCurrencyAmount * sourceConversionRate;

        destinationCurrencyEditText.setText(Float.toString(currencyConversion));
    }

    public void switchCurrency(View view) {
        String sourceCurrencyText = sourceCurrencyTextView.getText().toString();
        String destinationCurrencyText = destinationCurrencyTextView.getText().toString();

        sourceCurrencyTextView.setText(destinationCurrencyText);
        destinationCurrencyTextView.setText(sourceCurrencyText);

        float sourceConversionRateOriginal = sourceConversionRate;
        sourceConversionRate = destinationConversionRate;
        destinationConversionRate = sourceConversionRateOriginal;
    }
}