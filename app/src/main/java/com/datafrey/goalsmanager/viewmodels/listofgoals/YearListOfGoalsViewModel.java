package com.datafrey.goalsmanager.viewmodels.listofgoals;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.datafrey.goalsmanager.data.Goal;

import java.util.List;

public class YearListOfGoalsViewModel extends ListOfGoalsViewModel {

    public YearListOfGoalsViewModel(@NonNull Application application) {
        super(application);
    }

    @Override
    protected LiveData<List<Goal>> setGoalsToDisplay() {
        return goalsRepository.getYearGoals();
    }
}
