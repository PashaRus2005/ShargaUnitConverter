package com.example.unit_converter;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {

    private EditText inputField;
    private Spinner fromUnitSpinner, toUnitSpinner;
    private TextView resultTextView;

    private final String[] lengthUnits = {"Метр", "Сантиметр", "Миллиметр", "Дюйм", "Фут", "Ярд"};
    private final String[] massUnits = {"Килограмм", "Грамм", "Унция", "Фунт"};
    private final String[] volumeUnits = {"Литр", "Миллилитр", "Галлон", "Кубический сантиметр"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        inputField = findViewById(R.id.inputField);
        fromUnitSpinner = findViewById(R.id.fromUnitSpinner);
        toUnitSpinner = findViewById(R.id.toUnitSpinner);
        resultTextView = findViewById(R.id.resultTextView);

        setupSpinners();

        inputField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                convertUnits();
            }
        });

        fromUnitSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                convertUnits();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        toUnitSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                convertUnits();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
    }

    private void setupSpinners() {

        ArrayAdapter<String> lengthAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, lengthUnits);
        new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, massUnits);
        new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, volumeUnits);

        fromUnitSpinner.setAdapter(lengthAdapter);
        toUnitSpinner.setAdapter(lengthAdapter);

    }

    private void convertUnits() {
        String fromUnit = (String) fromUnitSpinner.getSelectedItem();
        String toUnit = (String) toUnitSpinner.getSelectedItem();
        double inputValue;

        try {
            inputValue = Double.parseDouble(inputField.getText().toString());
        } catch (NumberFormatException e) {
            resultTextView.setText("");
            return;
        }

        double result = convertValue(inputValue, fromUnit, toUnit);
        resultTextView.setText(new DecimalFormat("#.###").format(result));
    }

    private double convertValue(double value, String fromUnit, String toUnit) {
        double meters;
        switch (fromUnit) {
            case "Метр":
                meters = value;
                break;
            case "Сантиметр":
                meters = value / 100;
                break;
            case "Миллиметр":
                meters = value / 1000;
                break;
            case "Дюйм":
                meters = value * 0.0254;
                break;
            case "Фут":
                meters = value * 0.3048;
                break;
            case "Ярд":
                meters = value * 0.9144;
                break;
            default:
                return 0;
        }

        switch (toUnit) {
            case "Метр":
                return meters;
            case "Сантиметр":
                return meters * 100;
            case "Миллиметр":
                return meters * 1000;
            case "Дюйм":
                return meters / 0.0254;
            case "Фут":
                return meters / 0.3048;
            case "Ярд":
                return meters / 0.9144;
            default:
                return 0;
        }
    }
}
