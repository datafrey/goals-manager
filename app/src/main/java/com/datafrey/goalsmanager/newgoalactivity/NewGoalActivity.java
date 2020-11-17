package com.datafrey.goalsmanager.newgoalactivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.datafrey.goalsmanager.R;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Calendar;
import java.util.Date;

public class NewGoalActivity extends AppCompatActivity {

    private TextInputLayout titleTextInput, descriptionTextInput;
    private Spinner categoriesSpinner;
    private DatePicker deadlineDatePicker;
    private ExtendedFloatingActionButton addGoalButton;

    private NewGoalActivityViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_goal);

        setupView();

        titleTextInput = findViewById(R.id.titleTextInput);
        descriptionTextInput = findViewById(R.id.descriptionTextInput);
        categoriesSpinner = findViewById(R.id.categoriesSpinner);
        deadlineDatePicker = findViewById(R.id.deadlineDatePicker);
        addGoalButton = findViewById(R.id.addGoalButton);

        viewModel = new ViewModelProvider(
                this,
                new NewGoalActivityViewModelFactory(getApplication())
        ).get(NewGoalActivityViewModel.class);

        setupErrorMessages();

        findViewById(R.id.cancelButton).setOnClickListener(v -> finish());
        addGoalButton.setOnClickListener(v -> onAddGoalButtonClick());

        viewModel.getAddGoalButtonEnabled().observe(this,
                enabled -> addGoalButton.setEnabled(enabled));

        viewModel.getNewGoalAdditionResult().observe(this, success -> {
            if (success != null) {
                Toast.makeText(
                        this,
                        success ? "New goal successfully added!" : "Something went wrong...",
                        Toast.LENGTH_SHORT
                ).show();

                viewModel.uiReactedToNewGoalAdditionResult();
                finish();
            }
        });
    }

    private void setupView() {
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
    }

    private void setupErrorMessages() {
        viewModel.getTitleInputErrorMessage().observe(this,
                errorMessage -> titleTextInput.setError(errorMessage));

        titleTextInput.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                viewModel.removeTitleInputErrorMessage();
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        viewModel.getDescriptionInputErrorMessage().observe(this,
                errorMessage -> descriptionTextInput.setError(errorMessage));

        descriptionTextInput.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                viewModel.removeDescriptionInputErrorMessage();
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    private void onAddGoalButtonClick() {
        viewModel.setAddGoalButtonEnabled(false);

        String goalTitle = titleTextInput.getEditText().getText().toString().trim();
        String goalDescription = descriptionTextInput.getEditText().getText().toString().trim();
        String goalCategory = categoriesSpinner.getSelectedItem().toString();
        Date deadlineDate = getDeadlineDatePickerSelectedDate();

        viewModel.addGoal(goalTitle, goalDescription, goalCategory, deadlineDate);
    }

    private Date getDeadlineDatePickerSelectedDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(
                deadlineDatePicker.getYear(),
                deadlineDatePicker.getMonth(),
                deadlineDatePicker.getDayOfMonth()
        );

        return calendar.getTime();
    }
}