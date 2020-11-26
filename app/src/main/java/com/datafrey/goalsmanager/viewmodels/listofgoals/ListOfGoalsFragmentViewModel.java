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

public abstract class ListOfGoalsFragmentViewModel extends AndroidViewModel {

    protected GoalsRepository goalsRepository;

    protected LiveData<List<Goal>> goalsToDisplay = new MutableLiveData<>(null);
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

    protected final MutableLiveData<Boolean> placeholderVisibility = new MutableLiveData<>(true);
    public LiveData<Boolean> getPlaceholderVisibility() {
        return placeholderVisibility;
    }

    public void setPlaceholderVisibility(boolean isVisible) {
        placeholderVisibility.setValue(isVisible);
    }

    public ListOfGoalsFragmentViewModel(@NonNull Application application) {
        super(application);

        goalsRepository = new GoalsRepository(application);
        goalsToDisplay = setGoalsToDisplay();
    }

    public void deleteGoal(Goal goal) {
        goalsRepository.deleteGoal(goal.getId());
    }
}
