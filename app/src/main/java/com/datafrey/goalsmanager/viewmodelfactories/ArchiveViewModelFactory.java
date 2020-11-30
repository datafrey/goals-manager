package com.datafrey.goalsmanager.viewmodelfactories;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.datafrey.goalsmanager.viewmodels.ArchiveViewModel;

public class ArchiveViewModelFactory implements ViewModelProvider.Factory {

    private Application application;

    public ArchiveViewModelFactory(Application application) {
        this.application = application;
    }

    @SuppressWarnings("unchecked")
    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(ArchiveViewModel.class)) {
            return (T) new ArchiveViewModel(application);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
