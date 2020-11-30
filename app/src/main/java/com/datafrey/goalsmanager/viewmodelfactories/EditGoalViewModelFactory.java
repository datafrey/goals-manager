package com.datafrey.goalsmanager.viewmodelfactories;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.datafrey.goalsmanager.viewmodels.EditGoalViewModel;

public class EditGoalViewModelFactory implements ViewModelProvider.Factory {

    private Application application;
    private long goalId;

    public EditGoalViewModelFactory(Application application, long goalId) {
        this.application = application;
        this.goalId = goalId;
    }

    @SuppressWarnings("unchecked")
    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(EditGoalViewModel.class)) {
            return (T) new EditGoalViewModel(application, goalId);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
