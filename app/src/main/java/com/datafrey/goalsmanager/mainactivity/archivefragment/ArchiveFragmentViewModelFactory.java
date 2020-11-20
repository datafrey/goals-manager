package com.datafrey.goalsmanager.mainactivity.archivefragment;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class ArchiveFragmentViewModelFactory implements ViewModelProvider.Factory {

    private Application application;

    public ArchiveFragmentViewModelFactory(Application application) {
        this.application = application;
    }

    @SuppressWarnings("unchecked")
    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(ArchiveFragmentViewModel.class)) {
            return (T) new ArchiveFragmentViewModel(application);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
