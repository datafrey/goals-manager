package com.datafrey.goalsmanager.newgoalactivity;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.datafrey.goalsmanager.data.DateConverter;
import com.datafrey.goalsmanager.data.Goal;
import com.datafrey.goalsmanager.data.GoalsRepository;
import com.datafrey.goalsmanager.util.userinputvalidation.InputIsEmptyMiddleware;
import com.datafrey.goalsmanager.util.userinputvalidation.InputIsTooLongMiddleware;
import com.datafrey.goalsmanager.util.userinputvalidation.InputValidationResult;
import com.datafrey.goalsmanager.util.userinputvalidation.InputValidatorMiddleware;

import java.util.Date;

public class NewGoalActivityViewModel extends AndroidViewModel {

    private GoalsRepository goalsRepository;

    public LiveData<Boolean> getNewGoalAdditionResult() {
        return goalsRepository.getGoalInsertionSuccess();
    }

    public void uiReactedToNewGoalAdditionResult() {
        goalsRepository.setGoalInsertionSuccessValueToDefault();
    }

    private final MutableLiveData<String> titleInputErrorMessage = new MutableLiveData<>("");
    public LiveData<String> getTitleInputErrorMessage() {
        return titleInputErrorMessage;
    }

    public void removeTitleInputErrorMessage() {
        titleInputErrorMessage.setValue("");
    }

    private final MutableLiveData<String> descriptionInputErrorMessage = new MutableLiveData<>("");
    public LiveData<String> getDescriptionInputErrorMessage() {
        return descriptionInputErrorMessage;
    }

    public void removeDescriptionInputErrorMessage() {
        descriptionInputErrorMessage.setValue("");
    }

    private final MutableLiveData<Boolean> addGoalButtonEnabled = new MutableLiveData<>(true);
    public LiveData<Boolean> getAddGoalButtonEnabled() {
        return addGoalButtonEnabled;
    }

    public void setAddGoalButtonEnabled(boolean enabled) {
        addGoalButtonEnabled.postValue(enabled);
    }

    public NewGoalActivityViewModel(@NonNull Application application) {
        super(application);
        goalsRepository = new GoalsRepository(getApplication());
    }

    public void addGoal(String title, String description,
                        String category, Date deadlineDate) {
        if (inputIsValid(title, description)) {
            Goal newGoal = new Goal();
            newGoal.setTitle(title);
            newGoal.setDescription(description);
            newGoal.setCategory(category);
            newGoal.setDeadlineDate(DateConverter.fromDateToDatabaseString(deadlineDate));

            goalsRepository.insertGoal(newGoal);
        }

        addGoalButtonEnabled.postValue(true);
    }

    private boolean inputIsValid(String title, String description) {
        InputValidatorMiddleware titleValidator = new InputIsEmptyMiddleware();
        titleValidator.linkWith(new InputIsTooLongMiddleware(50));

        InputValidatorMiddleware descriptionValidator = new InputIsEmptyMiddleware();
        descriptionValidator.linkWith(new InputIsTooLongMiddleware(300));

        InputValidationResult titleValidationResult = titleValidator.check(title);
        InputValidationResult descriptionValidationResult = descriptionValidator.check(description);

        switch (titleValidationResult) {
            case OK:
                titleInputErrorMessage.setValue("");
                break;
            case INPUT_IS_EMPTY:
                titleInputErrorMessage.setValue("Title field is empty!");
                break;
            case INPUT_IS_TOO_LONG:
                titleInputErrorMessage.setValue("Title is too long!");
                break;
        }

        switch (descriptionValidationResult) {
            case OK:
                descriptionInputErrorMessage.setValue("");
                break;
            case INPUT_IS_EMPTY:
                descriptionInputErrorMessage.setValue("Description field is empty!");
                break;
            case INPUT_IS_TOO_LONG:
                descriptionInputErrorMessage.setValue("Description is too long!");
                break;
        }

        return titleValidationResult == InputValidationResult.OK &&
                descriptionValidationResult == InputValidationResult.OK;
    }
}
