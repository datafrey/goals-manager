package com.datafrey.goalsmanager.viewmodelfactories;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.datafrey.goalsmanager.viewmodels.NewGoalActivityViewModel;

public class NewGoalActivityViewModelFactory implements ViewModelProvider.Factory {

    private Application application;

    public NewGoalActivityViewModelFactory(Application application) {
        this.application = application;
    }

    @SuppressWarnings("unchecked")
    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(NewGoalActivityViewModel.class)) {
            return (T) new NewGoalActivityViewModel(application);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
