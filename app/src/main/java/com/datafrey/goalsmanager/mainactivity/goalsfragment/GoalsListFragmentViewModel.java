package com.datafrey.goalsmanager.mainactivity.goalsfragment;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.datafrey.goalsmanager.data.Goal;
import com.datafrey.goalsmanager.data.GoalsRepository;

import java.util.List;

public class GoalsListFragmentViewModel extends AndroidViewModel {

    private GoalsRepository goalsRepository;

    private LiveData<List<Goal>> goalsToDisplay;
    public LiveData<List<Goal>> getGoalsToDisplay() {
        return goalsToDisplay;
    }

    private GoalsListRecyclerViewAdapter goalsListRecyclerViewAdapter;

    public GoalsListRecyclerViewAdapter getGoalsListRecyclerViewAdapter() {
        return goalsListRecyclerViewAdapter;
    }

    public void setGoalsListRecyclerViewAdapter(GoalsListRecyclerViewAdapter adapter) {
        goalsListRecyclerViewAdapter = adapter;
    }

    public GoalsListFragmentViewModel(@NonNull Application application, DeadlineType deadlineType) {
        super(application);

        goalsRepository = new GoalsRepository(getApplication());

        switch (deadlineType) {
            case TODAY:
                goalsToDisplay = goalsRepository.getTodayGoals();
                break;
            case WEEK:
                goalsToDisplay = goalsRepository.getWeekGoals();
                break;
            case MONTH:
                goalsToDisplay = goalsRepository.getMonthGoals();
                break;
            case YEAR:
                goalsToDisplay = goalsRepository.getYearGoals();
                break;
        }
    }
}
