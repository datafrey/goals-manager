package com.datafrey.goalsmanager.mainactivity.goalsfragment;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class GoalsListFragmentViewModelFactory implements ViewModelProvider.Factory {

    private Application application;
    private DeadlineType deadlineType;

    public GoalsListFragmentViewModelFactory(Application application,
                                             DeadlineType deadlineType) {
        this.application = application;
        this.deadlineType = deadlineType;
    }

    @SuppressWarnings("unchecked")
    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(GoalsListFragmentViewModel.class)) {
            return (T) new GoalsListFragmentViewModel(application, deadlineType);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
