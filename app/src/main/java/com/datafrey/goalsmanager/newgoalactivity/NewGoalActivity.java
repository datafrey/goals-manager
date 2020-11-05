package com.datafrey.goalsmanager.newgoalactivity;

import android.os.Bundle;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import com.datafrey.goalsmanager.R;

public class NewGoalActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_goal);

        getWindow().setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        );

        Spinner categoriesSpinner = findViewById(R.id.categoriesSpinner);
        String[] categories = getResources().getStringArray(R.array.goal_categories);
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(
                this, android.R.layout.simple_spinner_item, categories);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categoriesSpinner.setAdapter(spinnerAdapter);

        ((DatePicker) findViewById(R.id.deadlineDatePicker))
                .setMinDate(System.currentTimeMillis() - 1000);

        findViewById(R.id.cancelButton).setOnClickListener(v -> finish());
    }
}