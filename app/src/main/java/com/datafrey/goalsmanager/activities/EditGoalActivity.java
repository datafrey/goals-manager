package com.datafrey.goalsmanager.activities;

import android.content.Intent;
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
import com.datafrey.goalsmanager.data.Goal;
import com.datafrey.goalsmanager.viewmodelfactories.EditGoalActivityViewModelFactory;
import com.datafrey.goalsmanager.viewmodels.EditGoalActivityViewModel;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

public class EditGoalActivity extends AppCompatActivity {

    private TextInputLayout titleTextInput, descriptionTextInput;
    private Spinner categoriesSpinner;
    private DatePicker deadlineDatePicker;
    private ExtendedFloatingActionButton editGoalButton;

    private EditGoalActivityViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_goal);

        setupView();

        titleTextInput = findViewById(R.id.titleTextInput);
        descriptionTextInput = findViewById(R.id.descriptionTextInput);
        categoriesSpinner = findViewById(R.id.categoriesSpinner);
        deadlineDatePicker = findViewById(R.id.deadlineDatePicker);
        editGoalButton = findViewById(R.id.editGoalButton);

        Intent intent = getIntent();
        long goalId = intent.getLongExtra("goalId", Long.MAX_VALUE);

        viewModel = new ViewModelProvider(
                this,
                new EditGoalActivityViewModelFactory(getApplication(), goalId)
        ).get(EditGoalActivityViewModel.class);

        setupErrorMessages();

        viewModel.getObtainedGoal().observe(this, this::fillFieldsWithGoalInfo);

        findViewById(R.id.cancelButton).setOnClickListener(button -> finish());
        editGoalButton.setOnClickListener(button -> onEditGoalButtonClick());

        viewModel.getEditGoalButtonEnabled().observe(this,
                isEnabled -> editGoalButton.setEnabled(isEnabled));

        viewModel.getGoalEditionResult().observe(this, this::reactToGoalEditionResult);
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
                viewModel.hideTitleInputErrorMessage();
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
                viewModel.hideDescriptionInputErrorMessage();
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    private void fillFieldsWithGoalInfo(Goal goal) {
        if (goal != null) {
            titleTextInput.getEditText().setText(goal.getTitle());
            descriptionTextInput.getEditText().setText(goal.getDescription());

            categoriesSpinner.setSelection(
                    Arrays.asList(getResources().getStringArray(R.array.goal_categories))
                            .indexOf(goal.getCategory())
            );

            String[] dateComponents = goal.getDeadlineDate().split("-");
            deadlineDatePicker.updateDate(
                    Integer.parseInt(dateComponents[0]),
                    Integer.parseInt(dateComponents[1]) - 1,
                    Integer.parseInt(dateComponents[2])
            );
        }
    }

    private void onEditGoalButtonClick() {
        viewModel.setEditGoalButtonEnabled(false);

        String goalTitle = titleTextInput.getEditText().getText().toString().trim();
        String goalDescription = descriptionTextInput.getEditText().getText().toString().trim();
        String goalCategory = categoriesSpinner.getSelectedItem().toString();
        Date deadlineDate = getDeadlineDatePickerSelectedDate();

        viewModel.editGoal(goalTitle, goalDescription, goalCategory, deadlineDate);
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

    private void reactToGoalEditionResult(Boolean success) {
        if (success != null) {
            Toast.makeText(
                    this,
                    success ? "Goal successfully edited!" : "Something went wrong...",
                    Toast.LENGTH_SHORT
            ).show();

            viewModel.uiReactedToGoalEditionResult();

            if (success) finish();
        }
    }
}