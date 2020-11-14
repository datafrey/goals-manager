package com.datafrey.goalsmanager.data;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.datafrey.goalsmanager.util.TaskRunner;

import java.util.List;
import java.util.concurrent.Callable;

public class GoalsRepository {

    private final TaskRunner taskRunner;

    private final GoalsDao goalsDao;

    private final LiveData<List<Goal>> todayGoals;
    public LiveData<List<Goal>> getTodayGoals() {
        return todayGoals;
    }

    private final LiveData<List<Goal>> allGoals;
    public LiveData<List<Goal>> getAllGoals() {
        return allGoals;
    }

    // ...

    public GoalsRepository(Application application) {
        taskRunner = new TaskRunner();

        GoalsDatabase database = GoalsDatabase.getInstance(application);
        goalsDao = database.goalsDao();
        todayGoals = goalsDao.getTodayGoals();
        allGoals = goalsDao.getAllGoals();
    }

    public void insertGoal(Goal goal) {
        taskRunner.executeAsync(new InsertGoalTask(goalsDao, goal), success -> {
            if (success) {
                Log.i("DatabaseInsertionResult", "Insertion success");
            } else {
                Log.i("DatabaseInsertionResult", "Insertion failed");
            }
        });
    }

    public static class InsertGoalTask implements Callable<Boolean> {

        private final GoalsDao goalsDao;
        private final Goal goal;

        public InsertGoalTask(GoalsDao goalsDao, Goal goal) {
            this.goalsDao = goalsDao;
            this.goal = goal;
        }

        @Override
        public Boolean call() {
            try {
                goalsDao.insert(goal);
                return true;
            } catch (Exception exception) {
                Log.e("InsertionError", exception.getMessage());
                return false;
            }
        }
    }

    // ...
}
