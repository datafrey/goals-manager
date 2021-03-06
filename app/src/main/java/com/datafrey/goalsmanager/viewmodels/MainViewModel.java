package com.datafrey.goalsmanager.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class MainViewModel extends AndroidViewModel {

    private final MutableLiveData<String> actionBarTitle = new MutableLiveData("Goals");
    public LiveData<String> getActionBarTitle() {
        return actionBarTitle;
    }

    private final MutableLiveData<Boolean> actionBarHasShadow = new MutableLiveData(false);
    public LiveData<Boolean> getActionBarHasShadow() {
        return actionBarHasShadow;
    }

    public MainViewModel(@NonNull Application application) {
        super(application);
    }

    public void goalsFragmentOpened() {
        actionBarTitle.setValue("Goals");
        actionBarHasShadow.setValue(false);
    }

    public void archiveFragmentOpened() {
        actionBarTitle.setValue("Archive");
        actionBarHasShadow.setValue(true);
    }

    public void aboutFragmentOpened() {
        actionBarTitle.setValue("About");
        actionBarHasShadow.setValue(true);
    }
}
