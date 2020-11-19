package com.datafrey.goalsmanager.editgoalactivity;

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

public class EditGoalActivityViewModel extends AndroidViewModel {

    private GoalsRepository goalsRepository;

    public LiveData<Goal> getObtainedGoal() {
        return goalsRepository.getObtainedGoal();
    }

    private void cleanObtainedGoalField() {
        goalsRepository.setObtainedGoalValueToDefault();
    }

    public LiveData<Boolean> getGoalEditionResult() {
        return goalsRepository.getGoalUpdateSuccess();
    }

    public void uiReactedToGoalEditionResult() {
        goalsRepository.setGoalUpdateSuccessValueToDefault();
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

    private final MutableLiveData<Boolean> editGoalButtonEnabled = new MutableLiveData<>(true);
    public LiveData<Boolean> getEditGoalButtonEnabled() {
        return editGoalButtonEnabled;
    }

    public void setEditGoalButtonEnabled(boolean enabled) {
        editGoalButtonEnabled.postValue(enabled);
    }

    public EditGoalActivityViewModel(@NonNull Application application, long goalId) {
        super(application);
        goalsRepository = new GoalsRepository(getApplication());
        goalsRepository.getGoal(goalId);
    }

    public void editGoal(String title, String description,
                         String category, Date deadlineDate) {
        if (inputIsValid(title, description)) {
            Goal goal = getObtainedGoal().getValue();
            goal.setTitle(title);
            goal.setDescription(description);
            goal.setCategory(category);
            goal.setDeadlineDate(DateConverter.fromDateToDatabaseString(deadlineDate));

            goalsRepository.updateGoal(goal);
        }

        editGoalButtonEnabled.postValue(true);
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

    @Override
    protected void onCleared() {
        super.onCleared();
        cleanObtainedGoalField();
    }
}
