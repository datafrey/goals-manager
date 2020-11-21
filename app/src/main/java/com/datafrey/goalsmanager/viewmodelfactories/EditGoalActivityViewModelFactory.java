package com.datafrey.goalsmanager.viewmodelfactories;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.datafrey.goalsmanager.viewmodels.EditGoalActivityViewModel;

public class EditGoalActivityViewModelFactory implements ViewModelProvider.Factory {

    private Application application;
    private long goalId;

    public EditGoalActivityViewModelFactory(Application application, long goalId) {
        this.application = application;
        this.goalId = goalId;
    }

    @SuppressWarnings("unchecked")
    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(EditGoalActivityViewModel.class)) {
            return (T) new EditGoalActivityViewModel(application, goalId);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
