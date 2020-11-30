package com.datafrey.goalsmanager.viewmodels.listofgoals;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.datafrey.goalsmanager.adapters.GoalsListRecyclerViewAdapter;
import com.datafrey.goalsmanager.data.Goal;
import com.datafrey.goalsmanager.data.GoalsRepository;

import java.util.List;

public abstract class ListOfGoalsViewModel extends AndroidViewModel {

    protected GoalsRepository goalsRepository;

    protected boolean firstLoadOfGoalsList = true;

    public boolean isFirstLoadOfGoalsList() {
        return firstLoadOfGoalsList;
    }

    public void setFirstLoadOfGoalsList(boolean firstLoadOfGoalsList) {
        this.firstLoadOfGoalsList = firstLoadOfGoalsList;
    }

    protected LiveData<List<Goal>> goalsToDisplay;
    public LiveData<List<Goal>> getGoalsToDisplay() {
        return goalsToDisplay;
    }

    protected abstract LiveData<List<Goal>> setGoalsToDisplay();

    public LiveData<Boolean> getGoalDeletionResult() {
        return goalsRepository.getGoalDeletionSuccess();
    }

    public void uiReactedToGoalDeletionResult() {
        goalsRepository.setGoalDeleteSuccessValueToDefault();
    }

    protected GoalsListRecyclerViewAdapter goalsListRecyclerViewAdapter;

    public GoalsListRecyclerViewAdapter getGoalsListRecyclerViewAdapter() {
        return goalsListRecyclerViewAdapter;
    }

    public void setGoalsListRecyclerViewAdapter(GoalsListRecyclerViewAdapter adapter) {
        goalsListRecyclerViewAdapter = adapter;
    }

    protected final MutableLiveData<Boolean> progressBarVisibility = new MutableLiveData<>(true);
    public LiveData<Boolean> getProgressBarVisibility() {
        return progressBarVisibility;
    }

    public void setProgressBarVisibility(boolean isVisible) {
        progressBarVisibility.setValue(isVisible);
    }

    protected final MutableLiveData<Boolean> placeholderVisibility = new MutableLiveData<>(false);
    public LiveData<Boolean> getPlaceholderVisibility() {
        return placeholderVisibility;
    }

    public void setPlaceholderVisibility(boolean isVisible) {
        placeholderVisibility.setValue(isVisible);
    }

    public ListOfGoalsViewModel(@NonNull Application application) {
        super(application);

        goalsRepository = new GoalsRepository(application);
        goalsToDisplay = setGoalsToDisplay();
    }

    public void deleteGoal(Goal goal) {
        goalsRepository.deleteGoal(goal.getId());
    }
}
