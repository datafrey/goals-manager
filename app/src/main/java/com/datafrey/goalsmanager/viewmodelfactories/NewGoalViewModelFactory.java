package com.datafrey.goalsmanager.viewmodelfactories;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.datafrey.goalsmanager.viewmodels.NewGoalViewModel;

public class NewGoalViewModelFactory implements ViewModelProvider.Factory {

    private Application application;

    public NewGoalViewModelFactory(Application application) {
        this.application = application;
    }

    @SuppressWarnings("unchecked")
    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(NewGoalViewModel.class)) {
            return (T) new NewGoalViewModel(application);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
