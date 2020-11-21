package com.datafrey.goalsmanager.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.datafrey.goalsmanager.adapters.GoalsListRecyclerViewAdapter;
import com.datafrey.goalsmanager.data.DeadlineType;
import com.datafrey.goalsmanager.data.Goal;
import com.datafrey.goalsmanager.data.GoalsRepository;

import java.util.List;

public class GoalsListFragmentViewModel extends AndroidViewModel {

    private GoalsRepository goalsRepository;
    private DeadlineType deadlineType;

    public DeadlineType getDeadlineType() {
        return deadlineType;
    }

    private LiveData<List<Goal>> goalsToDisplay;
    public LiveData<List<Goal>> getGoalsToDisplay() {
        return goalsToDisplay;
    }

    public LiveData<Boolean> getGoalDeletionResult() {
        return goalsRepository.getGoalDeletionSuccess();
    }

    public void uiReactedToGoalDeletionResult() {
        goalsRepository.setGoalDeleteSuccessValueToDefault();
    }

    private GoalsListRecyclerViewAdapter goalsListRecyclerViewAdapter;

    public GoalsListRecyclerViewAdapter getGoalsListRecyclerViewAdapter() {
        return goalsListRecyclerViewAdapter;
    }

    public void setGoalsListRecyclerViewAdapter(GoalsListRecyclerViewAdapter adapter) {
        goalsListRecyclerViewAdapter = adapter;
    }

    private final MutableLiveData<Boolean> placeholderVisibility = new MutableLiveData<>(true);
    public LiveData<Boolean> getPlaceholderVisibility() {
        return placeholderVisibility;
    }

    public void setPlaceholderVisibility(boolean isVisible) {
        placeholderVisibility.setValue(isVisible);
    }

    public GoalsListFragmentViewModel(@NonNull Application application, DeadlineType deadlineType) {
        super(application);

        goalsRepository = new GoalsRepository(getApplication());
        this.deadlineType = deadlineType;

        switch (deadlineType) {
            case TODAY:
                goalsToDisplay = goalsRepository.getTodayGoals();
                break;
            case WEEK:
                goalsToDisplay = goalsRepository.getWeekGoals();
                break;
            case NEXT_WEEK:
                goalsToDisplay = goalsRepository.getNextWeekGoals();
                break;
            case MONTH:
                goalsToDisplay = goalsRepository.getMonthGoals();
                break;
            case NEXT_MONTH:
                goalsToDisplay = goalsRepository.getNextMonthGoals();
                break;
            case YEAR:
                goalsToDisplay = goalsRepository.getYearGoals();
                break;
            case NEXT_YEAR:
                goalsToDisplay = goalsRepository.getNextYearGoals();
                break;
            case LONG_TERM:
                goalsToDisplay = goalsRepository.getLongTermGoals();
                break;
            case ARCHIVE:
                goalsToDisplay = goalsRepository.getArchiveGoals();
                break;
        }
    }

    public void deleteGoal(Goal goal) {
        goalsRepository.deleteGoal(goal.getId());
    }
}
